package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.EvaluationService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.EvaluationDTO;
import com.memoire.kital.raph.service.dto.EvaluationCriteria;
import com.memoire.kital.raph.service.EvaluationQueryService;

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
 * REST controller for managing {@link com.memoire.kital.raph.domain.Evaluation}.
 */
@RestController
@RequestMapping("/api")
public class EvaluationResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationResource.class);

    private static final String ENTITY_NAME = "evaluationEvaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationService evaluationService;

    private final EvaluationQueryService evaluationQueryService;

    public EvaluationResource(EvaluationService evaluationService, EvaluationQueryService evaluationQueryService) {
        this.evaluationService = evaluationService;
        this.evaluationQueryService = evaluationQueryService;
    }

    /**
     * {@code POST  /evaluations} : Create a new evaluation.
     *
     * @param evaluationDTO the evaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationDTO, or with status {@code 400 (Bad Request)} if the evaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluations")
    public ResponseEntity<EvaluationDTO> createEvaluation(@Valid @RequestBody EvaluationDTO evaluationDTO) throws URISyntaxException {
        log.debug("REST request to save Evaluation : {}", evaluationDTO);
        if (evaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationDTO result = evaluationService.save(evaluationDTO);
        return ResponseEntity.created(new URI("/api/evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluations} : Updates an existing evaluation.
     *
     * @param evaluationDTO the evaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluations")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@Valid @RequestBody EvaluationDTO evaluationDTO) throws URISyntaxException {
        log.debug("REST request to update Evaluation : {}", evaluationDTO);
        if (evaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvaluationDTO result = evaluationService.save(evaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evaluations} : get all the evaluations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluations in body.
     */
    @GetMapping("/evaluations")
    public ResponseEntity<List<EvaluationDTO>> getAllEvaluations(EvaluationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Evaluations by criteria: {}", criteria);
        Page<EvaluationDTO> page = evaluationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evaluations/count} : count all the evaluations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/evaluations/count")
    public ResponseEntity<Long> countEvaluations(EvaluationCriteria criteria) {
        log.debug("REST request to count Evaluations by criteria: {}", criteria);
        return ResponseEntity.ok().body(evaluationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /evaluations/:id} : get the "id" evaluation.
     *
     * @param id the id of the evaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluations/{id}")
    public ResponseEntity<EvaluationDTO> getEvaluation(@PathVariable String id) {
        log.debug("REST request to get Evaluation : {}", id);
        Optional<EvaluationDTO> evaluationDTO = evaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationDTO);
    }

    /**
     * {@code DELETE  /evaluations/:id} : delete the "id" evaluation.
     *
     * @param id the id of the evaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluations/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable String id) {
        log.debug("REST request to delete Evaluation : {}", id);
        evaluationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
