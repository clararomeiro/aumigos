package com.vet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float valor;
    private String tipo; // Credito, Debito, Plano
    private Long data;   // Timestamp ou data em long conforme UML

    @OneToOne(mappedBy = "pagamento")
    private Consulta consulta;
}
