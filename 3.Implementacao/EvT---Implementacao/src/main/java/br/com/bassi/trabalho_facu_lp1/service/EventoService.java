package br.com.bassi.trabalho_facu_lp1.service;

import br.com.bassi.trabalho_facu_lp1.domain.Evento;
import br.com.bassi.trabalho_facu_lp1.domain.Local;
import br.com.bassi.trabalho_facu_lp1.domain.Usuario;
import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumEstadoEvento;
import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumTipoEvento;
import br.com.bassi.trabalho_facu_lp1.dto.EventoDTO;
import br.com.bassi.trabalho_facu_lp1.repositories.EventoRepository;
import br.com.bassi.trabalho_facu_lp1.repositories.LocalRepository;
import br.com.bassi.trabalho_facu_lp1.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final LocalRepository localRepository;
    private final UsuarioRepository usuarioRepository;

    public Evento criarEvento(EventoDTO dto) {
        Evento evento = new Evento();

        evento.setData(dto.data());
        evento.setTitulo(dto.titulo());
        evento.setDescricao(dto.descricao());
        evento.setVagas(dto.vagas());
        evento.setTipoEvento(dto.tipoEvento());

        if (dto.data().before(new Date())) {
            evento.setEstadoEvento(EnumEstadoEvento.FECHADO);
        } else {
            evento.setEstadoEvento(dto.estadoEvento() != null ? dto.estadoEvento() : EnumEstadoEvento.ABERTO);
        }

        if (dto.tipoEvento() != EnumTipoEvento.REMOTO && dto.localId() != null) {
            Local local = localRepository.findById(dto.localId())
                    .orElseThrow(() -> new RuntimeException("Local não encontrado"));
            evento.setLocal(local);
        }

        Usuario palestrante = usuarioRepository.findById(dto.palestranteId())
                .orElseThrow(() -> new RuntimeException("Palestrante não encontrado"));
        evento.setPalestrante(palestrante);

        return eventoRepository.save(evento);
    }

    public void deletarEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    public Optional<Evento> buscarEvento(Long id) {
        Optional<Evento> eventoOpt = eventoRepository.findById(id);
        eventoOpt.ifPresent(this::atualizarEstadoSeNecessario);
        return eventoOpt;
    }

    public List<Evento> listarEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        eventos.forEach(this::atualizarEstadoSeNecessario);
        return eventos;
    }

    public Evento editarEvento(Long id, EventoDTO dto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (evento.getEstadoEvento() == EnumEstadoEvento.FECHADO) {
            throw new RuntimeException("Evento já foi fechado e não pode ser alterado.");
        }

        evento.setData(dto.data());
        evento.setTitulo(dto.titulo());
        evento.setDescricao(dto.descricao());
        evento.setVagas(dto.vagas());
        evento.setTipoEvento(dto.tipoEvento());

        if (dto.data().before(new Date())) {
            evento.setEstadoEvento(EnumEstadoEvento.FECHADO);
        } else {
            evento.setEstadoEvento(dto.estadoEvento() != null ? dto.estadoEvento() : EnumEstadoEvento.ABERTO);
        }

        if (dto.palestranteId() != null) {
            Usuario palestrante = usuarioRepository.findById(dto.palestranteId())
                    .orElseThrow(() -> new RuntimeException("Palestrante não encontrado"));
            evento.setPalestrante(palestrante);
        }

        if (dto.tipoEvento() == EnumTipoEvento.REMOTO) {
            evento.setLocal(null);
        } else if (dto.localId() != null) {
            Local local = localRepository.findById(dto.localId())
                    .orElseThrow(() -> new RuntimeException("Local não encontrado"));
            evento.setLocal(local);
        }

        return eventoRepository.save(evento);
    }

    private void atualizarEstadoSeNecessario(Evento evento) {
        if (evento.getData().before(new Date()) && evento.getEstadoEvento() != EnumEstadoEvento.FECHADO) {
            evento.setEstadoEvento(EnumEstadoEvento.FECHADO);
            eventoRepository.save(evento);
        }
    }
}
