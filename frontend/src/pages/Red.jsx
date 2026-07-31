import React, { useEffect, useRef, useState } from 'react';
import { Network } from 'vis-network';
import { DataSet } from 'vis-data';

export default function RedColaboracion() {
  const containerRef = useRef(null);
  const [tooltip, setTooltip] = useState({ show: false, x: 0, y: 0, title: '', info: '', type: '' });
  const [stats, setStats] = useState({ personas: 5, proyectos: 4, conexiones: 8 });

  useEffect(() => {
    if (!containerRef.current) return;

    // 1. Dataset Mock (JSON)
    const dataset = {
      nodes: [
        { id: 'u1', label: 'Migao Mendoza', type: 'persona', info: 'Presidente RAS', email: 'migao@javeriana.edu.co' },
        { id: 'u2', label: 'Empanada Pérez', type: 'persona', info: 'Vicepresidente RAS', email: 'empanada@javeriana.edu.co' },
        { id: 'u3', label: 'Tamal García', type: 'persona', info: 'Secretario RAS', email: 'tamal@javeriana.edu.co' },
        { id: 'u4', label: 'Arepa López', type: 'persona', info: 'Webmaster RAS', email: 'arepa@javeriana.edu.co' },
        { id: 'u5', label: 'Ajiaco Ramírez', type: 'persona', info: 'Tesorero RAS', email: 'ajiaco@javeriana.edu.co' },
        
        { id: 'p1', label: 'Robot Sumo Autónomo', type: 'proyecto', info: 'Estado: Completado', tech: 'C++, ROS2, PCB' },
        { id: 'p2', label: 'Brazo Háptico 6-DOF', type: 'proyecto', info: 'Estado: En Desarrollo', tech: 'Python, Arduino' },
        { id: 'p3', label: 'Visión Drones YOLOv8', type: 'proyecto', info: 'Estado: Fase Inicial', tech: 'PyTorch, OpenCV' },
        { id: 'p4', label: 'Simulador ROS2 Gazebo', type: 'proyecto', info: 'Estado: Completado', tech: 'Gazebo, Linux' }
      ],
      edges: [
        { from: 'u1', to: 'p1' }, 
        { from: 'u2', to: 'p1' }, 
        { from: 'u1', to: 'p2' },
        { from: 'u3', to: 'p2' },
        { from: 'u4', to: 'p3' },
        { from: 'u5', to: 'p3' },
        { from: 'u2', to: 'p4' },
        { from: 'u4', to: 'p4' }
      ]
    };

    // 2. Formatting Nodes with High-End Dark Glow
    const formattedNodes = dataset.nodes.map(node => {
      const isPersona = node.type === 'persona';
      return {
        id: node.id,
        label: node.label,
        shape: isPersona ? 'dot' : 'diamond',
        size: isPersona ? 20 : 24,
        color: {
          background: isPersona ? '#0284c7' : '#059669',
          border: isPersona ? '#38bdf8' : '#34d399',
          highlight: {
            background: isPersona ? '#38bdf8' : '#34d399',
            border: isPersona ? '#bae6fd' : '#a7f3d0'
          },
          hover: {
            background: isPersona ? '#38bdf8' : '#34d399',
            border: '#ffffff'
          }
        },
        shadow: {
          enabled: true,
          color: isPersona ? 'rgba(56, 189, 248, 0.45)' : 'rgba(52, 211, 153, 0.45)',
          size: 18,
          x: 0,
          y: 0
        },
        borderWidth: 2,
        borderWidthSelected: 4,
        font: { 
          color: '#f8fafc',
          face: 'Inter, Segoe UI, system-ui, sans-serif',
          size: 13,
          strokeWidth: 0,
          background: 'rgba(15, 23, 42, 0.75)',
          bold: { color: '#ffffff' }
        },
        // Store raw node info for tooltip
        meta: node
      };
    });

    // 3. Formatting Edges with Transparency & Glow
    const formattedEdges = dataset.edges.map((edge, index) => {
      return {
        id: `e${index}`,
        from: edge.from,
        to: edge.to,
        color: { 
          color: 'rgba(148, 163, 184, 0.25)', 
          highlight: '#f43f5e',
          hover: '#fb7185'
        },
        width: 1.5,
        selectionWidth: 3.5,
        hoverWidth: 2.5,
        smooth: {
          type: 'continuous',
          roundness: 0.2
        },
        shadow: {
          enabled: false,
          color: 'rgba(244, 63, 94, 0.6)',
          size: 10
        }
      };
    });

    const nodesDataSet = new DataSet(formattedNodes);
    const edgesDataSet = new DataSet(formattedEdges);
    const data = { nodes: nodesDataSet, edges: edgesDataSet };

    // 4. Advanced Physics & Options
    const options = {
      interaction: {
        hover: true,
        tooltipDelay: 300,
        zoomView: true,
        dragView: true,
        selectConnectedEdges: false
      },
      physics: {
        solver: 'forceAtlas2Based',
        forceAtlas2Based: {
          gravitationalConstant: -70,
          centralGravity: 0.012,
          springLength: 160,
          springConstant: 0.04,
          damping: 0.5
        },
        maxVelocity: 35,
        timestep: 0.35,
        stabilization: {
          enabled: true,
          iterations: 120,
          updateInterval: 25
        }
      }
    };

    const network = new Network(containerRef.current, data, options);

    // 5. Custom Floating Tooltip Logic
    network.on("hoverNode", function (params) {
      const node = nodesDataSet.get(params.node);
      if (node && node.meta) {
        const domPos = network.canvasToDOM(network.getPosition(params.node));
        const containerRect = containerRef.current.getBoundingClientRect();
        
        setTooltip({
          show: true,
          x: domPos.x,
          y: domPos.y - 15,
          title: node.meta.label,
          info: node.meta.info,
          type: node.meta.type,
          extra: node.meta.email || node.meta.tech
        });
      }
    });

    network.on("blurNode", function () {
      setTooltip(prev => ({ ...prev, show: false }));
    });

    // 6. Interactive Highlighting (Focus Effect)
    network.on("click", function (params) {
      if (params.nodes.length > 0) {
        const selectedNodeId = params.nodes[0];
        const connectedNodes = network.getConnectedNodes(selectedNodeId);
        const connectedEdges = network.getConnectedEdges(selectedNodeId);

        // Dim non-connected nodes, illuminate connected ones
        const updateNodes = nodesDataSet.get().map(node => {
          const isConnected = node.id === selectedNodeId || connectedNodes.includes(node.id);
          return { 
            id: node.id, 
            opacity: isConnected ? 1 : 0.12,
            font: { 
              color: isConnected ? '#ffffff' : 'rgba(255, 255, 255, 0.2)',
              background: isConnected ? 'rgba(15, 23, 42, 0.9)' : 'transparent'
            }
          };
        });
        nodesDataSet.update(updateNodes);

        // Highlight connected edges with glowing magenta/pink
        const updateEdges = edgesDataSet.get().map(edge => {
          const isConnected = connectedEdges.includes(edge.id);
          return { 
            id: edge.id, 
            opacity: isConnected ? 1 : 0.05,
            width: isConnected ? 3 : 1,
            color: { color: isConnected ? '#f43f5e' : 'rgba(148, 163, 184, 0.05)' }
          };
        });
        edgesDataSet.update(updateEdges);

      } else {
        // Reset state on empty click
        const updateNodes = nodesDataSet.get().map(node => ({ 
          id: node.id, 
          opacity: 1,
          font: { color: '#f8fafc', background: 'rgba(15, 23, 42, 0.75)' }
        }));
        nodesDataSet.update(updateNodes);
        
        const updateEdges = edgesDataSet.get().map(edge => ({ 
          id: edge.id, 
          opacity: 1,
          width: 1.5,
          color: { color: 'rgba(148, 163, 184, 0.25)' }
        }));
        edgesDataSet.update(updateEdges);
      }
    });

    // 7. Efecto de "Flotación" Continua (Continuous floating effect)
    const floatInterval = setInterval(() => {
      const allNodes = nodesDataSet.get();
      const updates = allNodes.map(node => {
        const position = network.getPositions([node.id])[node.id];
        if (position) {
          return {
            id: node.id,
            // Pequeña perturbación aleatoria para crear el efecto de flotación
            x: position.x + (Math.random() * 6 - 3),
            y: position.y + (Math.random() * 6 - 3)
          };
        }
        return null;
      }).filter(Boolean);
      nodesDataSet.update(updates);
      network.startSimulation(); // Despierta la física para que se ajuste suavemente
    }, 800);

    return () => {
      clearInterval(floatInterval);
      network.destroy();
    };
  }, []);

  return (
    <div style={{ backgroundColor: '#090d16', minHeight: 'calc(100vh - 70px)', padding: '2rem 1.5rem', color: '#f8fafc' }}>
      <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
        
        {/* Header con estética Dark Glassmorphism */}
        <div style={{ 
          display: 'flex', 
          justifyContent: 'space-between', 
          alignItems: 'center', 
          marginBottom: '1.5rem',
          flexWrap: 'wrap',
          gap: '1rem'
        }}>
          <div>
            <h1 style={{ 
              margin: 0, 
              fontSize: '1.875rem', 
              fontWeight: 700, 
              background: 'linear-gradient(135deg, #38bdf8 0%, #34d399 100%)',
              WebkitBackgroundClip: 'text',
              WebkitTextFillColor: 'transparent'
            }}>
              Red de Colaboración
            </h1>
            <p style={{ margin: '0.25rem 0 0 0', color: '#94a3b8', fontSize: '0.95rem' }}>
              Mapeo interactivo de relaciones de personas y proyectos del capítulo
            </p>
          </div>

          {/* Leyenda Glassmorphic */}
          <div style={{ 
            display: 'flex', 
            alignItems: 'center', 
            gap: '1.25rem',
            background: 'rgba(30, 41, 59, 0.6)',
            backdropFilter: 'blur(12px)',
            border: '1px solid rgba(255, 255, 255, 0.1)',
            padding: '0.6rem 1.2rem',
            borderRadius: '12px',
            boxShadow: '0 8px 32px 0 rgba(0, 0, 0, 0.36)'
          }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.875rem' }}>
              <span style={{ 
                width: '12px', 
                height: '12px', 
                borderRadius: '50%', 
                backgroundColor: '#38bdf8',
                boxShadow: '0 0 10px rgba(56, 189, 248, 0.8)'
              }}></span>
              <span style={{ color: '#cbd5e1' }}>Persona</span>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.875rem' }}>
              <span style={{ 
                width: '12px', 
                height: '12px', 
                transform: 'rotate(45deg)',
                backgroundColor: '#34d399',
                boxShadow: '0 0 10px rgba(52, 211, 153, 0.8)'
              }}></span>
              <span style={{ color: '#cbd5e1' }}>Proyecto</span>
            </div>
          </div>
        </div>

        {/* Contenedor del Grafo */}
        <div style={{ position: 'relative' }}>
          <div 
            ref={containerRef} 
            style={{ 
              height: '620px',
              width: '100%',
              backgroundColor: '#0f172a', 
              border: '1px solid rgba(255, 255, 255, 0.08)', 
              borderRadius: '16px',
              boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.5), 0 8px 10px -6px rgba(0, 0, 0, 0.5)',
              overflow: 'hidden'
            }} 
          />

          {/* Custom Floating Tooltip */}
          {tooltip.show && (
            <div style={{
              position: 'absolute',
              top: tooltip.y,
              left: tooltip.x,
              transform: 'translate(-50%, -100%)',
              pointerEvents: 'none',
              zIndex: 100,
              background: 'rgba(15, 23, 42, 0.92)',
              backdropFilter: 'blur(16px)',
              border: `1px solid ${tooltip.type === 'persona' ? 'rgba(56, 189, 248, 0.4)' : 'rgba(52, 211, 153, 0.4)'}`,
              borderRadius: '10px',
              padding: '0.75rem 1rem',
              minWidth: '180px',
              boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.6), 0 0 15px rgba(0, 0, 0, 0.5)',
              transition: 'opacity 0.2s ease, transform 0.2s ease'
            }}>
              <div style={{ display: 'flex', alignItems: 'center', gap: '0.4rem', marginBottom: '0.25rem' }}>
                <span style={{
                  fontSize: '0.7rem',
                  padding: '2px 6px',
                  borderRadius: '4px',
                  fontWeight: 600,
                  textTransform: 'uppercase',
                  backgroundColor: tooltip.type === 'persona' ? 'rgba(56, 189, 248, 0.2)' : 'rgba(52, 211, 153, 0.2)',
                  color: tooltip.type === 'persona' ? '#38bdf8' : '#34d399'
                }}>
                  {tooltip.type}
                </span>
              </div>
              <div style={{ fontWeight: 700, fontSize: '0.95rem', color: '#f8fafc', marginBottom: '0.2rem' }}>
                {tooltip.title}
              </div>
              <div style={{ fontSize: '0.8rem', color: '#94a3b8' }}>
                {tooltip.info}
              </div>
              {tooltip.extra && (
                <div style={{ fontSize: '0.75rem', color: '#64748b', marginTop: '0.4rem', borderTop: '1px solid rgba(255, 255, 255, 0.08)', paddingTop: '0.4rem' }}>
                  {tooltip.extra}
                </div>
              )}
            </div>
          )}
        </div>

      </div>
    </div>
  );
}
