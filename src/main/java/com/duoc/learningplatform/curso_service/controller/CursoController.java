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
        return cursoService.obtenerCursoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

        return cursoService.modificarCurso(id, curso)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESOR')")
    public ResponseEntity<Void> eliminarCurso(@PathVariable @Positive Long id) {

        return cursoService.eliminarCurso(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeCurso(@PathVariable Long id) {
        boolean existe = cursoService.obtenerCursoPorId(id).isPresent();
        return ResponseEntity.ok(existe);
}
}