package br.com.bassi.trabalho_facu_lp1.controller;

import br.com.bassi.trabalho_facu_lp1.dto.LoginRequest;
import br.com.bassi.trabalho_facu_lp1.dto.FuncionarioDTO;
import br.com.bassi.trabalho_facu_lp1.dto.TokenResponse;
import br.com.bassi.trabalho_facu_lp1.dto.UsuarioDTO;
import br.com.bassi.trabalho_facu_lp1.service.AuthService;
import br.com.bassi.trabalho_facu_lp1.service.FuncionarioService;
import br.com.bassi.trabalho_facu_lp1.service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final FuncionarioService funcionarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String token = authService.autenticar(loginRequest.email(), loginRequest.senha());

        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1h
        response.addCookie(cookie);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestBody UsuarioDTO dto) {
        usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @PreAuthorize("hasAuthority('GERENTE')")
    @PostMapping("/funcionarios")
    public ResponseEntity<Void> cadastrarFuncionario(@RequestBody FuncionarioDTO dto) {
        funcionarioService.cadastrarFuncionario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/cadastrar-gerente")
    public ResponseEntity<Void> cadastrarFuncionario2(@RequestBody FuncionarioDTO dto) {
        funcionarioService.cadastrarFuncionario(dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Authorization", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }
}

