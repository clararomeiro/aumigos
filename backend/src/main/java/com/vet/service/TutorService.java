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

  public Tutor buscarPorCpf(String cpf) {
        return tutorRepository.findById(cpf).orElse(null);
    }

    public Tutor cadastrar(Tutor t) {
        return tutorRepository.save(t);
    }
}
