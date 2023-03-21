package com.memoire.kital.raph.service;

import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.service.dto.TrimestreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.memoire.kital.raph.domain.Trimestre}.
 */
public interface TrimestreService {
    TrimestreDTO save(TrimestreDTO trimestreDTO);
    Page<TrimestreDTO> findAll(Pageable pageable);
    ResponseEntity<List<AnneeClient>> getAnnees();
    Optional<TrimestreDTO> findOne(String id);
    void delete(String id);
}
