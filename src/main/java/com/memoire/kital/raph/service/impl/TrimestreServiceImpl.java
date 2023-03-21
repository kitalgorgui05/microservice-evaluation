package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.feignRestClient.AnneeRestClient;
import com.memoire.kital.raph.restClient.AnneeClient;
import com.memoire.kital.raph.service.TrimestreService;
import com.memoire.kital.raph.domain.Trimestre;
import com.memoire.kital.raph.repository.TrimestreRepository;
import com.memoire.kital.raph.service.dto.TrimestreDTO;
import com.memoire.kital.raph.service.mapper.TrimestreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Trimestre}.
 */
@Service
@Transactional
public class TrimestreServiceImpl implements TrimestreService {

    private final Logger log = LoggerFactory.getLogger(TrimestreServiceImpl.class);

    private final TrimestreRepository trimestreRepository;

    private final TrimestreMapper trimestreMapper;

    private final AnneeRestClient anneeRestClient;

    public TrimestreServiceImpl(TrimestreRepository trimestreRepository, TrimestreMapper trimestreMapper, AnneeRestClient anneeRestClient) {
        this.trimestreRepository = trimestreRepository;
        this.trimestreMapper = trimestreMapper;
        this.anneeRestClient = anneeRestClient;
    }

    @Override
    public TrimestreDTO save(TrimestreDTO trimestreDTO) {
        log.debug("Request to save Trimestre : {}", trimestreDTO);
        Trimestre trimestre = trimestreMapper.toEntity(trimestreDTO);
        trimestre = trimestreRepository.save(trimestre);
        return trimestreMapper.toDto(trimestre);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrimestreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trimestres");
        return trimestreRepository.findAll(pageable)
            .map(trimestreMapper::toDto);
    }

    @Override
    public ResponseEntity<List<AnneeClient>> getAnnees() {
        return anneeRestClient.getAllAnnees();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TrimestreDTO> findOne(String id) {
        log.debug("Request to get Trimestre : {}", id);
        Trimestre trimestre=trimestreRepository.findById(id).orElse(null);
        AnneeClient anneeClient= anneeRestClient.getAnnee(trimestre.getAnnee()).getBody();
        trimestre.setAnneeClient(anneeClient);
        return Optional.ofNullable(trimestreMapper.toDto(trimestre));
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Trimestre : {}", id);
        trimestreRepository.deleteById(id);
    }
}
