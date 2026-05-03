package com.duoc.learningplatform.curso_service.service;

import com.duoc.learningplatform.curso_service.client.AuthClient;
import com.duoc.learningplatform.curso_service.exception.BadRequestException;
import com.duoc.learningplatform.curso_service.exception.ResourceNotFoundException;
import com.duoc.learningplatform.curso_service.model.Inscripcion;
import com.duoc.learningplatform.curso_service.repository.CursoRepository;
import com.duoc.learningplatform.curso_service.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final CursoRepository cursoRepository;
    private final AuthClient authClient;

    private static final String ROL_ALUMNO = "ALUMNO";

    public InscripcionService(InscripcionRepository inscripcionRepository,
                              CursoRepository cursoRepository,
                              AuthClient authClient) {
        this.inscripcionRepository = inscripcionRepository;
        this.cursoRepository = cursoRepository;
        this.authClient = authClient;
    }

    public List<Inscripcion> obtenerInscripcionesCursoId(Long cursoId) {

        if (!cursoRepository.existsById(cursoId)) {
            throw new ResourceNotFoundException("El curso no existe");
        }

        return inscripcionRepository.findByCursoId(cursoId);
    }

    public Inscripcion registrarInscripcion(Inscripcion inscripcion) {

        if (!cursoRepository.existsById(inscripcion.getCursoId())) {
            throw new ResourceNotFoundException("El curso no existe");
        }

        Boolean existe = authClient.existsUserById(inscripcion.getEstudianteId());

        if (existe == null || !existe) {
            throw new ResourceNotFoundException("Estudiante no existe");
        }

        String rol = authClient.getUserRole(inscripcion.getEstudianteId());

        if (rol == null || !ROL_ALUMNO.equalsIgnoreCase(rol)) {
            throw new BadRequestException("El usuario no tiene rol ALUMNO");
        }

        return inscripcionRepository.save(inscripcion);
    }

    public boolean eliminarInscripcion(Long id) {
        if (inscripcionRepository.existsById(id)) {
            inscripcionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}