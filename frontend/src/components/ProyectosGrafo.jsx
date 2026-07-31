import { useEffect, useRef, useState, useCallback } from "react";
import { Network } from "vis-network";
import { DataSet } from "vis-data";

/**
 * ProyectosGrafo — Embeddable vis-network graph for the Projects section.
 * Renders a force-directed graph of Personas ↔ Proyectos relationships.
 *
 * @param {{ proyectos: Array, junta: Array }} props
 */
export default function ProyectosGrafo({ proyectos = [], junta = [] }) {
  const containerRef = useRef(null);
  const networkRef = useRef(null);
  const floatRef = useRef(null);
  const interactingRef = useRef(false);
  const [tooltip, setTooltip] = useState({
    show: false,
    x: 0,
    y: 0,
    title: "",
    info: "",
    type: "",
    extra: "",
  });

  // Build vis-network nodes & edges from backend data
  const buildGraphData = useCallback(() => {
    const nodeMap = new Map();
    const edges = [];

    // Add persona nodes from junta
    junta.forEach((u) => {
      const rol =
        u.roles && u.roles.length > 0 ? u.roles[0].rol : "Miembro";
      nodeMap.set(`u-${u.idUsuario}`, {
        id: `u-${u.idUsuario}`,
        label: u.nombre,
        type: "persona",
        info: rol,
        extra: u.correo || "",
      });
    });

    // Add proyecto nodes + edges to collaborators
    proyectos.forEach((p) => {
      nodeMap.set(`p-${p.idProyecto}`, {
        id: `p-${p.idProyecto}`,
        label: p.nombre,
        type: "proyecto",
        info: `Estado: ${p.estado || "N/A"}`,
        extra: p.github || "",
      });

      (p.colaboradores || []).forEach((c) => {
        const personaId = `u-${c.idUsuario}`;
        // If persona wasn't in junta, add them
        if (!nodeMap.has(personaId)) {
          nodeMap.set(personaId, {
            id: personaId,
            label: c.nombre,
            type: "persona",
            info: "Colaborador",
            extra: c.correo || "",
          });
        }
        edges.push({ from: personaId, to: `p-${p.idProyecto}` });
      });
    });

    return { rawNodes: Array.from(nodeMap.values()), rawEdges: edges };
  }, [proyectos, junta]);

  useEffect(() => {
    if (!containerRef.current) return;
    if (proyectos.length === 0 && junta.length === 0) return;

    const { rawNodes, rawEdges } = buildGraphData();

    // ── Format nodes with high-end dark glow ──
    const formattedNodes = rawNodes.map((node) => {
      const isPersona = node.type === "persona";
      return {
        id: node.id,
        label: node.label,
        shape: isPersona ? "dot" : "diamond",
        size: isPersona ? 18 : 22,
        color: {
          background: isPersona ? "#006699" : "#10b981",
          border: isPersona ? "#00a3e0" : "#34d399",
          highlight: {
            background: isPersona ? "#00a3e0" : "#34d399",
            border: isPersona ? "#e0f2fe" : "#d1fae5",
          },
          hover: {
            background: isPersona ? "#00a3e0" : "#34d399",
            border: "#ffffff",
          },
        },
        shadow: {
          enabled: true,
          color: isPersona
            ? "rgba(0, 163, 224, 0.45)"
            : "rgba(52, 211, 153, 0.45)",
          size: 16,
          x: 0,
          y: 0,
        },
        borderWidth: 2,
        borderWidthSelected: 4,
        font: {
          color: "#0f172a",
          face: "'Plus Jakarta Sans', Inter, system-ui, sans-serif",
          size: 12,
          strokeWidth: 0,
          background: "rgba(255, 255, 255, 0.85)",
          bold: { color: "#0f172a" },
        },
        meta: node,
      };
    });

    // ── Format edges with subtle glow ──
    const formattedEdges = rawEdges.map((edge, i) => ({
      id: `e${i}`,
      from: edge.from,
      to: edge.to,
      color: {
        color: "rgba(100, 116, 139, 0.3)",
        highlight: "#f43f5e",
        hover: "#fb7185",
      },
      width: 1.5,
      selectionWidth: 3.5,
      hoverWidth: 2.5,
      smooth: { type: "continuous", roundness: 0.2 },
    }));

    const nodesDS = new DataSet(formattedNodes);
    const edgesDS = new DataSet(formattedEdges);

    // ── Physics: Soft floating, no jerks ──
    const options = {
      interaction: {
        hover: true,
        tooltipDelay: 200,
        zoomView: true,
        dragView: true,
        selectConnectedEdges: false,
      },
      physics: {
        solver: "forceAtlas2Based",
        forceAtlas2Based: {
          gravitationalConstant: -35,
          centralGravity: 0.005,
          springLength: 180,
          springConstant: 0.02,
          damping: 0.85,
        },
        maxVelocity: 8,
        timestep: 0.35,
        stabilization: {
          enabled: true,
          iterations: 200,
          updateInterval: 25,
        },
      },
    };

    const network = new Network(
      containerRef.current,
      { nodes: nodesDS, edges: edgesDS },
      options
    );
    networkRef.current = network;

    // ── Tooltip (clamped to container bounds) ──
    network.on("hoverNode", (params) => {
      const node = nodesDS.get(params.node);
      if (!node?.meta) return;
      const domPos = network.canvasToDOM(network.getPosition(params.node));
      const rect = containerRef.current.getBoundingClientRect();
      const tooltipW = 200;
      const tooltipH = 90;

      // Clamp so tooltip stays inside container
      let tx = Math.max(tooltipW / 2 + 8, Math.min(domPos.x, rect.width - tooltipW / 2 - 8));
      let ty = Math.max(tooltipH + 16, domPos.y - 15);

      setTooltip({
        show: true,
        x: tx,
        y: ty,
        title: node.meta.label,
        info: node.meta.info,
        type: node.meta.type,
        extra: node.meta.extra,
      });
    });

    network.on("blurNode", () =>
      setTooltip((prev) => ({ ...prev, show: false }))
    );

    // ── Click focus: dim non-connected ──
    network.on("click", (params) => {
      if (params.nodes.length > 0) {
        const selId = params.nodes[0];
        const connected = network.getConnectedNodes(selId);
        const connEdges = network.getConnectedEdges(selId);

        nodesDS.update(
          nodesDS.get().map((n) => {
            const lit = n.id === selId || connected.includes(n.id);
            return {
              id: n.id,
              opacity: lit ? 1 : 0.12,
              font: {
                color: lit ? "#0f172a" : "rgba(15,23,42,0.15)",
                background: lit ? "rgba(255,255,255,0.95)" : "transparent",
              },
            };
          })
        );
        edgesDS.update(
          edgesDS.get().map((e) => {
            const lit = connEdges.includes(e.id);
            return {
              id: e.id,
              opacity: lit ? 1 : 0.05,
              width: lit ? 3 : 1,
              color: {
                color: lit ? "#f43f5e" : "rgba(100,116,139,0.05)",
              },
            };
          })
        );
      } else {
        // Reset
        nodesDS.update(
          nodesDS.get().map((n) => ({
            id: n.id,
            opacity: 1,
            font: { color: "#0f172a", background: "rgba(255, 255, 255, 0.85)" },
          }))
        );
        edgesDS.update(
          edgesDS.get().map((e) => ({
            id: e.id,
            opacity: 1,
            width: 1.5,
            color: { color: "rgba(100, 116, 139, 0.3)" },
          }))
        );
      }
    });

    // ── Pause floating during user interaction ──
    network.on("dragStart", () => {
      interactingRef.current = true;
    });
    network.on("dragEnd", () => {
      setTimeout(() => {
        interactingRef.current = false;
      }, 1200);
    });

    // ── Idle floating: sinusoidal micro-perturbation ──
    let tick = 0;
    floatRef.current = setInterval(() => {
      if (interactingRef.current) return;
      tick++;
      const allNodes = nodesDS.get();
      const updates = allNodes
        .map((node) => {
          const pos = network.getPositions([node.id])[node.id];
          if (!pos) return null;
          // Phase offset per node for organic motion
          const phase = (node.id.charCodeAt(1) || 0) * 0.7;
          return {
            id: node.id,
            x: pos.x + Math.sin(tick * 0.3 + phase) * 1.2,
            y: pos.y + Math.cos(tick * 0.25 + phase * 1.3) * 1.0,
          };
        })
        .filter(Boolean);
      nodesDS.update(updates);
      network.startSimulation();
    }, 2000);

    return () => {
      clearInterval(floatRef.current);
      network.destroy();
    };
  }, [proyectos, junta, buildGraphData]);

  // Stats derived from data
  const personaCount = new Set([
    ...junta.map((u) => u.idUsuario),
    ...proyectos.flatMap((p) =>
      (p.colaboradores || []).map((c) => c.idUsuario)
    ),
  ]).size;
  const proyectoCount = proyectos.length;
  const edgeCount = proyectos.reduce(
    (acc, p) => acc + (p.colaboradores?.length || 0),
    0
  );

  if (proyectos.length === 0 && junta.length === 0) return null;

  return (
    <div className="grafo-container">
      {/* Header */}
      <div className="grafo-header">
        <div>
          <h3 className="grafo-title">Red de Colaboración</h3>
          <p className="grafo-subtitle">
            {personaCount} personas · {proyectoCount} proyectos ·{" "}
            {edgeCount} conexiones
          </p>
        </div>

        {/* Legend */}
        <div className="grafo-legend">
          <div className="grafo-legend-item">
            <span className="grafo-legend-dot grafo-legend-persona"></span>
            <span>Persona</span>
          </div>
          <div className="grafo-legend-item">
            <span className="grafo-legend-dot grafo-legend-proyecto"></span>
            <span>Proyecto</span>
          </div>
        </div>
      </div>

      {/* Graph canvas */}
      <div style={{ position: "relative" }}>
        <div ref={containerRef} className="grafo-canvas" />

        {/* Floating tooltip */}
        {tooltip.show && (
          <div
            className="grafo-tooltip"
            style={{
              top: tooltip.y,
              left: tooltip.x,
              borderColor:
                tooltip.type === "persona"
                  ? "rgba(0, 163, 224, 0.4)"
                  : "rgba(52, 211, 153, 0.4)",
            }}
          >
            <div className="grafo-tooltip-badge-row">
              <span
                className="grafo-tooltip-badge"
                style={{
                  backgroundColor:
                    tooltip.type === "persona"
                      ? "rgba(0, 163, 224, 0.2)"
                      : "rgba(52, 211, 153, 0.2)",
                  color:
                    tooltip.type === "persona" ? "#00a3e0" : "#34d399",
                }}
              >
                {tooltip.type}
              </span>
            </div>
            <div className="grafo-tooltip-title">{tooltip.title}</div>
            <div className="grafo-tooltip-info">{tooltip.info}</div>
            {tooltip.extra && (
              <div className="grafo-tooltip-extra">{tooltip.extra}</div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}
