package com.duoc.learningplatform.curso_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "evaluacion")
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long cursoId;

    @NotBlank
    private String nombre;

    @Min(1)
    private int puntajeMaximo;

    @NotNull
    @PastOrPresent
    private LocalDate fechaAplicacion;
}