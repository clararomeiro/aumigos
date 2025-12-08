package com.vet.service;

import com.vet.model.*;
import com.vet.repository.*;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioService {

    private final VeterinarioRepository repo;

    public VeterinarioService(VeterinarioRepository repo) {
        this.repo = repo;
    }

    public Veterinario cadastrar(Veterinario v) {
        return repo.save(v);
    }
}
