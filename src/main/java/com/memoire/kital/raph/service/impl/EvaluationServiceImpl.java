package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.EvaluationService;
import com.memoire.kital.raph.domain.Evaluation;
import com.memoire.kital.raph.repository.EvaluationRepository;
import com.memoire.kital.raph.service.dto.EvaluationDTO;
import com.memoire.kital.raph.service.mapper.EvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link Evaluation}.
 */
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    private final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    private final EvaluationRepository evaluationRepository;

    private final EvaluationMapper evaluationMapper;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository, EvaluationMapper evaluationMapper) {
        this.evaluationRepository = evaluationRepository;
        this.evaluationMapper = evaluationMapper;
    }

    @Override
    public EvaluationDTO save(EvaluationDTO evaluationDTO) {
        log.debug("Request to save Evaluation : {}", evaluationDTO);
        Evaluation evaluation = evaluationMapper.toEntity(evaluationDTO);
        evaluation.setId(UUID.randomUUID().toString());
        evaluation = evaluationRepository.save(evaluation);
        return evaluationMapper.toDto(evaluation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Evaluations");
        return evaluationRepository.findAll(pageable)
            .map(evaluationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationDTO> findOne(String id) {
        log.debug("Request to get Evaluation : {}", id);
        return evaluationRepository.findById(id)
            .map(evaluationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Evaluation : {}", id);
        evaluationRepository.deleteById(id);
    }
}
