package com.porkalite.sdb.repository;

import com.porkalite.sdb.domain.Lote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

}
