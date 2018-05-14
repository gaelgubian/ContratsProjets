package fr.sully.boot.web.rest;

import fr.sully.boot.SullyBootApp;

import fr.sully.boot.domain.Outil;
import fr.sully.boot.repository.OutilRepository;
import fr.sully.boot.service.OutilService;
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

import fr.sully.boot.domain.enumeration.CategorieOutil;
/**
 * Test class for the OutilResource REST controller.
 *
 * @see OutilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SullyBootApp.class)
public class OutilResourceIntTest {

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final CategorieOutil DEFAULT_CATEGORIE = CategorieOutil.CONCEPTION;
    private static final CategorieOutil UPDATED_CATEGORIE = CategorieOutil.DEVELOPPEMENT;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private OutilRepository outilRepository;

    @Autowired
    private OutilService outilService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOutilMockMvc;

    private Outil outil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OutilResource outilResource = new OutilResource(outilService);
        this.restOutilMockMvc = MockMvcBuilders.standaloneSetup(outilResource)
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
    public static Outil createEntity(EntityManager em) {
        Outil outil = new Outil()
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .categorie(DEFAULT_CATEGORIE)
            .nom(DEFAULT_NOM)
            .url(DEFAULT_URL);
        return outil;
    }

    @Before
    public void initTest() {
        outil = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutil() throws Exception {
        int databaseSizeBeforeCreate = outilRepository.findAll().size();

        // Create the Outil
        restOutilMockMvc.perform(post("/api/outils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isCreated());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeCreate + 1);
        Outil testOutil = outilList.get(outilList.size() - 1);
        assertThat(testOutil.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testOutil.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testOutil.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testOutil.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testOutil.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createOutilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outilRepository.findAll().size();

        // Create the Outil with an existing ID
        outil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutilMockMvc.perform(post("/api/outils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isBadRequest());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCategorieIsRequired() throws Exception {
        int databaseSizeBeforeTest = outilRepository.findAll().size();
        // set the field null
        outil.setCategorie(null);

        // Create the Outil, which fails.

        restOutilMockMvc.perform(post("/api/outils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isBadRequest());

        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = outilRepository.findAll().size();
        // set the field null
        outil.setNom(null);

        // Create the Outil, which fails.

        restOutilMockMvc.perform(post("/api/outils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isBadRequest());

        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOutils() throws Exception {
        // Initialize the database
        outilRepository.saveAndFlush(outil);

        // Get all the outilList
        restOutilMockMvc.perform(get("/api/outils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outil.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getOutil() throws Exception {
        // Initialize the database
        outilRepository.saveAndFlush(outil);

        // Get the outil
        restOutilMockMvc.perform(get("/api/outils/{id}", outil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(outil.getId().intValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOutil() throws Exception {
        // Get the outil
        restOutilMockMvc.perform(get("/api/outils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutil() throws Exception {
        // Initialize the database
        outilService.save(outil);

        int databaseSizeBeforeUpdate = outilRepository.findAll().size();

        // Update the outil
        Outil updatedOutil = outilRepository.findOne(outil.getId());
        // Disconnect from session so that the updates on updatedOutil are not directly saved in db
        em.detach(updatedOutil);
        updatedOutil
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .categorie(UPDATED_CATEGORIE)
            .nom(UPDATED_NOM)
            .url(UPDATED_URL);

        restOutilMockMvc.perform(put("/api/outils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOutil)))
            .andExpect(status().isOk());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate);
        Outil testOutil = outilList.get(outilList.size() - 1);
        assertThat(testOutil.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testOutil.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testOutil.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testOutil.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testOutil.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingOutil() throws Exception {
        int databaseSizeBeforeUpdate = outilRepository.findAll().size();

        // Create the Outil

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOutilMockMvc.perform(put("/api/outils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outil)))
            .andExpect(status().isCreated());

        // Validate the Outil in the database
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOutil() throws Exception {
        // Initialize the database
        outilService.save(outil);

        int databaseSizeBeforeDelete = outilRepository.findAll().size();

        // Get the outil
        restOutilMockMvc.perform(delete("/api/outils/{id}", outil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Outil> outilList = outilRepository.findAll();
        assertThat(outilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Outil.class);
        Outil outil1 = new Outil();
        outil1.setId(1L);
        Outil outil2 = new Outil();
        outil2.setId(outil1.getId());
        assertThat(outil1).isEqualTo(outil2);
        outil2.setId(2L);
        assertThat(outil1).isNotEqualTo(outil2);
        outil1.setId(null);
        assertThat(outil1).isNotEqualTo(outil2);
    }
}
