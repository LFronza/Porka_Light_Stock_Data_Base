package com.porkalite.sdb.repository;

import com.porkalite.sdb.domain.Apresentacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Apresentacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {

}
