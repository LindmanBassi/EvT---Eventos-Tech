package br.com.bassi.trabalho_facu_lp1.dto;

public record EnderecoDTO(
        String rua,
        String bairro,
        String cidade,
        String estado,
        int numero,
        String cep
) {}