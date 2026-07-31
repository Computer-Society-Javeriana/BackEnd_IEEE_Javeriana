package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "lugar")
    private String lugar;

    @ManyToOne
    @JoinColumn(name = "id_capitulo", nullable = false)
    private Capitulo capitulo;
}
