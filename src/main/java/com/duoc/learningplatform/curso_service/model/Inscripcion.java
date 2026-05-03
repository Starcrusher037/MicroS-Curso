package com.duoc.learningplatform.curso_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "inscripcion")
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    private Long cursoId;

    @Min(1)
    private Long estudianteId;

    @NotNull
    @PastOrPresent
    private LocalDate fechaInscripcion;
}