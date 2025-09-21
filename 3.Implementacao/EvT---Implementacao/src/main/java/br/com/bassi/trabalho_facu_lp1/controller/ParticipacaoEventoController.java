package br.com.bassi.trabalho_facu_lp1.controller;

import br.com.bassi.trabalho_facu_lp1.domain.Usuario;
import br.com.bassi.trabalho_facu_lp1.dto.ParticipacaoEventoDTO;
import br.com.bassi.trabalho_facu_lp1.dto.FuncionarioDTO;
import br.com.bassi.trabalho_facu_lp1.service.ParticipacaoEventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/participacoes")
@RequiredArgsConstructor
public class ParticipacaoEventoController {

    private final ParticipacaoEventoService participacaoEventoService;

    @PostMapping
    public ResponseEntity<Void> participarDoEvento(@RequestBody ParticipacaoEventoDTO dto) {
        participacaoEventoService.adicionarParticipante(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("!hasAuthority('VISITANTE')")
    @GetMapping("/evento/{eventoId}/usuarios")
    public ResponseEntity<List<FuncionarioDTO>> listarUsuariosDoEvento(@PathVariable Long eventoId) {
        List<FuncionarioDTO> usuarios = participacaoEventoService.listarUsuariosPorEvento(eventoId);
        return ResponseEntity.ok(usuarios);
    }

}

