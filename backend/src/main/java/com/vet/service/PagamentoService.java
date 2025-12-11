package com.vet.service;

import com.vet.model.*;
import com.vet.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepo;
    private final ConsultaRepository consultaRepo;
    private final RestTemplate restTemplate;

    @Value("${api.payments.gateway.url}")
    private String gatewayUrl;

    public PagamentoService(PagamentoRepository pr, ConsultaRepository cr, RestTemplate rt) {
        this.pagamentoRepo = pr;
        this.consultaRepo = cr;
        this.restTemplate = rt;
    }

    public void realizarPagamento(Long consultaId, String tipo, Float valor) {

        Consulta consulta = consultaRepo.findById(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (consulta.getPagamento() != null) {
            throw new RuntimeException("Esta consulta já está paga!");
        }

        String url = gatewayUrl + "/pagar";
        PagamentoRequest requestExterno = new PagamentoRequest(tipo, valor);

        try {
            ResponseEntity<PagamentoResponseExterno> response = 
                restTemplate.postForEntity(url, requestExterno, PagamentoResponseExterno.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Pagamento recusado pelo serviço financeiro.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na comunicação com pagamentos: " + e.getMessage());
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setTipo(tipo);
        pagamento.setValor(valor);
        pagamento.setData(System.currentTimeMillis());

        pagamentoRepo.save(pagamento); // Salva no repositório de pagamentos

        consulta.setPagamento(pagamento); // Atualiza a consulta
        consultaRepo.save(consulta);
    }
}

class PagamentoRequest {
    public String tipo;
    public double valor;
    public PagamentoRequest(String t, Float v) { this.tipo = t; this.valor = v; }
}
class PagamentoResponseExterno {
    public String status;
    public String message;
}