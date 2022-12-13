package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.TrimestreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Trimestre}.
 */
public interface TrimestreService {

    /**
     * Save a trimestre.
     *
     * @param trimestreDTO the entity to save.
     * @return the persisted entity.
     */
    TrimestreDTO save(TrimestreDTO trimestreDTO);

    /**
     * Get all the trimestres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrimestreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" trimestre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrimestreDTO> findOne(String id);

    /**
     * Delete the "id" trimestre.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
