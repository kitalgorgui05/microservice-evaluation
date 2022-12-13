package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.EvaluationApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Trimestre;
import com.memoire.kital.raph.repository.TrimestreRepository;
import com.memoire.kital.raph.service.TrimestreService;
import com.memoire.kital.raph.service.dto.TrimestreDTO;
import com.memoire.kital.raph.service.mapper.TrimestreMapper;
import com.memoire.kital.raph.service.dto.TrimestreCriteria;
import com.memoire.kital.raph.service.TrimestreQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrimestreResource} REST controller.
 */
@SpringBootTest(classes = { EvaluationApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TrimestreResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_DEBUT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_FIN = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ANNEE = "AAAAAAAAAA";
    private static final String UPDATED_ANNEE = "BBBBBBBBBB";

    @Autowired
    private TrimestreRepository trimestreRepository;

    @Autowired
    private TrimestreMapper trimestreMapper;

    @Autowired
    private TrimestreService trimestreService;

    @Autowired
    private TrimestreQueryService trimestreQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrimestreMockMvc;

    private Trimestre trimestre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trimestre createEntity(EntityManager em) {
        Trimestre trimestre = new Trimestre()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .annee(DEFAULT_ANNEE);
        return trimestre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trimestre createUpdatedEntity(EntityManager em) {
        Trimestre trimestre = new Trimestre()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .annee(UPDATED_ANNEE);
        return trimestre;
    }

    @BeforeEach
    public void initTest() {
        trimestre = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrimestre() throws Exception {
        int databaseSizeBeforeCreate = trimestreRepository.findAll().size();
        // Create the Trimestre
        TrimestreDTO trimestreDTO = trimestreMapper.toDto(trimestre);
        restTrimestreMockMvc.perform(post("/api/trimestres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestreDTO)))
            .andExpect(status().isCreated());

        // Validate the Trimestre in the database
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeCreate + 1);
        Trimestre testTrimestre = trimestreList.get(trimestreList.size() - 1);
        assertThat(testTrimestre.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testTrimestre.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testTrimestre.getAnnee()).isEqualTo(DEFAULT_ANNEE);
    }

    @Test
    @Transactional
    public void createTrimestreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trimestreRepository.findAll().size();

        // Create the Trimestre with an existing ID
        trimestre.setId(null);
        TrimestreDTO trimestreDTO = trimestreMapper.toDto(trimestre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrimestreMockMvc.perform(post("/api/trimestres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trimestre in the database
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = trimestreRepository.findAll().size();
        // set the field null
        trimestre.setDateDebut(null);

        // Create the Trimestre, which fails.
        TrimestreDTO trimestreDTO = trimestreMapper.toDto(trimestre);


        restTrimestreMockMvc.perform(post("/api/trimestres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestreDTO)))
            .andExpect(status().isBadRequest());

        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = trimestreRepository.findAll().size();
        // set the field null
        trimestre.setDateFin(null);

        // Create the Trimestre, which fails.
        TrimestreDTO trimestreDTO = trimestreMapper.toDto(trimestre);


        restTrimestreMockMvc.perform(post("/api/trimestres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestreDTO)))
            .andExpect(status().isBadRequest());

        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trimestreRepository.findAll().size();
        // set the field null
        trimestre.setAnnee(null);

        // Create the Trimestre, which fails.
        TrimestreDTO trimestreDTO = trimestreMapper.toDto(trimestre);


        restTrimestreMockMvc.perform(post("/api/trimestres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestreDTO)))
            .andExpect(status().isBadRequest());

        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrimestres() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList
        restTrimestreMockMvc.perform(get("/api/trimestres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trimestre.getId())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)));
    }

    @Test
    @Transactional
    public void getTrimestre() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get the trimestre
        restTrimestreMockMvc.perform(get("/api/trimestres/{id}", trimestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trimestre.getId()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE));
    }


    @Test
    @Transactional
    public void getTrimestresByIdFiltering() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        String id = trimestre.getId();

        defaultTrimestreShouldBeFound("id.equals=" + id);
        defaultTrimestreShouldNotBeFound("id.notEquals=" + id);

        defaultTrimestreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrimestreShouldNotBeFound("id.greaterThan=" + id);

        defaultTrimestreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrimestreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTrimestresByDateDebutIsEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateDebut equals to DEFAULT_DATE_DEBUT
        defaultTrimestreShouldBeFound("dateDebut.equals=" + DEFAULT_DATE_DEBUT);

        // Get all the trimestreList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultTrimestreShouldNotBeFound("dateDebut.equals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateDebutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateDebut not equals to DEFAULT_DATE_DEBUT
        defaultTrimestreShouldNotBeFound("dateDebut.notEquals=" + DEFAULT_DATE_DEBUT);

        // Get all the trimestreList where dateDebut not equals to UPDATED_DATE_DEBUT
        defaultTrimestreShouldBeFound("dateDebut.notEquals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateDebutIsInShouldWork() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateDebut in DEFAULT_DATE_DEBUT or UPDATED_DATE_DEBUT
        defaultTrimestreShouldBeFound("dateDebut.in=" + DEFAULT_DATE_DEBUT + "," + UPDATED_DATE_DEBUT);

        // Get all the trimestreList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultTrimestreShouldNotBeFound("dateDebut.in=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateDebutIsNullOrNotNull() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateDebut is not null
        defaultTrimestreShouldBeFound("dateDebut.specified=true");

        // Get all the trimestreList where dateDebut is null
        defaultTrimestreShouldNotBeFound("dateDebut.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateDebutIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateDebut is greater than or equal to DEFAULT_DATE_DEBUT
        defaultTrimestreShouldBeFound("dateDebut.greaterThanOrEqual=" + DEFAULT_DATE_DEBUT);

        // Get all the trimestreList where dateDebut is greater than or equal to UPDATED_DATE_DEBUT
        defaultTrimestreShouldNotBeFound("dateDebut.greaterThanOrEqual=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateDebutIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateDebut is less than or equal to DEFAULT_DATE_DEBUT
        defaultTrimestreShouldBeFound("dateDebut.lessThanOrEqual=" + DEFAULT_DATE_DEBUT);

        // Get all the trimestreList where dateDebut is less than or equal to SMALLER_DATE_DEBUT
        defaultTrimestreShouldNotBeFound("dateDebut.lessThanOrEqual=" + SMALLER_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateDebutIsLessThanSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateDebut is less than DEFAULT_DATE_DEBUT
        defaultTrimestreShouldNotBeFound("dateDebut.lessThan=" + DEFAULT_DATE_DEBUT);

        // Get all the trimestreList where dateDebut is less than UPDATED_DATE_DEBUT
        defaultTrimestreShouldBeFound("dateDebut.lessThan=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateDebutIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateDebut is greater than DEFAULT_DATE_DEBUT
        defaultTrimestreShouldNotBeFound("dateDebut.greaterThan=" + DEFAULT_DATE_DEBUT);

        // Get all the trimestreList where dateDebut is greater than SMALLER_DATE_DEBUT
        defaultTrimestreShouldBeFound("dateDebut.greaterThan=" + SMALLER_DATE_DEBUT);
    }


    @Test
    @Transactional
    public void getAllTrimestresByDateFinIsEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateFin equals to DEFAULT_DATE_FIN
        defaultTrimestreShouldBeFound("dateFin.equals=" + DEFAULT_DATE_FIN);

        // Get all the trimestreList where dateFin equals to UPDATED_DATE_FIN
        defaultTrimestreShouldNotBeFound("dateFin.equals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateFinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateFin not equals to DEFAULT_DATE_FIN
        defaultTrimestreShouldNotBeFound("dateFin.notEquals=" + DEFAULT_DATE_FIN);

        // Get all the trimestreList where dateFin not equals to UPDATED_DATE_FIN
        defaultTrimestreShouldBeFound("dateFin.notEquals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateFinIsInShouldWork() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateFin in DEFAULT_DATE_FIN or UPDATED_DATE_FIN
        defaultTrimestreShouldBeFound("dateFin.in=" + DEFAULT_DATE_FIN + "," + UPDATED_DATE_FIN);

        // Get all the trimestreList where dateFin equals to UPDATED_DATE_FIN
        defaultTrimestreShouldNotBeFound("dateFin.in=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateFin is not null
        defaultTrimestreShouldBeFound("dateFin.specified=true");

        // Get all the trimestreList where dateFin is null
        defaultTrimestreShouldNotBeFound("dateFin.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateFinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateFin is greater than or equal to DEFAULT_DATE_FIN
        defaultTrimestreShouldBeFound("dateFin.greaterThanOrEqual=" + DEFAULT_DATE_FIN);

        // Get all the trimestreList where dateFin is greater than or equal to UPDATED_DATE_FIN
        defaultTrimestreShouldNotBeFound("dateFin.greaterThanOrEqual=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateFinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateFin is less than or equal to DEFAULT_DATE_FIN
        defaultTrimestreShouldBeFound("dateFin.lessThanOrEqual=" + DEFAULT_DATE_FIN);

        // Get all the trimestreList where dateFin is less than or equal to SMALLER_DATE_FIN
        defaultTrimestreShouldNotBeFound("dateFin.lessThanOrEqual=" + SMALLER_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateFinIsLessThanSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateFin is less than DEFAULT_DATE_FIN
        defaultTrimestreShouldNotBeFound("dateFin.lessThan=" + DEFAULT_DATE_FIN);

        // Get all the trimestreList where dateFin is less than UPDATED_DATE_FIN
        defaultTrimestreShouldBeFound("dateFin.lessThan=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllTrimestresByDateFinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where dateFin is greater than DEFAULT_DATE_FIN
        defaultTrimestreShouldNotBeFound("dateFin.greaterThan=" + DEFAULT_DATE_FIN);

        // Get all the trimestreList where dateFin is greater than SMALLER_DATE_FIN
        defaultTrimestreShouldBeFound("dateFin.greaterThan=" + SMALLER_DATE_FIN);
    }


    @Test
    @Transactional
    public void getAllTrimestresByAnneeIsEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where annee equals to DEFAULT_ANNEE
        defaultTrimestreShouldBeFound("annee.equals=" + DEFAULT_ANNEE);

        // Get all the trimestreList where annee equals to UPDATED_ANNEE
        defaultTrimestreShouldNotBeFound("annee.equals=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllTrimestresByAnneeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where annee not equals to DEFAULT_ANNEE
        defaultTrimestreShouldNotBeFound("annee.notEquals=" + DEFAULT_ANNEE);

        // Get all the trimestreList where annee not equals to UPDATED_ANNEE
        defaultTrimestreShouldBeFound("annee.notEquals=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllTrimestresByAnneeIsInShouldWork() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where annee in DEFAULT_ANNEE or UPDATED_ANNEE
        defaultTrimestreShouldBeFound("annee.in=" + DEFAULT_ANNEE + "," + UPDATED_ANNEE);

        // Get all the trimestreList where annee equals to UPDATED_ANNEE
        defaultTrimestreShouldNotBeFound("annee.in=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllTrimestresByAnneeIsNullOrNotNull() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where annee is not null
        defaultTrimestreShouldBeFound("annee.specified=true");

        // Get all the trimestreList where annee is null
        defaultTrimestreShouldNotBeFound("annee.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrimestresByAnneeContainsSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where annee contains DEFAULT_ANNEE
        defaultTrimestreShouldBeFound("annee.contains=" + DEFAULT_ANNEE);

        // Get all the trimestreList where annee contains UPDATED_ANNEE
        defaultTrimestreShouldNotBeFound("annee.contains=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllTrimestresByAnneeNotContainsSomething() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList where annee does not contain DEFAULT_ANNEE
        defaultTrimestreShouldNotBeFound("annee.doesNotContain=" + DEFAULT_ANNEE);

        // Get all the trimestreList where annee does not contain UPDATED_ANNEE
        defaultTrimestreShouldBeFound("annee.doesNotContain=" + UPDATED_ANNEE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrimestreShouldBeFound(String filter) throws Exception {
        restTrimestreMockMvc.perform(get("/api/trimestres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trimestre.getId())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)));

        // Check, that the count call also returns 1
        restTrimestreMockMvc.perform(get("/api/trimestres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrimestreShouldNotBeFound(String filter) throws Exception {
        restTrimestreMockMvc.perform(get("/api/trimestres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrimestreMockMvc.perform(get("/api/trimestres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTrimestre() throws Exception {
        // Get the trimestre
        restTrimestreMockMvc.perform(get("/api/trimestres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrimestre() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        int databaseSizeBeforeUpdate = trimestreRepository.findAll().size();

        // Update the trimestre
        Trimestre updatedTrimestre = trimestreRepository.findById(trimestre.getId()).get();
        // Disconnect from session so that the updates on updatedTrimestre are not directly saved in db
        em.detach(updatedTrimestre);
        updatedTrimestre
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .annee(UPDATED_ANNEE);
        TrimestreDTO trimestreDTO = trimestreMapper.toDto(updatedTrimestre);

        restTrimestreMockMvc.perform(put("/api/trimestres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestreDTO)))
            .andExpect(status().isOk());

        // Validate the Trimestre in the database
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeUpdate);
        Trimestre testTrimestre = trimestreList.get(trimestreList.size() - 1);
        assertThat(testTrimestre.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testTrimestre.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testTrimestre.getAnnee()).isEqualTo(UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void updateNonExistingTrimestre() throws Exception {
        int databaseSizeBeforeUpdate = trimestreRepository.findAll().size();

        // Create the Trimestre
        TrimestreDTO trimestreDTO = trimestreMapper.toDto(trimestre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrimestreMockMvc.perform(put("/api/trimestres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trimestre in the database
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrimestre() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        int databaseSizeBeforeDelete = trimestreRepository.findAll().size();

        // Delete the trimestre
        restTrimestreMockMvc.perform(delete("/api/trimestres/{id}", trimestre.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
