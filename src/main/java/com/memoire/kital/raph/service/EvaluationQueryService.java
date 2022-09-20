package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.Evaluation;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.EvaluationRepository;
import com.memoire.kital.raph.service.dto.EvaluationCriteria;
import com.memoire.kital.raph.service.dto.EvaluationDTO;
import com.memoire.kital.raph.service.mapper.EvaluationMapper;

/**
 * Service for executing complex queries for {@link Evaluation} entities in the database.
 * The main input is a {@link EvaluationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EvaluationDTO} or a {@link Page} of {@link EvaluationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EvaluationQueryService extends QueryService<Evaluation> {

    private final Logger log = LoggerFactory.getLogger(EvaluationQueryService.class);

    private final EvaluationRepository evaluationRepository;

    private final EvaluationMapper evaluationMapper;

    public EvaluationQueryService(EvaluationRepository evaluationRepository, EvaluationMapper evaluationMapper) {
        this.evaluationRepository = evaluationRepository;
        this.evaluationMapper = evaluationMapper;
    }

    /**
     * Return a {@link List} of {@link EvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EvaluationDTO> findByCriteria(EvaluationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Evaluation> specification = createSpecification(criteria);
        return evaluationMapper.toDto(evaluationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> findByCriteria(EvaluationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Evaluation> specification = createSpecification(criteria);
        return evaluationRepository.findAll(specification, page)
            .map(evaluationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EvaluationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Evaluation> specification = createSpecification(criteria);
        return evaluationRepository.count(specification);
    }

    /**
     * Function to convert {@link EvaluationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Evaluation> createSpecification(EvaluationCriteria criteria) {
        Specification<Evaluation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Evaluation_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Evaluation_.type));
            }
            if (criteria.getDateEvaluation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEvaluation(), Evaluation_.dateEvaluation));
            }
            if (criteria.getClasse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClasse(), Evaluation_.classe));
            }
            if (criteria.getMatiere() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatiere(), Evaluation_.matiere));
            }
            if (criteria.getTrimestreId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrimestreId(),
                    root -> root.join(Evaluation_.trimestre, JoinType.LEFT).get(Trimestre_.id)));
            }
        }
        return specification;
    }
}
