package br.com.bassi.trabalho_facu_lp1.controller;

import br.com.bassi.trabalho_facu_lp1.domain.Usuario;
import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumCargos;
import br.com.bassi.trabalho_facu_lp1.dto.UsuarioDTO;
import br.com.bassi.trabalho_facu_lp1.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PreAuthorize("hasAuthority('GERENTE')")
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }


    @PreAuthorize("hasAuthority('GERENTE')")
    @GetMapping("/visitantes")
    public ResponseEntity<List<Usuario>> listarVisitantes() {
        return ResponseEntity.ok(usuarioService.listarPorCargo(EnumCargos.VISITANTE));
    }

    @PreAuthorize("hasAuthority('GERENTE')")
    @GetMapping("/nao-visitantes")
    public ResponseEntity<List<Usuario>> listarNaoVisitantes() {
        return ResponseEntity.ok(usuarioService.listarDiferenteDeCargo(EnumCargos.VISITANTE));
    }

    @PreAuthorize("hasAuthority('GERENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        usuarioService.editarUsuario(id, dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('GERENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
