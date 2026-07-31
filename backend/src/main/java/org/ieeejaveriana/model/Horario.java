package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "horarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long idHorario;

    @Column(name = "tema_clase", nullable = false)
    private String temaClase;

    @Column(name = "profesor", nullable = false)
    private String profesor;

    @Column(name = "lugar", nullable = false)
    private String lugar;

    @Column(name = "recomendaciones")
    private String recomendaciones;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_capitulo", nullable = false)
    private Capitulo capitulo;
}
