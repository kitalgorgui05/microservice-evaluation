package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.EvaluationApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Evaluation;
import com.memoire.kital.raph.domain.Trimestre;
import com.memoire.kital.raph.repository.EvaluationRepository;
import com.memoire.kital.raph.service.EvaluationService;
import com.memoire.kital.raph.service.dto.EvaluationDTO;
import com.memoire.kital.raph.service.mapper.EvaluationMapper;
import com.memoire.kital.raph.service.dto.EvaluationCriteria;
import com.memoire.kital.raph.service.EvaluationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EvaluationResource} REST controller.
 */
@SpringBootTest(classes = { EvaluationApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class EvaluationResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_EVALUATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_EVALUATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CLASSE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_MATIERE = "AAAAAAAAAA";
    private static final String UPDATED_MATIERE = "BBBBBBBBBB";

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private EvaluationQueryService evaluationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationMockMvc;

    private Evaluation evaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evaluation createEntity(EntityManager em) {
        Evaluation evaluation = new Evaluation()
            .type(DEFAULT_TYPE)
            .dateEvaluation(DEFAULT_DATE_EVALUATION)
            .classe(DEFAULT_CLASSE)
            .matiere(DEFAULT_MATIERE);
        return evaluation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evaluation createUpdatedEntity(EntityManager em) {
        Evaluation evaluation = new Evaluation()
            .type(UPDATED_TYPE)
            .dateEvaluation(UPDATED_DATE_EVALUATION)
            .classe(UPDATED_CLASSE)
            .matiere(UPDATED_MATIERE);
        return evaluation;
    }

    @BeforeEach
    public void initTest() {
        evaluation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluation() throws Exception {
        int databaseSizeBeforeCreate = evaluationRepository.findAll().size();
        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);
        restEvaluationMockMvc.perform(post("/api/evaluations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isCreated());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeCreate + 1);
        Evaluation testEvaluation = evaluationList.get(evaluationList.size() - 1);
        assertThat(testEvaluation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvaluation.getDateEvaluation()).isEqualTo(DEFAULT_DATE_EVALUATION);
        assertThat(testEvaluation.getClasse()).isEqualTo(DEFAULT_CLASSE);
        assertThat(testEvaluation.getMatiere()).isEqualTo(DEFAULT_MATIERE);
    }

    @Test
    @Transactional
    public void createEvaluationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluationRepository.findAll().size();

        // Create the Evaluation with an existing ID
        evaluation.setId(1L);
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationMockMvc.perform(post("/api/evaluations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationRepository.findAll().size();
        // set the field null
        evaluation.setType(null);

        // Create the Evaluation, which fails.
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);


        restEvaluationMockMvc.perform(post("/api/evaluations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateEvaluationIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationRepository.findAll().size();
        // set the field null
        evaluation.setDateEvaluation(null);

        // Create the Evaluation, which fails.
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);


        restEvaluationMockMvc.perform(post("/api/evaluations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationRepository.findAll().size();
        // set the field null
        evaluation.setClasse(null);

        // Create the Evaluation, which fails.
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);


        restEvaluationMockMvc.perform(post("/api/evaluations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatiereIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationRepository.findAll().size();
        // set the field null
        evaluation.setMatiere(null);

        // Create the Evaluation, which fails.
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);


        restEvaluationMockMvc.perform(post("/api/evaluations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvaluations() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList
        restEvaluationMockMvc.perform(get("/api/evaluations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateEvaluation").value(hasItem(DEFAULT_DATE_EVALUATION.toString())))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE)))
            .andExpect(jsonPath("$.[*].matiere").value(hasItem(DEFAULT_MATIERE)));
    }
    
    @Test
    @Transactional
    public void getEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get the evaluation
        restEvaluationMockMvc.perform(get("/api/evaluations/{id}", evaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluation.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.dateEvaluation").value(DEFAULT_DATE_EVALUATION.toString()))
            .andExpect(jsonPath("$.classe").value(DEFAULT_CLASSE))
            .andExpect(jsonPath("$.matiere").value(DEFAULT_MATIERE));
    }


    @Test
    @Transactional
    public void getEvaluationsByIdFiltering() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        Long id = evaluation.getId();

        defaultEvaluationShouldBeFound("id.equals=" + id);
        defaultEvaluationShouldNotBeFound("id.notEquals=" + id);

        defaultEvaluationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEvaluationShouldNotBeFound("id.greaterThan=" + id);

        defaultEvaluationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEvaluationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEvaluationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where type equals to DEFAULT_TYPE
        defaultEvaluationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the evaluationList where type equals to UPDATED_TYPE
        defaultEvaluationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where type not equals to DEFAULT_TYPE
        defaultEvaluationShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the evaluationList where type not equals to UPDATED_TYPE
        defaultEvaluationShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEvaluationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the evaluationList where type equals to UPDATED_TYPE
        defaultEvaluationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where type is not null
        defaultEvaluationShouldBeFound("type.specified=true");

        // Get all the evaluationList where type is null
        defaultEvaluationShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllEvaluationsByTypeContainsSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where type contains DEFAULT_TYPE
        defaultEvaluationShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the evaluationList where type contains UPDATED_TYPE
        defaultEvaluationShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where type does not contain DEFAULT_TYPE
        defaultEvaluationShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the evaluationList where type does not contain UPDATED_TYPE
        defaultEvaluationShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllEvaluationsByDateEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where dateEvaluation equals to DEFAULT_DATE_EVALUATION
        defaultEvaluationShouldBeFound("dateEvaluation.equals=" + DEFAULT_DATE_EVALUATION);

        // Get all the evaluationList where dateEvaluation equals to UPDATED_DATE_EVALUATION
        defaultEvaluationShouldNotBeFound("dateEvaluation.equals=" + UPDATED_DATE_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByDateEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where dateEvaluation not equals to DEFAULT_DATE_EVALUATION
        defaultEvaluationShouldNotBeFound("dateEvaluation.notEquals=" + DEFAULT_DATE_EVALUATION);

        // Get all the evaluationList where dateEvaluation not equals to UPDATED_DATE_EVALUATION
        defaultEvaluationShouldBeFound("dateEvaluation.notEquals=" + UPDATED_DATE_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByDateEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where dateEvaluation in DEFAULT_DATE_EVALUATION or UPDATED_DATE_EVALUATION
        defaultEvaluationShouldBeFound("dateEvaluation.in=" + DEFAULT_DATE_EVALUATION + "," + UPDATED_DATE_EVALUATION);

        // Get all the evaluationList where dateEvaluation equals to UPDATED_DATE_EVALUATION
        defaultEvaluationShouldNotBeFound("dateEvaluation.in=" + UPDATED_DATE_EVALUATION);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByDateEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where dateEvaluation is not null
        defaultEvaluationShouldBeFound("dateEvaluation.specified=true");

        // Get all the evaluationList where dateEvaluation is null
        defaultEvaluationShouldNotBeFound("dateEvaluation.specified=false");
    }

    @Test
    @Transactional
    public void getAllEvaluationsByClasseIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where classe equals to DEFAULT_CLASSE
        defaultEvaluationShouldBeFound("classe.equals=" + DEFAULT_CLASSE);

        // Get all the evaluationList where classe equals to UPDATED_CLASSE
        defaultEvaluationShouldNotBeFound("classe.equals=" + UPDATED_CLASSE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByClasseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where classe not equals to DEFAULT_CLASSE
        defaultEvaluationShouldNotBeFound("classe.notEquals=" + DEFAULT_CLASSE);

        // Get all the evaluationList where classe not equals to UPDATED_CLASSE
        defaultEvaluationShouldBeFound("classe.notEquals=" + UPDATED_CLASSE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByClasseIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where classe in DEFAULT_CLASSE or UPDATED_CLASSE
        defaultEvaluationShouldBeFound("classe.in=" + DEFAULT_CLASSE + "," + UPDATED_CLASSE);

        // Get all the evaluationList where classe equals to UPDATED_CLASSE
        defaultEvaluationShouldNotBeFound("classe.in=" + UPDATED_CLASSE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByClasseIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where classe is not null
        defaultEvaluationShouldBeFound("classe.specified=true");

        // Get all the evaluationList where classe is null
        defaultEvaluationShouldNotBeFound("classe.specified=false");
    }
                @Test
    @Transactional
    public void getAllEvaluationsByClasseContainsSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where classe contains DEFAULT_CLASSE
        defaultEvaluationShouldBeFound("classe.contains=" + DEFAULT_CLASSE);

        // Get all the evaluationList where classe contains UPDATED_CLASSE
        defaultEvaluationShouldNotBeFound("classe.contains=" + UPDATED_CLASSE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByClasseNotContainsSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where classe does not contain DEFAULT_CLASSE
        defaultEvaluationShouldNotBeFound("classe.doesNotContain=" + DEFAULT_CLASSE);

        // Get all the evaluationList where classe does not contain UPDATED_CLASSE
        defaultEvaluationShouldBeFound("classe.doesNotContain=" + UPDATED_CLASSE);
    }


    @Test
    @Transactional
    public void getAllEvaluationsByMatiereIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where matiere equals to DEFAULT_MATIERE
        defaultEvaluationShouldBeFound("matiere.equals=" + DEFAULT_MATIERE);

        // Get all the evaluationList where matiere equals to UPDATED_MATIERE
        defaultEvaluationShouldNotBeFound("matiere.equals=" + UPDATED_MATIERE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByMatiereIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where matiere not equals to DEFAULT_MATIERE
        defaultEvaluationShouldNotBeFound("matiere.notEquals=" + DEFAULT_MATIERE);

        // Get all the evaluationList where matiere not equals to UPDATED_MATIERE
        defaultEvaluationShouldBeFound("matiere.notEquals=" + UPDATED_MATIERE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByMatiereIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where matiere in DEFAULT_MATIERE or UPDATED_MATIERE
        defaultEvaluationShouldBeFound("matiere.in=" + DEFAULT_MATIERE + "," + UPDATED_MATIERE);

        // Get all the evaluationList where matiere equals to UPDATED_MATIERE
        defaultEvaluationShouldNotBeFound("matiere.in=" + UPDATED_MATIERE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByMatiereIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where matiere is not null
        defaultEvaluationShouldBeFound("matiere.specified=true");

        // Get all the evaluationList where matiere is null
        defaultEvaluationShouldNotBeFound("matiere.specified=false");
    }
                @Test
    @Transactional
    public void getAllEvaluationsByMatiereContainsSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where matiere contains DEFAULT_MATIERE
        defaultEvaluationShouldBeFound("matiere.contains=" + DEFAULT_MATIERE);

        // Get all the evaluationList where matiere contains UPDATED_MATIERE
        defaultEvaluationShouldNotBeFound("matiere.contains=" + UPDATED_MATIERE);
    }

    @Test
    @Transactional
    public void getAllEvaluationsByMatiereNotContainsSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluationList where matiere does not contain DEFAULT_MATIERE
        defaultEvaluationShouldNotBeFound("matiere.doesNotContain=" + DEFAULT_MATIERE);

        // Get all the evaluationList where matiere does not contain UPDATED_MATIERE
        defaultEvaluationShouldBeFound("matiere.doesNotContain=" + UPDATED_MATIERE);
    }


    @Test
    @Transactional
    public void getAllEvaluationsByTrimestreIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);
        Trimestre trimestre = TrimestreResourceIT.createEntity(em);
        em.persist(trimestre);
        em.flush();
        evaluation.setTrimestre(trimestre);
        evaluationRepository.saveAndFlush(evaluation);
        Long trimestreId = trimestre.getId();

        // Get all the evaluationList where trimestre equals to trimestreId
        defaultEvaluationShouldBeFound("trimestreId.equals=" + trimestreId);

        // Get all the evaluationList where trimestre equals to trimestreId + 1
        defaultEvaluationShouldNotBeFound("trimestreId.equals=" + (trimestreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEvaluationShouldBeFound(String filter) throws Exception {
        restEvaluationMockMvc.perform(get("/api/evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateEvaluation").value(hasItem(DEFAULT_DATE_EVALUATION.toString())))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE)))
            .andExpect(jsonPath("$.[*].matiere").value(hasItem(DEFAULT_MATIERE)));

        // Check, that the count call also returns 1
        restEvaluationMockMvc.perform(get("/api/evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEvaluationShouldNotBeFound(String filter) throws Exception {
        restEvaluationMockMvc.perform(get("/api/evaluations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEvaluationMockMvc.perform(get("/api/evaluations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluation() throws Exception {
        // Get the evaluation
        restEvaluationMockMvc.perform(get("/api/evaluations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();

        // Update the evaluation
        Evaluation updatedEvaluation = evaluationRepository.findById(evaluation.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluation are not directly saved in db
        em.detach(updatedEvaluation);
        updatedEvaluation
            .type(UPDATED_TYPE)
            .dateEvaluation(UPDATED_DATE_EVALUATION)
            .classe(UPDATED_CLASSE)
            .matiere(UPDATED_MATIERE);
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(updatedEvaluation);

        restEvaluationMockMvc.perform(put("/api/evaluations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isOk());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
        Evaluation testEvaluation = evaluationList.get(evaluationList.size() - 1);
        assertThat(testEvaluation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvaluation.getDateEvaluation()).isEqualTo(UPDATED_DATE_EVALUATION);
        assertThat(testEvaluation.getClasse()).isEqualTo(UPDATED_CLASSE);
        assertThat(testEvaluation.getMatiere()).isEqualTo(UPDATED_MATIERE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = evaluationRepository.findAll().size();

        // Create the Evaluation
        EvaluationDTO evaluationDTO = evaluationMapper.toDto(evaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationMockMvc.perform(put("/api/evaluations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evaluationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evaluation in the database
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        int databaseSizeBeforeDelete = evaluationRepository.findAll().size();

        // Delete the evaluation
        restEvaluationMockMvc.perform(delete("/api/evaluations/{id}", evaluation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evaluation> evaluationList = evaluationRepository.findAll();
        assertThat(evaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
