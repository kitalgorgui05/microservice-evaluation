package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.EvaluationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Evaluation}.
 */
public interface EvaluationService {

    /**
     * Save a evaluation.
     *
     * @param evaluationDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluationDTO save(EvaluationDTO evaluationDTO);

    /**
     * Get all the evaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" evaluation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluationDTO> findOne(String id);

    /**
     * Delete the "id" evaluation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
