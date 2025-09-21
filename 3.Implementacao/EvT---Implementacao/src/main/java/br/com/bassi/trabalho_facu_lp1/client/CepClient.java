package br.com.bassi.trabalho_facu_lp1.client;

import br.com.bassi.trabalho_facu_lp1.dto.ViaCepResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient",url = "https://viacep.com.br/ws/")
public interface CepClient {

    @GetMapping("{cep}/json/")
    ViaCepResponseDTO getEndereco(@PathVariable String cep);
}
