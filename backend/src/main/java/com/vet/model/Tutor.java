package com.vet.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tutor {

    @Id
    private String cpf;

    private String nome;
    private String endereco;
    private String email;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animais;
}
