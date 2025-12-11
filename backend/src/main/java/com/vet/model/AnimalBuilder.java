package com.vet.model;

// Poderia ser singleton
public class AnimalBuilder {
    private Animal animal;

    public AnimalBuilder() {
        this.animal = new Animal();
    }

    public AnimalBuilder comNome(String nome) {
        this.animal.setNome(nome);
        return this;
    }

    public AnimalBuilder daEspecie(String especie) {
        this.animal.setEspecie(especie);
        return this;
    }

    public AnimalBuilder daRaca(String raca) {
        this.animal.setRaca(raca);
        return this;
    }

    public AnimalBuilder nascidoEm(String nascimento) {
        this.animal.setNascimento(nascimento);
        return this;
    }

    public AnimalBuilder comPeso(Float peso) {
        this.animal.setPeso(peso);
        return this;
    }

    public AnimalBuilder doSexo(String sexo) {
        this.animal.setSexo(sexo);
        return this;
    }

    public AnimalBuilder comPlano(Long plano) {
        this.animal.setPlano(plano);
        return this;
    }

    public AnimalBuilder comObservacoes(String obs) {
        this.animal.setObs(obs);
        return this;
    }

    public AnimalBuilder doTutor(Tutor tutor) {
        this.animal.setTutor(tutor);
        return this;
    }

    public Animal build() {
        return this.animal;
    }
}