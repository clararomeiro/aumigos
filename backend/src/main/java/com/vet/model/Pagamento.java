package com.vet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String tipo; 
    private Long data;  

    @OneToOne(mappedBy = "pagamento")
    @JsonIgnore
    private Consulta consulta;
}
