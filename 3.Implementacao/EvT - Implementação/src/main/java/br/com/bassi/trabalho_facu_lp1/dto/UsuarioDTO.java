    package br.com.bassi.trabalho_facu_lp1.dto;

    import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumCargos;

    public record UsuarioDTO(String email, String senha, String nome, String cpf) {
    }
