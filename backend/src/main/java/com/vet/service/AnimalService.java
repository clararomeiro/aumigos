package com.vet.service;

import com.vet.model.*;
import com.vet.repository.*;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final TutorRepository tutorRepository;

    public AnimalService(AnimalRepository ar, TutorRepository tr) {
        this.animalRepository = ar;
        this.tutorRepository = tr;
    }

    public Animal cadastrar(String tutorCpf, String nome, String especie) {
        Tutor t = tutorRepository.findById(tutorCpf).orElseThrow();
        Animal a = new Animal();
        a.setNome(nome);
        a.setEspecie(especie);
        a.setTutor(t);
        return animalRepository.save(a);
    }

    public void deletar(Long id) {
        animalRepository.deleteById(id);
    }
}
