package com.duoc.learningplatform.curso_service.service;

import com.duoc.learningplatform.curso_service.client.AuthClient;
import com.duoc.learningplatform.curso_service.exception.BadRequestException;
import com.duoc.learningplatform.curso_service.exception.ResourceNotFoundException;
import com.duoc.learningplatform.curso_service.model.Curso;
import com.duoc.learningplatform.curso_service.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final AuthClient authClient;

    private static final String ROL_PROFESOR = "PROFESOR";

    public CursoService(CursoRepository cursoRepository, AuthClient authClient) {
        this.cursoRepository = cursoRepository;
        this.authClient = authClient;
    }

    public List<Curso> obtenerTodosCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso registrarCurso(Curso curso) {

        Boolean existe = authClient.existsUserById(curso.getProfesorId());

        if (existe == null || !existe) {
            throw new ResourceNotFoundException("Profesor no existe");
        }

        String rol = authClient.getUserRole(curso.getProfesorId());

        if (rol == null || !ROL_PROFESOR.equalsIgnoreCase(rol)) {
            throw new BadRequestException("El usuario no tiene rol PROFESOR");
        }

        return cursoRepository.save(curso);
    }

    public Optional<Curso> modificarCurso(Long id, Curso curso) {

        if (!cursoRepository.existsById(id)) {
            return Optional.empty();
        }

        Boolean existe = authClient.existsUserById(curso.getProfesorId());

        if (existe == null || !existe) {
            throw new ResourceNotFoundException("Profesor no existe");
        }

        String rol = authClient.getUserRole(curso.getProfesorId());

        if (rol == null || !ROL_PROFESOR.equalsIgnoreCase(rol)) {
            throw new BadRequestException("El usuario no tiene rol PROFESOR");
        }

        curso.setId(id);
        return Optional.of(cursoRepository.save(curso));
    }

    public boolean eliminarCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            return false;
        }
        cursoRepository.deleteById(id);
        return true;
    }
}