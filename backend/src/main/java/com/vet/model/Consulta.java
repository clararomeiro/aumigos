package com.vet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;

    @ManyToOne
    private Animal animal;

    @ManyToOne
    private Veterinario veterinario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;
}