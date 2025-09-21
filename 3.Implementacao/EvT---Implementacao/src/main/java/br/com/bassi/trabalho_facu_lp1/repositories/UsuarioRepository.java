package br.com.bassi.trabalho_facu_lp1.repositories;

import br.com.bassi.trabalho_facu_lp1.domain.Usuario;
import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumCargos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    List<Usuario> findByCargo(EnumCargos cargo);
    List<Usuario> findByCargoNot(EnumCargos cargo);

}
