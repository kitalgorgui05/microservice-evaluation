package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Trimestre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Trimestre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrimestreRepository extends JpaRepository<Trimestre, Long>, JpaSpecificationExecutor<Trimestre> {
}
