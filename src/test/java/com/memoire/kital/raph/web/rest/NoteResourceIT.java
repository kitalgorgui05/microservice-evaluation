package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.EvaluationApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Note;
import com.memoire.kital.raph.domain.Evaluation;
import com.memoire.kital.raph.repository.NoteRepository;
import com.memoire.kital.raph.service.NoteService;
import com.memoire.kital.raph.service.dto.NoteDTO;
import com.memoire.kital.raph.service.mapper.NoteMapper;
import com.memoire.kital.raph.service.dto.NoteCriteria;
import com.memoire.kital.raph.service.NoteQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NoteResource} REST controller.
 */
@SpringBootTest(classes = { EvaluationApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class NoteResourceIT {

    private static final Double DEFAULT_NOTE = 1D;
    private static final Double UPDATED_NOTE = 2D;
    private static final Double SMALLER_NOTE = 1D - 1D;

    private static final String DEFAULT_ELEVE = "AAAAAAAAAA";
    private static final String UPDATED_ELEVE = "BBBBBBBBBB";

    private static final String DEFAULT_APPERCIATION = "AAAAAAAAAA";
    private static final String UPDATED_APPERCIATION = "BBBBBBBBBB";

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteQueryService noteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoteMockMvc;

    private Note note;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createEntity(EntityManager em) {
        Note note = new Note()
            .note(DEFAULT_NOTE)
            .eleve(DEFAULT_ELEVE)
            .apperciation(DEFAULT_APPERCIATION);
        return note;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createUpdatedEntity(EntityManager em) {
        Note note = new Note()
            .note(UPDATED_NOTE)
            .eleve(UPDATED_ELEVE)
            .apperciation(UPDATED_APPERCIATION);
        return note;
    }

    @BeforeEach
    public void initTest() {
        note = createEntity(em);
    }

    @Test
    @Transactional
    public void createNote() throws Exception {
        int databaseSizeBeforeCreate = noteRepository.findAll().size();
        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);
        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isCreated());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeCreate + 1);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testNote.getEleve()).isEqualTo(DEFAULT_ELEVE);
        assertThat(testNote.getApperciation()).isEqualTo(DEFAULT_APPERCIATION);
    }

    @Test
    @Transactional
    public void createNoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noteRepository.findAll().size();

        // Create the Note with an existing ID
        note.setId(null);
        NoteDTO noteDTO = noteMapper.toDto(note);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteRepository.findAll().size();
        // set the field null
        note.setNote(null);

        // Create the Note, which fails.
        NoteDTO noteDTO = noteMapper.toDto(note);


        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isBadRequest());

        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEleveIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteRepository.findAll().size();
        // set the field null
        note.setEleve(null);

        // Create the Note, which fails.
        NoteDTO noteDTO = noteMapper.toDto(note);


        restNoteMockMvc.perform(post("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isBadRequest());

        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotes() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList
        restNoteMockMvc.perform(get("/api/notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(note.getId())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.doubleValue())))
            .andExpect(jsonPath("$.[*].eleve").value(hasItem(DEFAULT_ELEVE)))
            .andExpect(jsonPath("$.[*].apperciation").value(hasItem(DEFAULT_APPERCIATION.toString())));
    }

    @Test
    @Transactional
    public void getNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get the note
        restNoteMockMvc.perform(get("/api/notes/{id}", note.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(note.getId()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.doubleValue()))
            .andExpect(jsonPath("$.eleve").value(DEFAULT_ELEVE))
            .andExpect(jsonPath("$.apperciation").value(DEFAULT_APPERCIATION.toString()));
    }


    @Test
    @Transactional
    public void getNotesByIdFiltering() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        String id = note.getId();

        defaultNoteShouldBeFound("id.equals=" + id);
        defaultNoteShouldNotBeFound("id.notEquals=" + id);

        defaultNoteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNoteShouldNotBeFound("id.greaterThan=" + id);

        defaultNoteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNoteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNotesByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where note equals to DEFAULT_NOTE
        defaultNoteShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the noteList where note equals to UPDATED_NOTE
        defaultNoteShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNotesByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where note not equals to DEFAULT_NOTE
        defaultNoteShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the noteList where note not equals to UPDATED_NOTE
        defaultNoteShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNotesByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultNoteShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the noteList where note equals to UPDATED_NOTE
        defaultNoteShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNotesByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where note is not null
        defaultNoteShouldBeFound("note.specified=true");

        // Get all the noteList where note is null
        defaultNoteShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotesByNoteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where note is greater than or equal to DEFAULT_NOTE
        defaultNoteShouldBeFound("note.greaterThanOrEqual=" + DEFAULT_NOTE);

        // Get all the noteList where note is greater than or equal to UPDATED_NOTE
        defaultNoteShouldNotBeFound("note.greaterThanOrEqual=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNotesByNoteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where note is less than or equal to DEFAULT_NOTE
        defaultNoteShouldBeFound("note.lessThanOrEqual=" + DEFAULT_NOTE);

        // Get all the noteList where note is less than or equal to SMALLER_NOTE
        defaultNoteShouldNotBeFound("note.lessThanOrEqual=" + SMALLER_NOTE);
    }

    @Test
    @Transactional
    public void getAllNotesByNoteIsLessThanSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where note is less than DEFAULT_NOTE
        defaultNoteShouldNotBeFound("note.lessThan=" + DEFAULT_NOTE);

        // Get all the noteList where note is less than UPDATED_NOTE
        defaultNoteShouldBeFound("note.lessThan=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNotesByNoteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where note is greater than DEFAULT_NOTE
        defaultNoteShouldNotBeFound("note.greaterThan=" + DEFAULT_NOTE);

        // Get all the noteList where note is greater than SMALLER_NOTE
        defaultNoteShouldBeFound("note.greaterThan=" + SMALLER_NOTE);
    }


    @Test
    @Transactional
    public void getAllNotesByEleveIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where eleve equals to DEFAULT_ELEVE
        defaultNoteShouldBeFound("eleve.equals=" + DEFAULT_ELEVE);

        // Get all the noteList where eleve equals to UPDATED_ELEVE
        defaultNoteShouldNotBeFound("eleve.equals=" + UPDATED_ELEVE);
    }

    @Test
    @Transactional
    public void getAllNotesByEleveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where eleve not equals to DEFAULT_ELEVE
        defaultNoteShouldNotBeFound("eleve.notEquals=" + DEFAULT_ELEVE);

        // Get all the noteList where eleve not equals to UPDATED_ELEVE
        defaultNoteShouldBeFound("eleve.notEquals=" + UPDATED_ELEVE);
    }

    @Test
    @Transactional
    public void getAllNotesByEleveIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where eleve in DEFAULT_ELEVE or UPDATED_ELEVE
        defaultNoteShouldBeFound("eleve.in=" + DEFAULT_ELEVE + "," + UPDATED_ELEVE);

        // Get all the noteList where eleve equals to UPDATED_ELEVE
        defaultNoteShouldNotBeFound("eleve.in=" + UPDATED_ELEVE);
    }

    @Test
    @Transactional
    public void getAllNotesByEleveIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where eleve is not null
        defaultNoteShouldBeFound("eleve.specified=true");

        // Get all the noteList where eleve is null
        defaultNoteShouldNotBeFound("eleve.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotesByEleveContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where eleve contains DEFAULT_ELEVE
        defaultNoteShouldBeFound("eleve.contains=" + DEFAULT_ELEVE);

        // Get all the noteList where eleve contains UPDATED_ELEVE
        defaultNoteShouldNotBeFound("eleve.contains=" + UPDATED_ELEVE);
    }

    @Test
    @Transactional
    public void getAllNotesByEleveNotContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where eleve does not contain DEFAULT_ELEVE
        defaultNoteShouldNotBeFound("eleve.doesNotContain=" + DEFAULT_ELEVE);

        // Get all the noteList where eleve does not contain UPDATED_ELEVE
        defaultNoteShouldBeFound("eleve.doesNotContain=" + UPDATED_ELEVE);
    }


    @Test
    @Transactional
    public void getAllNotesByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);
        Evaluation evaluation = EvaluationResourceIT.createEntity(em);
        em.persist(evaluation);
        em.flush();
        note.setEvaluation(evaluation);
        noteRepository.saveAndFlush(note);
        String evaluationId = evaluation.getId();

        // Get all the noteList where evaluation equals to evaluationId
        defaultNoteShouldBeFound("evaluationId.equals=" + evaluationId);

        // Get all the noteList where evaluation equals to evaluationId + 1
        defaultNoteShouldNotBeFound("evaluationId.equals=" + (evaluationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoteShouldBeFound(String filter) throws Exception {
        restNoteMockMvc.perform(get("/api/notes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(note.getId())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.doubleValue())))
            .andExpect(jsonPath("$.[*].eleve").value(hasItem(DEFAULT_ELEVE)))
            .andExpect(jsonPath("$.[*].apperciation").value(hasItem(DEFAULT_APPERCIATION.toString())));

        // Check, that the count call also returns 1
        restNoteMockMvc.perform(get("/api/notes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoteShouldNotBeFound(String filter) throws Exception {
        restNoteMockMvc.perform(get("/api/notes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoteMockMvc.perform(get("/api/notes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNote() throws Exception {
        // Get the note
        restNoteMockMvc.perform(get("/api/notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note
        Note updatedNote = noteRepository.findById(note.getId()).get();
        // Disconnect from session so that the updates on updatedNote are not directly saved in db
        em.detach(updatedNote);
        updatedNote
            .note(UPDATED_NOTE)
            .eleve(UPDATED_ELEVE)
            .apperciation(UPDATED_APPERCIATION);
        NoteDTO noteDTO = noteMapper.toDto(updatedNote);

        restNoteMockMvc.perform(put("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testNote.getEleve()).isEqualTo(UPDATED_ELEVE);
        assertThat(testNote.getApperciation()).isEqualTo(UPDATED_APPERCIATION);
    }

    @Test
    @Transactional
    public void updateNonExistingNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteMockMvc.perform(put("/api/notes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeDelete = noteRepository.findAll().size();

        // Delete the note
        restNoteMockMvc.perform(delete("/api/notes/{id}", note.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
