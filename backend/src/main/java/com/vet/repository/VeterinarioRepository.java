package com.vet.repository;

import com.vet.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, String> {
}