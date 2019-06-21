package com.porkalite.sdb.repository;

import com.porkalite.sdb.domain.Grupo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Grupo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
