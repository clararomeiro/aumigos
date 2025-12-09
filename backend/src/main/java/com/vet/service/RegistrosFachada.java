package com.vet.service;

import com.vet.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RegistrosFachada {

    private final TutorService tutorService;
    private final AnimalService animalService;
    private final VeterinarioService vetService;
    private final ConsultaService consultaService;
    private final PagamentoService pagamentoService;

    public RegistrosFachada(TutorService t, AnimalService a, VeterinarioService v, ConsultaService c, PagamentoService p) {
        this.tutorService = t;
        this.animalService = a;
        this.vetService = v;
        this.consultaService = c;
        this.pagamentoService = p;
    }

    public Tutor buscarTutor(String cpf) {
        return tutorService.buscarPorCpf(cpf);
    }

    public void cadastrarTutor(Tutor t) {
        tutorService.cadastrar(t);
    }

    public void cadastrarAnimal(String tutorCpf, String nome, String especie,
                                String raca, String nascimento, String sexo,
                                Float peso, Long plano, String obs) {
        animalService.cadastrar(tutorCpf, nome, especie, raca, nascimento, sexo, peso, plano, obs);
    }

    public void deletarAnimal(Long id) {
        animalService.deletar(id);
    }

    public void cadastrarVeterinario(Veterinario v) {
        vetService.cadastrar(v);
    }

    public void deletarVeterinario(String cpf) {
        vetService.deletar(cpf);
    }

   public List<Veterinario> listarVeterinarios() {
        return vetService.listarTodos();
    }

    public void agendarConsulta(Long animalId, String vetCpf, String data) {
        consultaService.agendar(animalId, vetCpf, data);
    }

    public List<Consulta> listarConsultas() {
        return consultaService.listarTodas();
    }

    public void realizarPagamento(Long consultaId, String tipo, Float valor) {
        pagamentoService.realizarPagamento(consultaId, tipo, valor);
    }
}