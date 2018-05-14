package fr.sully.boot.web.rest;

import fr.sully.boot.SullyBootApp;

import fr.sully.boot.domain.Projet;
import fr.sully.boot.repository.ProjetRepository;
import fr.sully.boot.service.ProjetService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.sully.boot.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProjetResource REST controller.
 *
 * @see ProjetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SullyBootApp.class)
public class ProjetResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COURT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COURT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private ProjetService projetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjetMockMvc;

    private Projet projet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjetResource projetResource = new ProjetResource(projetService);
        this.restProjetMockMvc = MockMvcBuilders.standaloneSetup(projetResource)
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
    public static Projet createEntity(EntityManager em) {
        Projet projet = new Projet()
            .nom(DEFAULT_NOM)
            .nomCourt(DEFAULT_NOM_COURT)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return projet;
    }

    @Before
    public void initTest() {
        projet = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjet() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // Create the Projet
        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate + 1);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProjet.getNomCourt()).isEqualTo(DEFAULT_NOM_COURT);
        assertThat(testProjet.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProjet.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProjetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // Create the Projet with an existing ID
        projet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setNom(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomCourtIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setNomCourt(null);

        // Create the Projet, which fails.

        restProjetMockMvc.perform(post("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjets() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList
        restProjetMockMvc.perform(get("/api/projets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projet.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].nomCourt").value(hasItem(DEFAULT_NOM_COURT.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get the projet
        restProjetMockMvc.perform(get("/api/projets/{id}", projet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projet.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.nomCourt").value(DEFAULT_NOM_COURT.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingProjet() throws Exception {
        // Get the projet
        restProjetMockMvc.perform(get("/api/projets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjet() throws Exception {
        // Initialize the database
        projetService.save(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet
        Projet updatedProjet = projetRepository.findOne(projet.getId());
        // Disconnect from session so that the updates on updatedProjet are not directly saved in db
        em.detach(updatedProjet);
        updatedProjet
            .nom(UPDATED_NOM)
            .nomCourt(UPDATED_NOM_COURT)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restProjetMockMvc.perform(put("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjet)))
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProjet.getNomCourt()).isEqualTo(UPDATED_NOM_COURT);
        assertThat(testProjet.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProjet.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Create the Projet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjetMockMvc.perform(put("/api/projets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projet)))
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjet() throws Exception {
        // Initialize the database
        projetService.save(projet);

        int databaseSizeBeforeDelete = projetRepository.findAll().size();

        // Get the projet
        restProjetMockMvc.perform(delete("/api/projets/{id}", projet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projet.class);
        Projet projet1 = new Projet();
        projet1.setId(1L);
        Projet projet2 = new Projet();
        projet2.setId(projet1.getId());
        assertThat(projet1).isEqualTo(projet2);
        projet2.setId(2L);
        assertThat(projet1).isNotEqualTo(projet2);
        projet1.setId(null);
        assertThat(projet1).isNotEqualTo(projet2);
    }
}
