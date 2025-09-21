package br.com.bassi.trabalho_facu_lp1.service;

import br.com.bassi.trabalho_facu_lp1.client.CepClient;
import br.com.bassi.trabalho_facu_lp1.dto.ViaCepResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CepService {
    private final CepClient client;

    public ViaCepResponseDTO buscarPorCep(String cep) {

        return client.getEndereco(cep);
    }
}