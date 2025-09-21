package br.com.bassi.trabalho_facu_lp1.dto;

import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumEstadoEvento;
import br.com.bassi.trabalho_facu_lp1.domain.enuns.EnumTipoEvento;

import java.util.Date;

public record EventoDTO(Long localId, EnumEstadoEvento estadoEvento, EnumTipoEvento tipoEvento, Date data, String titulo, String descricao, int vagas, Long palestranteId) {
}
