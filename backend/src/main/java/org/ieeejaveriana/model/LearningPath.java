package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "learningpath")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_path")
    private Long idPath;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "url_recurso")
    private String urlRecurso;

    @Column(name = "nivel")
    private String nivel;

    @ManyToOne
    @JoinColumn(name = "id_capitulo", nullable = false)
    private Capitulo capitulo;
}
