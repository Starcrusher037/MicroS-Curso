package com.duoc.learningplatform.curso_service.repository;

import com.duoc.learningplatform.curso_service.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByCursoId(Long cursoId);

    boolean existsByCursoId(Long cursoId);

    boolean existsByCursoIdAndEstudianteId(Long cursoId, Long estudianteId);
}