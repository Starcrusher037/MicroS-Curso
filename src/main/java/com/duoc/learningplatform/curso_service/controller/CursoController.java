package com.duoc.learningplatform.curso_service.controller;

import com.duoc.learningplatform.curso_service.model.Curso;
import com.duoc.learningplatform.curso_service.service.CursoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> obtenerTodosCursos() {
        return ResponseEntity.ok(cursoService.obtenerTodosCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCursoPorId(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(cursoService.obtenerCursoPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<Curso> registrarCurso(@Valid @RequestBody Curso curso) {

        Curso creado = cursoService.registrarCurso(curso);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<Curso> actualizarCurso(
            @PathVariable @Positive Long id,
            @Valid @RequestBody Curso curso) {

        return ResponseEntity.ok(cursoService.modificarCurso(id, curso));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<Void> eliminarCurso(@PathVariable @Positive Long id) {

        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeCurso(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.existeCurso(id));
    }
}