package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Evaluation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Evaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, String>, JpaSpecificationExecutor<Evaluation> {
}
