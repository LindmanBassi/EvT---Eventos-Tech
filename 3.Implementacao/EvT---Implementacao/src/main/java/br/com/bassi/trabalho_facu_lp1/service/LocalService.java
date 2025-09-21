package br.com.bassi.trabalho_facu_lp1.service;

import br.com.bassi.trabalho_facu_lp1.domain.Endereco;
import br.com.bassi.trabalho_facu_lp1.domain.Local;
import br.com.bassi.trabalho_facu_lp1.dto.EnderecoDTO;
import br.com.bassi.trabalho_facu_lp1.dto.LocalDTO;
import br.com.bassi.trabalho_facu_lp1.dto.ViaCepResponseDTO;
import br.com.bassi.trabalho_facu_lp1.dto.ViaCepResponseDTO;
import br.com.bassi.trabalho_facu_lp1.repositories.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;
    private final CepService cepService;

    public Local criarLocal(LocalDTO localDTO) {
        Endereco endereco = construirEnderecoCompletado(localDTO.endereco());
        Local local = new Local();
        local.setNome(localDTO.nome());
        local.setCapacidade(localDTO.capacidade());
        local.setEndereco(endereco);
        return localRepository.save(local);
    }

    public void deletarLocal(Long id) {
        localRepository.deleteById(id);
    }

    public Optional<Local> buscarLocal(Long id) {
        return localRepository.findById(id);
    }

    public List<Local> listarLocais() {
        return localRepository.findAll();
    }

    public Local editarLocal(Long id, LocalDTO localDTO) {
        return localRepository.findById(id).map(local -> {
            Endereco endereco = construirEnderecoCompletado(localDTO.endereco());
            local.setNome(localDTO.nome());
            local.setCapacidade(localDTO.capacidade());
            local.setEndereco(endereco);
            return localRepository.save(local);
        }).orElseThrow(() -> new RuntimeException("Local não encontrado"));
    }

    private Endereco construirEnderecoCompletado(EnderecoDTO dto) {
        ViaCepResponseDTO viaCep = cepService.buscarPorCep(dto.cep());

        String rua = isEmpty(dto.rua()) ? viaCep.logradouro() : dto.rua();
        String bairro = isEmpty(dto.bairro()) ? viaCep.bairro() : dto.bairro();
        String cidade = isEmpty(dto.cidade()) ? viaCep.localidade() : dto.cidade();
        String estado = isEmpty(dto.estado()) ? viaCep.uf() : dto.estado();

        return new Endereco(rua, bairro, cidade, estado, dto.numero(), dto.cep());
    }

    private boolean isEmpty(String s) {
        return s == null || s.isBlank();
    }
}
