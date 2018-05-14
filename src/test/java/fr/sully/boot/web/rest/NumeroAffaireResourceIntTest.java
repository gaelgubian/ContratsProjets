package fr.sully.boot.web.rest;

import fr.sully.boot.SullyBootApp;

import fr.sully.boot.domain.NumeroAffaire;
import fr.sully.boot.repository.NumeroAffaireRepository;
import fr.sully.boot.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.sully.boot.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NumeroAffaireResource REST controller.
 *
 * @see NumeroAffaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SullyBootApp.class)
public class NumeroAffaireResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private NumeroAffaireRepository numeroAffaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNumeroAffaireMockMvc;

    private NumeroAffaire numeroAffaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NumeroAffaireResource numeroAffaireResource = new NumeroAffaireResource(numeroAffaireRepository);
        this.restNumeroAffaireMockMvc = MockMvcBuilders.standaloneSetup(numeroAffaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumeroAffaire createEntity(EntityManager em) {
        NumeroAffaire numeroAffaire = new NumeroAffaire()
            .nom(DEFAULT_NOM);
        return numeroAffaire;
    }

    @Before
    public void initTest() {
        numeroAffaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createNumeroAffaire() throws Exception {
        int databaseSizeBeforeCreate = numeroAffaireRepository.findAll().size();

        // Create the NumeroAffaire
        restNumeroAffaireMockMvc.perform(post("/api/numero-affaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroAffaire)))
            .andExpect(status().isCreated());

        // Validate the NumeroAffaire in the database
        List<NumeroAffaire> numeroAffaireList = numeroAffaireRepository.findAll();
        assertThat(numeroAffaireList).hasSize(databaseSizeBeforeCreate + 1);
        NumeroAffaire testNumeroAffaire = numeroAffaireList.get(numeroAffaireList.size() - 1);
        assertThat(testNumeroAffaire.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createNumeroAffaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = numeroAffaireRepository.findAll().size();

        // Create the NumeroAffaire with an existing ID
        numeroAffaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumeroAffaireMockMvc.perform(post("/api/numero-affaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroAffaire)))
            .andExpect(status().isBadRequest());

        // Validate the NumeroAffaire in the database
        List<NumeroAffaire> numeroAffaireList = numeroAffaireRepository.findAll();
        assertThat(numeroAffaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = numeroAffaireRepository.findAll().size();
        // set the field null
        numeroAffaire.setNom(null);

        // Create the NumeroAffaire, which fails.

        restNumeroAffaireMockMvc.perform(post("/api/numero-affaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroAffaire)))
            .andExpect(status().isBadRequest());

        List<NumeroAffaire> numeroAffaireList = numeroAffaireRepository.findAll();
        assertThat(numeroAffaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNumeroAffaires() throws Exception {
        // Initialize the database
        numeroAffaireRepository.saveAndFlush(numeroAffaire);

        // Get all the numeroAffaireList
        restNumeroAffaireMockMvc.perform(get("/api/numero-affaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numeroAffaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void getNumeroAffaire() throws Exception {
        // Initialize the database
        numeroAffaireRepository.saveAndFlush(numeroAffaire);

        // Get the numeroAffaire
        restNumeroAffaireMockMvc.perform(get("/api/numero-affaires/{id}", numeroAffaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(numeroAffaire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNumeroAffaire() throws Exception {
        // Get the numeroAffaire
        restNumeroAffaireMockMvc.perform(get("/api/numero-affaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNumeroAffaire() throws Exception {
        // Initialize the database
        numeroAffaireRepository.saveAndFlush(numeroAffaire);
        int databaseSizeBeforeUpdate = numeroAffaireRepository.findAll().size();

        // Update the numeroAffaire
        NumeroAffaire updatedNumeroAffaire = numeroAffaireRepository.findOne(numeroAffaire.getId());
        // Disconnect from session so that the updates on updatedNumeroAffaire are not directly saved in db
        em.detach(updatedNumeroAffaire);
        updatedNumeroAffaire
            .nom(UPDATED_NOM);

        restNumeroAffaireMockMvc.perform(put("/api/numero-affaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNumeroAffaire)))
            .andExpect(status().isOk());

        // Validate the NumeroAffaire in the database
        List<NumeroAffaire> numeroAffaireList = numeroAffaireRepository.findAll();
        assertThat(numeroAffaireList).hasSize(databaseSizeBeforeUpdate);
        NumeroAffaire testNumeroAffaire = numeroAffaireList.get(numeroAffaireList.size() - 1);
        assertThat(testNumeroAffaire.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingNumeroAffaire() throws Exception {
        int databaseSizeBeforeUpdate = numeroAffaireRepository.findAll().size();

        // Create the NumeroAffaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNumeroAffaireMockMvc.perform(put("/api/numero-affaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroAffaire)))
            .andExpect(status().isCreated());

        // Validate the NumeroAffaire in the database
        List<NumeroAffaire> numeroAffaireList = numeroAffaireRepository.findAll();
        assertThat(numeroAffaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNumeroAffaire() throws Exception {
        // Initialize the database
        numeroAffaireRepository.saveAndFlush(numeroAffaire);
        int databaseSizeBeforeDelete = numeroAffaireRepository.findAll().size();

        // Get the numeroAffaire
        restNumeroAffaireMockMvc.perform(delete("/api/numero-affaires/{id}", numeroAffaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NumeroAffaire> numeroAffaireList = numeroAffaireRepository.findAll();
        assertThat(numeroAffaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumeroAffaire.class);
        NumeroAffaire numeroAffaire1 = new NumeroAffaire();
        numeroAffaire1.setId(1L);
        NumeroAffaire numeroAffaire2 = new NumeroAffaire();
        numeroAffaire2.setId(numeroAffaire1.getId());
        assertThat(numeroAffaire1).isEqualTo(numeroAffaire2);
        numeroAffaire2.setId(2L);
        assertThat(numeroAffaire1).isNotEqualTo(numeroAffaire2);
        numeroAffaire1.setId(null);
        assertThat(numeroAffaire1).isNotEqualTo(numeroAffaire2);
    }
}
