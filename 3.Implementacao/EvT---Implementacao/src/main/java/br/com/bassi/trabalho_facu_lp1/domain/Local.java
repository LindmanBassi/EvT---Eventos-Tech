package br.com.bassi.trabalho_facu_lp1.domain;

import br.com.bassi.trabalho_facu_lp1.dto.LocalDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Local")
@Table(name = "locais")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int capacidade;

    @Embedded
    private Endereco endereco;


}

