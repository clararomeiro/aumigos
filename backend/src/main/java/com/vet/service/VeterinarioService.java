package com.vet.service;

import com.vet.model.*;
import com.vet.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinarioRepository repo;

    public VeterinarioService(VeterinarioRepository repo) {
        this.repo = repo;
    }

    public Veterinario cadastrar(Veterinario v) {
        return repo.save(v);
    }

    public void deletar(String cpf) {
        repo.deleteById(cpf);
    }

    public List<Veterinario> listarTodos() {
        return repo.findAll();
    }
}
