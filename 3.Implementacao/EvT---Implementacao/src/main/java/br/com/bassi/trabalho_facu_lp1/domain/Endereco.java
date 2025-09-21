package br.com.bassi.trabalho_facu_lp1.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Endereco {
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private int numero;
    private String cep;


}
