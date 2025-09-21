package br.com.bassi.trabalho_facu_lp1.controller;

import br.com.bassi.trabalho_facu_lp1.domain.Evento;
import br.com.bassi.trabalho_facu_lp1.dto.EventoDTO;
import br.com.bassi.trabalho_facu_lp1.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PreAuthorize("!hasAuthority('VISITANTE')")
    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody EventoDTO eventoDTO) {
        var evento = eventoService.criarEvento(eventoDTO);
        return ResponseEntity.created(URI.create("/eventos/" + evento.getId())).body(evento);
    }

    @GetMapping
    public ResponseEntity<List<Evento>> listarEventos() {
        return ResponseEntity.ok(eventoService.listarEventos());
    }
    @PreAuthorize("!hasAuthority('VISITANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarEventoPorId(@PathVariable Long id) {
        return eventoService.buscarEvento(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("!hasAuthority('VISITANTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(@PathVariable Long id) {
        eventoService.deletarEvento(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("!hasAuthority('VISITANTE')")
    @PutMapping("/{id}")
    public ResponseEntity<Evento> editarEvento(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) {
        var evento = eventoService.editarEvento(id, eventoDTO);
        return ResponseEntity.ok(evento);
    }
<<<<<<< HEAD
=======

>>>>>>> 6984d858 (Minhas alterações locais antes do pull)
}
