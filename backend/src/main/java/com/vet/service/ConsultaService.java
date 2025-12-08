package com.vet.service;

import com.vet.model.*;
import com.vet.repository.*;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepo;
    private final AnimalRepository animalRepo;
    private final VeterinarioRepository vetRepo;

    public ConsultaService(ConsultaRepository c, AnimalRepository a, VeterinarioRepository v) {
        this.consultaRepo = c;
        this.animalRepo = a;
        this.vetRepo = v;
    }

    public Consulta agendar(Long animalId, String vetCpf, String data) {
        Animal a = animalRepo.findById(animalId).orElseThrow();
        Veterinario v = vetRepo.findById(vetCpf).orElseThrow();

        Consulta c = new Consulta();
        c.setAnimal(a);
        c.setVeterinario(v);
        c.setData(data);

        return consultaRepo.save(c);
    }
}
