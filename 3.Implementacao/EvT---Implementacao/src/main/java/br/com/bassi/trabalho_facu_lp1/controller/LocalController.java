package br.com.bassi.trabalho_facu_lp1.controller;

import br.com.bassi.trabalho_facu_lp1.domain.Local;
import br.com.bassi.trabalho_facu_lp1.dto.LocalDTO;
import br.com.bassi.trabalho_facu_lp1.service.LocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locais")
@RequiredArgsConstructor
public class LocalController {

    private final LocalService localService;

    @PreAuthorize("!hasAuthority('VISITANTE')")
    @PostMapping
    public ResponseEntity<Local> criarLocal(@RequestBody LocalDTO localDTO) {
        var local = localService.criarLocal(localDTO);
        return ResponseEntity.created(URI.create("/locais/" + local.getId())).body(local);
    }
    @PreAuthorize("!hasAuthority('VISITANTE')")
    @GetMapping
    public ResponseEntity<List<Local>> listarLocais() {
        return ResponseEntity.ok(localService.listarLocais());
    }
    @PreAuthorize("!hasAuthority('VISITANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Local> buscarLocal(@PathVariable Long id) {
        return localService.buscarLocal(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("!hasAuthority('VISITANTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLocal(@PathVariable Long id) {
        localService.deletarLocal(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("!hasAuthority('VISITANTE')")
    @PutMapping("/{id}")
    public ResponseEntity<Local> editarLocal(@PathVariable Long id, @RequestBody LocalDTO localDTO) {
        var local = localService.editarLocal(id, localDTO);
        return ResponseEntity.ok(local);
    }
}

