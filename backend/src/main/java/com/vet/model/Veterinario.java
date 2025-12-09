package com.vet.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Veterinario {

    @Id
    private String cpf;

    private String nome;
    private String email;

    @OneToMany(mappedBy = "veterinario")
    private List<Consulta> consultas;
}
