package br.com.bassi.trabalho_facu_lp1.service;

import br.com.bassi.trabalho_facu_lp1.domain.Usuario;
import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumCargos;
import br.com.bassi.trabalho_facu_lp1.dto.UsuarioDTO;
import br.com.bassi.trabalho_facu_lp1.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void cadastrarUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setCpf(dto.cpf());
        usuario.setCargo(EnumCargos.VISITANTE);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarPorCargo(EnumCargos cargo) {
        return usuarioRepository.findByCargo(cargo);
    }

    public List<Usuario> listarDiferenteDeCargo(EnumCargos cargo) {
        return usuarioRepository.findByCargoNot(cargo);
    }

    public void editarUsuario(Long id, UsuarioDTO dto) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            usuario.setNome(dto.nome());
            usuario.setEmail(dto.email());
            usuario.setCpf(dto.cpf());
            if (dto.senha() != null && !dto.senha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(dto.senha()));
            }
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
