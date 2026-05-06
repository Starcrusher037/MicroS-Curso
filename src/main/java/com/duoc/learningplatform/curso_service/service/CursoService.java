package com.duoc.learningplatform.curso_service.service;

import com.duoc.learningplatform.curso_service.client.AuthClient;
import com.duoc.learningplatform.curso_service.exception.BadRequestException;
import com.duoc.learningplatform.curso_service.exception.NotFoundException;
import com.duoc.learningplatform.curso_service.model.Curso;
import com.duoc.learningplatform.curso_service.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Curso obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));
    }

    public Curso registrarCurso(Curso curso) {

        Boolean existe = authClient.existsUserById(curso.getProfesorId());

        if (existe == null || !existe) {
            throw new NotFoundException("Profesor no existe");
        }

        String rol = authClient.getUserRole(curso.getProfesorId());

        if (rol == null || !ROL_PROFESOR.equalsIgnoreCase(rol)) {
            throw new BadRequestException("El usuario no tiene rol PROFESOR");
        }

        return cursoRepository.save(curso);
    }

    public Curso modificarCurso(Long id, Curso curso) {

        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

        Boolean existe = authClient.existsUserById(curso.getProfesorId());

        if (existe == null || !existe) {
            throw new NotFoundException("Profesor no existe");
        }

        String rol = authClient.getUserRole(curso.getProfesorId());

        if (rol == null || !ROL_PROFESOR.equalsIgnoreCase(rol)) {
            throw new BadRequestException("El usuario no tiene rol PROFESOR");
        }

        cursoExistente.setNombre(curso.getNombre());
        cursoExistente.setDescripcion(curso.getDescripcion());
        cursoExistente.setProfesorId(curso.getProfesorId());

        return cursoRepository.save(cursoExistente);
    }

    public void eliminarCurso(Long id) {

        if (!cursoRepository.existsById(id)) {
            throw new NotFoundException("Curso no encontrado");
        }

        cursoRepository.deleteById(id);
    }

    public boolean existeCurso(Long id) {
        return cursoRepository.existsById(id);
    }
}