package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.TrimestreService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.TrimestreDTO;
import com.memoire.kital.raph.service.dto.TrimestreCriteria;
import com.memoire.kital.raph.service.TrimestreQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.memoire.kital.raph.domain.Trimestre}.
 */
@RestController
@RequestMapping("/api")
public class TrimestreResource {

    private final Logger log = LoggerFactory.getLogger(TrimestreResource.class);

    private static final String ENTITY_NAME = "evaluationTrimestre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrimestreService trimestreService;

    private final TrimestreQueryService trimestreQueryService;

    public TrimestreResource(TrimestreService trimestreService, TrimestreQueryService trimestreQueryService) {
        this.trimestreService = trimestreService;
        this.trimestreQueryService = trimestreQueryService;
    }

    /**
     * {@code POST  /trimestres} : Create a new trimestre.
     *
     * @param trimestreDTO the trimestreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trimestreDTO, or with status {@code 400 (Bad Request)} if the trimestre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trimestres")
    public ResponseEntity<TrimestreDTO> createTrimestre(@Valid @RequestBody TrimestreDTO trimestreDTO) throws URISyntaxException {
        log.debug("REST request to save Trimestre : {}", trimestreDTO);
        if (trimestreDTO.getId() != null) {
            throw new BadRequestAlertException("A new trimestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrimestreDTO result = trimestreService.save(trimestreDTO);
        return ResponseEntity.created(new URI("/api/trimestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trimestres} : Updates an existing trimestre.
     *
     * @param trimestreDTO the trimestreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trimestreDTO,
     * or with status {@code 400 (Bad Request)} if the trimestreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trimestreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trimestres")
    public ResponseEntity<TrimestreDTO> updateTrimestre(@Valid @RequestBody TrimestreDTO trimestreDTO) throws URISyntaxException {
        log.debug("REST request to update Trimestre : {}", trimestreDTO);
        if (trimestreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrimestreDTO result = trimestreService.save(trimestreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trimestreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trimestres} : get all the trimestres.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trimestres in body.
     */
    @GetMapping("/trimestres")
    public ResponseEntity<List<TrimestreDTO>> getAllTrimestres(TrimestreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Trimestres by criteria: {}", criteria);
        Page<TrimestreDTO> page = trimestreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trimestres/count} : count all the trimestres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/trimestres/count")
    public ResponseEntity<Long> countTrimestres(TrimestreCriteria criteria) {
        log.debug("REST request to count Trimestres by criteria: {}", criteria);
        return ResponseEntity.ok().body(trimestreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trimestres/:id} : get the "id" trimestre.
     *
     * @param id the id of the trimestreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trimestreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trimestres/{id}")
    public ResponseEntity<TrimestreDTO> getTrimestre(@PathVariable String id) {
        log.debug("REST request to get Trimestre : {}", id);
        Optional<TrimestreDTO> trimestreDTO = trimestreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trimestreDTO);
    }

    /**
     * {@code DELETE  /trimestres/:id} : delete the "id" trimestre.
     *
     * @param id the id of the trimestreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trimestres/{id}")
    public ResponseEntity<Void> deleteTrimestre(@PathVariable String id) {
        log.debug("REST request to delete Trimestre : {}", id);
        trimestreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
