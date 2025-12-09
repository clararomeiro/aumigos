package com.vet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especie;
    private String raca;
    private String nascimento;
    private String sexo;
    private Float peso;

    @Column(nullable = true)
    private Long plano; 
    @Column(nullable = true)
    private String obs;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonIgnore
    private Tutor tutor;
}
