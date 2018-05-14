package fr.sully.boot.web.rest;

import fr.sully.boot.SullyBootApp;

import fr.sully.boot.domain.DonneePersonnelle;
import fr.sully.boot.repository.DonneePersonnelleRepository;
import fr.sully.boot.service.DonneePersonnelleService;
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

import fr.sully.boot.domain.enumeration.CategorieFonctionnelle;
import fr.sully.boot.domain.enumeration.CategorieRGPD;
import fr.sully.boot.domain.enumeration.Source;
import fr.sully.boot.domain.enumeration.Destinataire;
import fr.sully.boot.domain.enumeration.NatureTransmissionPaysTiers;
/**
 * Test class for the DonneePersonnelleResource REST controller.
 *
 * @see DonneePersonnelleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SullyBootApp.class)
public class DonneePersonnelleResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final CategorieFonctionnelle DEFAULT_CATEGORIE_FONCTIONNELLE = CategorieFonctionnelle.IDENTIFICATION_PERSONNELLE;
    private static final CategorieFonctionnelle UPDATED_CATEGORIE_FONCTIONNELLE = CategorieFonctionnelle.IDENTIFICATION_ELECTRONIQUE;

    private static final CategorieRGPD DEFAULT_CATEGORIE_RGPD = CategorieRGPD.GENETIQUE;
    private static final CategorieRGPD UPDATED_CATEGORIE_RGPD = CategorieRGPD.BIOMETRIQUE;

    private static final Source DEFAULT_SOURCE = Source.PERSONNE;
    private static final Source UPDATED_SOURCE = Source.RELATION;

    private static final Boolean DEFAULT_VULNERABLE = false;
    private static final Boolean UPDATED_VULNERABLE = true;

    private static final Destinataire DEFAULT_DESTINATAIRE = Destinataire.AUCUN;
    private static final Destinataire UPDATED_DESTINATAIRE = Destinataire.PERSONNE;

    private static final NatureTransmissionPaysTiers DEFAULT_NATURE_TRANSMISSION_PAYS_TIERS = NatureTransmissionPaysTiers.DECISION_ADEQUATION;
    private static final NatureTransmissionPaysTiers UPDATED_NATURE_TRANSMISSION_PAYS_TIERS = NatureTransmissionPaysTiers.GARANTIES_APPROPRIEES;

    private static final String DEFAULT_DELAI_CONSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_DELAI_CONSERVATION = "BBBBBBBBBB";

    private static final String DEFAULT_ECHANGE = "AAAAAAAAAA";
    private static final String UPDATED_ECHANGE = "BBBBBBBBBB";

    @Autowired
    private DonneePersonnelleRepository donneePersonnelleRepository;

    @Autowired
    private DonneePersonnelleService donneePersonnelleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDonneePersonnelleMockMvc;

    private DonneePersonnelle donneePersonnelle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DonneePersonnelleResource donneePersonnelleResource = new DonneePersonnelleResource(donneePersonnelleService);
        this.restDonneePersonnelleMockMvc = MockMvcBuilders.standaloneSetup(donneePersonnelleResource)
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
    public static DonneePersonnelle createEntity(EntityManager em) {
        DonneePersonnelle donneePersonnelle = new DonneePersonnelle()
            .nom(DEFAULT_NOM)
            .categorieFonctionnelle(DEFAULT_CATEGORIE_FONCTIONNELLE)
            .categorieRGPD(DEFAULT_CATEGORIE_RGPD)
            .source(DEFAULT_SOURCE)
            .vulnerable(DEFAULT_VULNERABLE)
            .destinataire(DEFAULT_DESTINATAIRE)
            .natureTransmissionPaysTiers(DEFAULT_NATURE_TRANSMISSION_PAYS_TIERS)
            .delaiConservation(DEFAULT_DELAI_CONSERVATION)
            .echange(DEFAULT_ECHANGE);
        return donneePersonnelle;
    }

    @Before
    public void initTest() {
        donneePersonnelle = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonneePersonnelle() throws Exception {
        int databaseSizeBeforeCreate = donneePersonnelleRepository.findAll().size();

        // Create the DonneePersonnelle
        restDonneePersonnelleMockMvc.perform(post("/api/donnee-personnelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donneePersonnelle)))
            .andExpect(status().isCreated());

        // Validate the DonneePersonnelle in the database
        List<DonneePersonnelle> donneePersonnelleList = donneePersonnelleRepository.findAll();
        assertThat(donneePersonnelleList).hasSize(databaseSizeBeforeCreate + 1);
        DonneePersonnelle testDonneePersonnelle = donneePersonnelleList.get(donneePersonnelleList.size() - 1);
        assertThat(testDonneePersonnelle.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDonneePersonnelle.getCategorieFonctionnelle()).isEqualTo(DEFAULT_CATEGORIE_FONCTIONNELLE);
        assertThat(testDonneePersonnelle.getCategorieRGPD()).isEqualTo(DEFAULT_CATEGORIE_RGPD);
        assertThat(testDonneePersonnelle.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testDonneePersonnelle.isVulnerable()).isEqualTo(DEFAULT_VULNERABLE);
        assertThat(testDonneePersonnelle.getDestinataire()).isEqualTo(DEFAULT_DESTINATAIRE);
        assertThat(testDonneePersonnelle.getNatureTransmissionPaysTiers()).isEqualTo(DEFAULT_NATURE_TRANSMISSION_PAYS_TIERS);
        assertThat(testDonneePersonnelle.getDelaiConservation()).isEqualTo(DEFAULT_DELAI_CONSERVATION);
        assertThat(testDonneePersonnelle.getEchange()).isEqualTo(DEFAULT_ECHANGE);
    }

    @Test
    @Transactional
    public void createDonneePersonnelleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donneePersonnelleRepository.findAll().size();

        // Create the DonneePersonnelle with an existing ID
        donneePersonnelle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonneePersonnelleMockMvc.perform(post("/api/donnee-personnelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donneePersonnelle)))
            .andExpect(status().isBadRequest());

        // Validate the DonneePersonnelle in the database
        List<DonneePersonnelle> donneePersonnelleList = donneePersonnelleRepository.findAll();
        assertThat(donneePersonnelleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = donneePersonnelleRepository.findAll().size();
        // set the field null
        donneePersonnelle.setNom(null);

        // Create the DonneePersonnelle, which fails.

        restDonneePersonnelleMockMvc.perform(post("/api/donnee-personnelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donneePersonnelle)))
            .andExpect(status().isBadRequest());

        List<DonneePersonnelle> donneePersonnelleList = donneePersonnelleRepository.findAll();
        assertThat(donneePersonnelleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelaiConservationIsRequired() throws Exception {
        int databaseSizeBeforeTest = donneePersonnelleRepository.findAll().size();
        // set the field null
        donneePersonnelle.setDelaiConservation(null);

        // Create the DonneePersonnelle, which fails.

        restDonneePersonnelleMockMvc.perform(post("/api/donnee-personnelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donneePersonnelle)))
            .andExpect(status().isBadRequest());

        List<DonneePersonnelle> donneePersonnelleList = donneePersonnelleRepository.findAll();
        assertThat(donneePersonnelleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDonneePersonnelles() throws Exception {
        // Initialize the database
        donneePersonnelleRepository.saveAndFlush(donneePersonnelle);

        // Get all the donneePersonnelleList
        restDonneePersonnelleMockMvc.perform(get("/api/donnee-personnelles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donneePersonnelle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].categorieFonctionnelle").value(hasItem(DEFAULT_CATEGORIE_FONCTIONNELLE.toString())))
            .andExpect(jsonPath("$.[*].categorieRGPD").value(hasItem(DEFAULT_CATEGORIE_RGPD.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].vulnerable").value(hasItem(DEFAULT_VULNERABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].destinataire").value(hasItem(DEFAULT_DESTINATAIRE.toString())))
            .andExpect(jsonPath("$.[*].natureTransmissionPaysTiers").value(hasItem(DEFAULT_NATURE_TRANSMISSION_PAYS_TIERS.toString())))
            .andExpect(jsonPath("$.[*].delaiConservation").value(hasItem(DEFAULT_DELAI_CONSERVATION.toString())))
            .andExpect(jsonPath("$.[*].echange").value(hasItem(DEFAULT_ECHANGE.toString())));
    }

    @Test
    @Transactional
    public void getDonneePersonnelle() throws Exception {
        // Initialize the database
        donneePersonnelleRepository.saveAndFlush(donneePersonnelle);

        // Get the donneePersonnelle
        restDonneePersonnelleMockMvc.perform(get("/api/donnee-personnelles/{id}", donneePersonnelle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donneePersonnelle.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.categorieFonctionnelle").value(DEFAULT_CATEGORIE_FONCTIONNELLE.toString()))
            .andExpect(jsonPath("$.categorieRGPD").value(DEFAULT_CATEGORIE_RGPD.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.vulnerable").value(DEFAULT_VULNERABLE.booleanValue()))
            .andExpect(jsonPath("$.destinataire").value(DEFAULT_DESTINATAIRE.toString()))
            .andExpect(jsonPath("$.natureTransmissionPaysTiers").value(DEFAULT_NATURE_TRANSMISSION_PAYS_TIERS.toString()))
            .andExpect(jsonPath("$.delaiConservation").value(DEFAULT_DELAI_CONSERVATION.toString()))
            .andExpect(jsonPath("$.echange").value(DEFAULT_ECHANGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDonneePersonnelle() throws Exception {
        // Get the donneePersonnelle
        restDonneePersonnelleMockMvc.perform(get("/api/donnee-personnelles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonneePersonnelle() throws Exception {
        // Initialize the database
        donneePersonnelleService.save(donneePersonnelle);

        int databaseSizeBeforeUpdate = donneePersonnelleRepository.findAll().size();

        // Update the donneePersonnelle
        DonneePersonnelle updatedDonneePersonnelle = donneePersonnelleRepository.findOne(donneePersonnelle.getId());
        // Disconnect from session so that the updates on updatedDonneePersonnelle are not directly saved in db
        em.detach(updatedDonneePersonnelle);
        updatedDonneePersonnelle
            .nom(UPDATED_NOM)
            .categorieFonctionnelle(UPDATED_CATEGORIE_FONCTIONNELLE)
            .categorieRGPD(UPDATED_CATEGORIE_RGPD)
            .source(UPDATED_SOURCE)
            .vulnerable(UPDATED_VULNERABLE)
            .destinataire(UPDATED_DESTINATAIRE)
            .natureTransmissionPaysTiers(UPDATED_NATURE_TRANSMISSION_PAYS_TIERS)
            .delaiConservation(UPDATED_DELAI_CONSERVATION)
            .echange(UPDATED_ECHANGE);

        restDonneePersonnelleMockMvc.perform(put("/api/donnee-personnelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonneePersonnelle)))
            .andExpect(status().isOk());

        // Validate the DonneePersonnelle in the database
        List<DonneePersonnelle> donneePersonnelleList = donneePersonnelleRepository.findAll();
        assertThat(donneePersonnelleList).hasSize(databaseSizeBeforeUpdate);
        DonneePersonnelle testDonneePersonnelle = donneePersonnelleList.get(donneePersonnelleList.size() - 1);
        assertThat(testDonneePersonnelle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDonneePersonnelle.getCategorieFonctionnelle()).isEqualTo(UPDATED_CATEGORIE_FONCTIONNELLE);
        assertThat(testDonneePersonnelle.getCategorieRGPD()).isEqualTo(UPDATED_CATEGORIE_RGPD);
        assertThat(testDonneePersonnelle.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testDonneePersonnelle.isVulnerable()).isEqualTo(UPDATED_VULNERABLE);
        assertThat(testDonneePersonnelle.getDestinataire()).isEqualTo(UPDATED_DESTINATAIRE);
        assertThat(testDonneePersonnelle.getNatureTransmissionPaysTiers()).isEqualTo(UPDATED_NATURE_TRANSMISSION_PAYS_TIERS);
        assertThat(testDonneePersonnelle.getDelaiConservation()).isEqualTo(UPDATED_DELAI_CONSERVATION);
        assertThat(testDonneePersonnelle.getEchange()).isEqualTo(UPDATED_ECHANGE);
    }

    @Test
    @Transactional
    public void updateNonExistingDonneePersonnelle() throws Exception {
        int databaseSizeBeforeUpdate = donneePersonnelleRepository.findAll().size();

        // Create the DonneePersonnelle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDonneePersonnelleMockMvc.perform(put("/api/donnee-personnelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donneePersonnelle)))
            .andExpect(status().isCreated());

        // Validate the DonneePersonnelle in the database
        List<DonneePersonnelle> donneePersonnelleList = donneePersonnelleRepository.findAll();
        assertThat(donneePersonnelleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDonneePersonnelle() throws Exception {
        // Initialize the database
        donneePersonnelleService.save(donneePersonnelle);

        int databaseSizeBeforeDelete = donneePersonnelleRepository.findAll().size();

        // Get the donneePersonnelle
        restDonneePersonnelleMockMvc.perform(delete("/api/donnee-personnelles/{id}", donneePersonnelle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DonneePersonnelle> donneePersonnelleList = donneePersonnelleRepository.findAll();
        assertThat(donneePersonnelleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonneePersonnelle.class);
        DonneePersonnelle donneePersonnelle1 = new DonneePersonnelle();
        donneePersonnelle1.setId(1L);
        DonneePersonnelle donneePersonnelle2 = new DonneePersonnelle();
        donneePersonnelle2.setId(donneePersonnelle1.getId());
        assertThat(donneePersonnelle1).isEqualTo(donneePersonnelle2);
        donneePersonnelle2.setId(2L);
        assertThat(donneePersonnelle1).isNotEqualTo(donneePersonnelle2);
        donneePersonnelle1.setId(null);
        assertThat(donneePersonnelle1).isNotEqualTo(donneePersonnelle2);
    }
}
