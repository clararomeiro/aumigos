package com.vet.repository;

import com.vet.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, String> {
    List<Tutor> findByNomeContainingIgnoreCase(String nome);
}
