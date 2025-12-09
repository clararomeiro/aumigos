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

    public Animal cadastrar(String tutorCpf, String nome, String especie, 
                            String raca, String nascimento, String sexo, 
                            Float peso, Long plano, String obs) {

        Tutor t = tutorRepository.findById(tutorCpf).orElseThrow(() -> new RuntimeException("Tutor não encontrado para o CPF: " + tutorCpf));
        Animal animal = new AnimalBuilder()
                .comNome(nome)
                .daEspecie(especie)
                .daRaca(raca)
                .nascidoEm(nascimento)
                .doSexo(sexo)
                .comPeso(peso)
                .comPlano(plano) 
                .comObservacoes(obs) 
                .doTutor(t)
                .build();

        return animalRepository.save(animal);
    }

    public void deletar(Long id) {
        animalRepository.deleteById(id);
    }
}
