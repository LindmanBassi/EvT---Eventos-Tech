package br.com.bassi.trabalho_facu_lp1.repositories;

import br.com.bassi.trabalho_facu_lp1.domain.Evento;
import br.com.bassi.trabalho_facu_lp1.domain.ParticipacaoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParticipacaoEventoRepository extends JpaRepository<ParticipacaoEvento, Long> {
    int countByEventoId(Evento evento);
    List<ParticipacaoEvento> findByEventoId(Evento evento);



}
