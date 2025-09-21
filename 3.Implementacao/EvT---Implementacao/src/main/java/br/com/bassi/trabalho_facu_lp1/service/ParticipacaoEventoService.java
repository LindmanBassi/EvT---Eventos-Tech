package br.com.bassi.trabalho_facu_lp1.service;

import br.com.bassi.trabalho_facu_lp1.domain.Evento;
import br.com.bassi.trabalho_facu_lp1.domain.ParticipacaoEvento;
import br.com.bassi.trabalho_facu_lp1.domain.Usuario;
import br.com.bassi.trabalho_facu_lp1.dto.ParticipacaoEventoDTO;
import br.com.bassi.trabalho_facu_lp1.dto.FuncionarioDTO;
import br.com.bassi.trabalho_facu_lp1.repositories.EventoRepository;
import br.com.bassi.trabalho_facu_lp1.repositories.ParticipacaoEventoRepository;
import br.com.bassi.trabalho_facu_lp1.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipacaoEventoService {

    private final EventoRepository eventoRepository;
    private final ParticipacaoEventoRepository participacaoEventoRepository;
    private final UsuarioRepository usuarioRepository;

    public void adicionarParticipante(ParticipacaoEventoDTO dto) {
        Evento evento = eventoRepository.findByTitulo(dto.tituloEvento())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com nome: " + dto.tituloEvento()));

        Usuario usuario = usuarioRepository.findByCpf(dto.cpfUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com CPF: " + dto.cpfUsuario()));

        if (evento.getVagas() <= 0) {
            throw new RuntimeException("Evento sem vagas disponíveis");
        }

        ParticipacaoEvento participacao = new ParticipacaoEvento();
        participacao.setEventoId(evento);
        participacao.setUsuarioId(usuario);

        participacaoEventoRepository.save(participacao);

        evento.setVagas(evento.getVagas() - 1);
        eventoRepository.save(evento);
    }

    public List<FuncionarioDTO> listarUsuariosPorEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        List<Usuario> usuarios = participacaoEventoRepository.findByEventoId(evento)
                .stream()
                .map(ParticipacaoEvento::getUsuarioId)
                .collect(Collectors.toList());


        return usuarios.stream()
                .map(usuario -> new FuncionarioDTO(
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getSenha(),
                        usuario.getCpf(),
                        usuario.getCargo(),
                        usuario.getDepartamento()
                ))
                .collect(Collectors.toList());
    }



}

