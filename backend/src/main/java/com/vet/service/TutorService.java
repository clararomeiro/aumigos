package com.vet.service;

import com.vet.model.*;
import com.vet.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public Tutor buscarPorNome(String nome) {
        List<Tutor> lista = tutorRepository.findByNomeContainingIgnoreCase(nome);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Tutor cadastrar(Tutor t) {
        return tutorRepository.save(t);
    }
}
