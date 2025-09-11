package br.com.bassi.trabalho_facu_lp1.service;

import br.com.bassi.trabalho_facu_lp1.domain.Usuario;
import br.com.bassi.trabalho_facu_lp1.dto.FuncionarioDTO;
import br.com.bassi.trabalho_facu_lp1.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void cadastrarFuncionario(FuncionarioDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setCargo(dto.cargo());
        usuario.setDepartamento(dto.departamento());
        usuario.setCpf((dto.cpf()));


        usuarioRepository.save(usuario);
    }
}






