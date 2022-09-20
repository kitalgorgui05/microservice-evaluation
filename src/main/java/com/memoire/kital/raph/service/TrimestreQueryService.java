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

import com.memoire.kital.raph.domain.Trimestre;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.TrimestreRepository;
import com.memoire.kital.raph.service.dto.TrimestreCriteria;
import com.memoire.kital.raph.service.dto.TrimestreDTO;
import com.memoire.kital.raph.service.mapper.TrimestreMapper;

/**
 * Service for executing complex queries for {@link Trimestre} entities in the database.
 * The main input is a {@link TrimestreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrimestreDTO} or a {@link Page} of {@link TrimestreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrimestreQueryService extends QueryService<Trimestre> {

    private final Logger log = LoggerFactory.getLogger(TrimestreQueryService.class);

    private final TrimestreRepository trimestreRepository;

    private final TrimestreMapper trimestreMapper;

    public TrimestreQueryService(TrimestreRepository trimestreRepository, TrimestreMapper trimestreMapper) {
        this.trimestreRepository = trimestreRepository;
        this.trimestreMapper = trimestreMapper;
    }

    /**
     * Return a {@link List} of {@link TrimestreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrimestreDTO> findByCriteria(TrimestreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Trimestre> specification = createSpecification(criteria);
        return trimestreMapper.toDto(trimestreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrimestreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrimestreDTO> findByCriteria(TrimestreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Trimestre> specification = createSpecification(criteria);
        return trimestreRepository.findAll(specification, page)
            .map(trimestreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrimestreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Trimestre> specification = createSpecification(criteria);
        return trimestreRepository.count(specification);
    }

    /**
     * Function to convert {@link TrimestreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Trimestre> createSpecification(TrimestreCriteria criteria) {
        Specification<Trimestre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Trimestre_.id));
            }
            if (criteria.getDateDebut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDebut(), Trimestre_.dateDebut));
            }
            if (criteria.getDateFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFin(), Trimestre_.dateFin));
            }
            if (criteria.getAnnee() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnnee(), Trimestre_.annee));
            }
        }
        return specification;
    }
}
