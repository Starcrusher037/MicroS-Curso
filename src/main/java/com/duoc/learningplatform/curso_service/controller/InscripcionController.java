package com.duoc.learningplatform.curso_service.controller;

import com.duoc.learningplatform.curso_service.model.Inscripcion;
import com.duoc.learningplatform.curso_service.service.InscripcionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Inscripcion>> obtenerPorCurso(
            @PathVariable @Positive Long cursoId) {

        return ResponseEntity.ok(
                inscripcionService.obtenerInscripcionesCursoId(cursoId)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ALUMNO')")
    public ResponseEntity<Inscripcion> registrarInscripcion(
            @Valid @RequestBody Inscripcion inscripcion) {

        Inscripcion creada = inscripcionService.registrarInscripcion(inscripcion);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ALUMNO')")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable @Positive Long id) {
        inscripcionService.eliminarInscripcion(id);
        return ResponseEntity.noContent().build();
    }
    
}