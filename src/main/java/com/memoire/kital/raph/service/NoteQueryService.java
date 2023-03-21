package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import com.memoire.kital.raph.feignRestClient.IEleveRestClient;
import com.memoire.kital.raph.restClient.EleveDTOReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.Note;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.NoteRepository;
import com.memoire.kital.raph.service.dto.NoteCriteria;
import com.memoire.kital.raph.service.dto.NoteDTO;
import com.memoire.kital.raph.service.mapper.NoteMapper;

/**
 * Service for executing complex queries for {@link Note} entities in the database.
 * The main input is a {@link NoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoteDTO} or a {@link Page} of {@link NoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoteQueryService extends QueryService<Note> {

    private final Logger log = LoggerFactory.getLogger(NoteQueryService.class);

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    private final IEleveRestClient iEleveRestClient;

    public NoteQueryService(NoteRepository noteRepository, NoteMapper noteMapper, IEleveRestClient iEleveRestClient) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.iEleveRestClient = iEleveRestClient;
    }

    /**
     * Return a {@link List} of {@link NoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoteDTO> findByCriteria(NoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Note> specification = createSpecification(criteria);
        return noteMapper.toDto(noteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoteDTO> findByCriteria(NoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Note> specification = createSpecification(criteria);
        Page<Note> notePage=noteRepository.findAll(specification, page);
        for(Note n : notePage.getContent()){
            EleveDTOReq eleveDTOReq = iEleveRestClient.getEleve(n.getEleve()).getBody();
            n.setEleveDTOReq(eleveDTOReq);
        }
        return notePage.map(noteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Note> specification = createSpecification(criteria);
        return noteRepository.count(specification);
    }

    /**
     * Function to convert {@link NoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Note> createSpecification(NoteCriteria criteria) {
        Specification<Note> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Note_.id));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNote(), Note_.note));
            }
            if (criteria.getEleve() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEleve(), Note_.eleve));
            }
            if (criteria.getEvaluationId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationId(),
                    root -> root.join(Note_.evaluation, JoinType.LEFT).get(Evaluation_.id)));
            }
        }
        return specification;
    }
}
