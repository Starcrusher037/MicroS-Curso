package com.duoc.learningplatform.curso_service.controller;

import com.duoc.learningplatform.curso_service.model.Evaluacion;
import com.duoc.learningplatform.curso_service.service.EvaluacionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public ResponseEntity<List<Evaluacion>> obtenerTodasEvaluaciones() {
        return ResponseEntity.ok(evaluacionService.obtenerTodasEvaluaciones());
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Evaluacion>> obtenerPorCurso(
            @PathVariable @Positive Long cursoId) {

        return ResponseEntity.ok(
                evaluacionService.obtenerEvaluacionesPorCurso(cursoId)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<Evaluacion> registrarEvaluacion(
            @Valid @RequestBody Evaluacion evaluacion) {

        Evaluacion creada = evaluacionService.registrarEvaluacion(evaluacion);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();

        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<Evaluacion> modificarEvaluacion(
            @PathVariable @Positive Long id,
            @Valid @RequestBody Evaluacion evaluacion) {

        return evaluacionService.modificarEvaluacion(id, evaluacion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}