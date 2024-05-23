package com.memoire.kital.raph.service;

import com.memoire.kital.raph.restClient.EleveDTOReq;
import com.memoire.kital.raph.service.dto.NoteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Note}.
 */
public interface NoteService {

    /**
     * Save a note.
     *
     * @param noteDTO the entity to save.
     * @return the persisted entity.
     */
    NoteDTO save(NoteDTO noteDTO);

    /**
     * Get all the notes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoteDTO> findAll(Pageable pageable);
    /**
     * Get the "id" note.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoteDTO> findOne(String id);

    /**
     * Delete the "id" note.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
