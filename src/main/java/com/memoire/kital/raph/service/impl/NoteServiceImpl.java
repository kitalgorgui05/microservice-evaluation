package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.feignRestClient.IEleveRestClient;
import com.memoire.kital.raph.restClient.EleveDTOReq;
import com.memoire.kital.raph.service.NoteService;
import com.memoire.kital.raph.domain.Note;
import com.memoire.kital.raph.repository.NoteRepository;
import com.memoire.kital.raph.service.dto.NoteDTO;
import com.memoire.kital.raph.service.mapper.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Note}.
 */
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final Logger log = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    private final IEleveRestClient iEleveRestClient;
    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper, IEleveRestClient iEleveRestClient) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.iEleveRestClient = iEleveRestClient;
    }

    @Override
    public NoteDTO save(NoteDTO noteDTO) {
        log.debug("Request to save Note : {}", noteDTO);
        Note note = noteMapper.toEntity(noteDTO);
        note = noteRepository.saveAndFlush(note);
        return noteMapper.toDto(note);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        return noteRepository.findAll(pageable)
            .map(noteMapper::toDto);
    }

    @Override
    public List<EleveDTOReq> getAllInscription() {
        return iEleveRestClient.getAllElevesInscrit();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NoteDTO> findOne(String id) {
        Note note=noteRepository.findById(id).orElse(null);
        EleveDTOReq eleveDTOReq = iEleveRestClient.getEleve(note.getEleve()).getBody();
        note.setEleveDTOReq(eleveDTOReq);
        return Optional.ofNullable(noteMapper.toDto(note));
    }
    @Override
    public void delete(String id) {
        log.debug("Request to delete Note : {}", id);
        noteRepository.deleteById(id);
    }
}
