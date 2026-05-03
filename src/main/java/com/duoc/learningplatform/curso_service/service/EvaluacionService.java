package com.duoc.learningplatform.curso_service.service;

import com.duoc.learningplatform.curso_service.exception.ResourceNotFoundException;
import com.duoc.learningplatform.curso_service.model.Evaluacion;
import com.duoc.learningplatform.curso_service.repository.CursoRepository;
import com.duoc.learningplatform.curso_service.repository.EvaluacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final CursoRepository cursoRepository;

    public EvaluacionService(EvaluacionRepository evaluacionRepository,
                             CursoRepository cursoRepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<Evaluacion> obtenerTodasEvaluaciones() {
        return evaluacionRepository.findAll();
    }

    public List<Evaluacion> obtenerEvaluacionesPorCurso(Long cursoId) {

        if (!cursoRepository.existsById(cursoId)) {
            throw new ResourceNotFoundException("El curso no existe");
        }

        return evaluacionRepository.findByCursoId(cursoId);
    }

    public Evaluacion registrarEvaluacion(Evaluacion evaluacion) {

        if (!cursoRepository.existsById(evaluacion.getCursoId())) {
            throw new ResourceNotFoundException("El curso no existe");
        }

        return evaluacionRepository.save(evaluacion);
    }

    public Optional<Evaluacion> modificarEvaluacion(Long id, Evaluacion evaluacion) {

        if (!evaluacionRepository.existsById(id)) {
            return Optional.empty();
        }

        evaluacion.setId(id);
        return Optional.of(evaluacionRepository.save(evaluacion));
    }
}