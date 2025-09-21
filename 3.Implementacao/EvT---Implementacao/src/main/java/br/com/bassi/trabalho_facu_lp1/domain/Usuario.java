package br.com.bassi.trabalho_facu_lp1.domain;

import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumCargos;
import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumDepartamento;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String senha;

    private String nome;

    @Column(unique = true)
    private String cpf;


    @Enumerated(EnumType.STRING)
    private EnumCargos cargo;

    @Enumerated(EnumType.STRING)
    private EnumDepartamento departamento;
}
