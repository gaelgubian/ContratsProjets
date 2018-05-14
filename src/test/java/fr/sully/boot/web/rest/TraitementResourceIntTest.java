package fr.sully.boot.web.rest;

import fr.sully.boot.SullyBootApp;

import fr.sully.boot.domain.Traitement;
import fr.sully.boot.repository.TraitementRepository;
import fr.sully.boot.service.TraitementService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static fr.sully.boot.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.sully.boot.domain.enumeration.Finalite;
import fr.sully.boot.domain.enumeration.FondementTraitement;
import fr.sully.boot.domain.enumeration.TypeTraitement;
/**
 * Test class for the TraitementResource REST controller.
 *
 * @see TraitementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SullyBootApp.class)
public class TraitementResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Finalite DEFAULT_FINALITE = Finalite.GENERAL_ADMIN_PERSONNEL;
    private static final Finalite UPDATED_FINALITE = Finalite.GENERAL_GESTION_PERSONNEL;

    private static final FondementTraitement DEFAULT_FONDEMENT_TRAITEMENT = FondementTraitement.CONSENTEMENT;
    private static final FondementTraitement UPDATED_FONDEMENT_TRAITEMENT = FondementTraitement.NECESSITE_CONTRACTUEL;

    private static final TypeTraitement DEFAULT_TYPE_TRAITEMENT = TypeTraitement.NORMAL;
    private static final TypeTraitement UPDATED_TYPE_TRAITEMENT = TypeTraitement.PROFILAGE_PREVISION;

    private static final String DEFAULT_SOUS_TRAITANT = "AAAAAAAAAA";
    private static final String UPDATED_SOUS_TRAITANT = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGIE = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGIE = "BBBBBBBBBB";

    private static final String DEFAULT_RISQUE = "AAAAAAAAAA";
    private static final String UPDATED_RISQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MESURE_SECURITE = "AAAAAAAAAA";
    private static final String UPDATED_MESURE_SECURITE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTATION = "BBBBBBBBBB";

    private static final String DEFAULT_AIPD = "AAAAAAAAAA";
    private static final String UPDATED_AIPD = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDURE_EXERCICE_DROITS = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDURE_EXERCICE_DROITS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARQUES = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MAJ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MAJ = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TraitementRepository traitementRepository;

    @Autowired
    private TraitementService traitementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraitementMockMvc;

    private Traitement traitement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TraitementResource traitementResource = new TraitementResource(traitementService);
        this.restTraitementMockMvc = MockMvcBuilders.standaloneSetup(traitementResource)
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
    public static Traitement createEntity(EntityManager em) {
        Traitement traitement = new Traitement()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .finalite(DEFAULT_FINALITE)
            .fondementTraitement(DEFAULT_FONDEMENT_TRAITEMENT)
            .typeTraitement(DEFAULT_TYPE_TRAITEMENT)
            .sousTraitant(DEFAULT_SOUS_TRAITANT)
            .technologie(DEFAULT_TECHNOLOGIE)
            .risque(DEFAULT_RISQUE)
            .mesureSecurite(DEFAULT_MESURE_SECURITE)
            .documentation(DEFAULT_DOCUMENTATION)
            .aipd(DEFAULT_AIPD)
            .information(DEFAULT_INFORMATION)
            .procedureExerciceDroits(DEFAULT_PROCEDURE_EXERCICE_DROITS)
            .remarques(DEFAULT_REMARQUES)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .dateMAJ(DEFAULT_DATE_MAJ);
        return traitement;
    }

    @Before
    public void initTest() {
        traitement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraitement() throws Exception {
        int databaseSizeBeforeCreate = traitementRepository.findAll().size();

        // Create the Traitement
        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isCreated());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeCreate + 1);
        Traitement testTraitement = traitementList.get(traitementList.size() - 1);
        assertThat(testTraitement.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTraitement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTraitement.getFinalite()).isEqualTo(DEFAULT_FINALITE);
        assertThat(testTraitement.getFondementTraitement()).isEqualTo(DEFAULT_FONDEMENT_TRAITEMENT);
        assertThat(testTraitement.getTypeTraitement()).isEqualTo(DEFAULT_TYPE_TRAITEMENT);
        assertThat(testTraitement.getSousTraitant()).isEqualTo(DEFAULT_SOUS_TRAITANT);
        assertThat(testTraitement.getTechnologie()).isEqualTo(DEFAULT_TECHNOLOGIE);
        assertThat(testTraitement.getRisque()).isEqualTo(DEFAULT_RISQUE);
        assertThat(testTraitement.getMesureSecurite()).isEqualTo(DEFAULT_MESURE_SECURITE);
        assertThat(testTraitement.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
        assertThat(testTraitement.getAipd()).isEqualTo(DEFAULT_AIPD);
        assertThat(testTraitement.getInformation()).isEqualTo(DEFAULT_INFORMATION);
        assertThat(testTraitement.getProcedureExerciceDroits()).isEqualTo(DEFAULT_PROCEDURE_EXERCICE_DROITS);
        assertThat(testTraitement.getRemarques()).isEqualTo(DEFAULT_REMARQUES);
        assertThat(testTraitement.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testTraitement.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testTraitement.getDateMAJ()).isEqualTo(DEFAULT_DATE_MAJ);
    }

    @Test
    @Transactional
    public void createTraitementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traitementRepository.findAll().size();

        // Create the Traitement with an existing ID
        traitement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isBadRequest());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = traitementRepository.findAll().size();
        // set the field null
        traitement.setNom(null);

        // Create the Traitement, which fails.

        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isBadRequest());

        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraitements() throws Exception {
        // Initialize the database
        traitementRepository.saveAndFlush(traitement);

        // Get all the traitementList
        restTraitementMockMvc.perform(get("/api/traitements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traitement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].finalite").value(hasItem(DEFAULT_FINALITE.toString())))
            .andExpect(jsonPath("$.[*].fondementTraitement").value(hasItem(DEFAULT_FONDEMENT_TRAITEMENT.toString())))
            .andExpect(jsonPath("$.[*].typeTraitement").value(hasItem(DEFAULT_TYPE_TRAITEMENT.toString())))
            .andExpect(jsonPath("$.[*].sousTraitant").value(hasItem(DEFAULT_SOUS_TRAITANT.toString())))
            .andExpect(jsonPath("$.[*].technologie").value(hasItem(DEFAULT_TECHNOLOGIE.toString())))
            .andExpect(jsonPath("$.[*].risque").value(hasItem(DEFAULT_RISQUE.toString())))
            .andExpect(jsonPath("$.[*].mesureSecurite").value(hasItem(DEFAULT_MESURE_SECURITE.toString())))
            .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION.toString())))
            .andExpect(jsonPath("$.[*].aipd").value(hasItem(DEFAULT_AIPD.toString())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].procedureExerciceDroits").value(hasItem(DEFAULT_PROCEDURE_EXERCICE_DROITS.toString())))
            .andExpect(jsonPath("$.[*].remarques").value(hasItem(DEFAULT_REMARQUES.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].dateMAJ").value(hasItem(DEFAULT_DATE_MAJ.toString())));
    }

    @Test
    @Transactional
    public void getTraitement() throws Exception {
        // Initialize the database
        traitementRepository.saveAndFlush(traitement);

        // Get the traitement
        restTraitementMockMvc.perform(get("/api/traitements/{id}", traitement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traitement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.finalite").value(DEFAULT_FINALITE.toString()))
            .andExpect(jsonPath("$.fondementTraitement").value(DEFAULT_FONDEMENT_TRAITEMENT.toString()))
            .andExpect(jsonPath("$.typeTraitement").value(DEFAULT_TYPE_TRAITEMENT.toString()))
            .andExpect(jsonPath("$.sousTraitant").value(DEFAULT_SOUS_TRAITANT.toString()))
            .andExpect(jsonPath("$.technologie").value(DEFAULT_TECHNOLOGIE.toString()))
            .andExpect(jsonPath("$.risque").value(DEFAULT_RISQUE.toString()))
            .andExpect(jsonPath("$.mesureSecurite").value(DEFAULT_MESURE_SECURITE.toString()))
            .andExpect(jsonPath("$.documentation").value(DEFAULT_DOCUMENTATION.toString()))
            .andExpect(jsonPath("$.aipd").value(DEFAULT_AIPD.toString()))
            .andExpect(jsonPath("$.information").value(DEFAULT_INFORMATION.toString()))
            .andExpect(jsonPath("$.procedureExerciceDroits").value(DEFAULT_PROCEDURE_EXERCICE_DROITS.toString()))
            .andExpect(jsonPath("$.remarques").value(DEFAULT_REMARQUES.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.dateMAJ").value(DEFAULT_DATE_MAJ.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraitement() throws Exception {
        // Get the traitement
        restTraitementMockMvc.perform(get("/api/traitements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraitement() throws Exception {
        // Initialize the database
        traitementService.save(traitement);

        int databaseSizeBeforeUpdate = traitementRepository.findAll().size();

        // Update the traitement
        Traitement updatedTraitement = traitementRepository.findOne(traitement.getId());
        // Disconnect from session so that the updates on updatedTraitement are not directly saved in db
        em.detach(updatedTraitement);
        updatedTraitement
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .finalite(UPDATED_FINALITE)
            .fondementTraitement(UPDATED_FONDEMENT_TRAITEMENT)
            .typeTraitement(UPDATED_TYPE_TRAITEMENT)
            .sousTraitant(UPDATED_SOUS_TRAITANT)
            .technologie(UPDATED_TECHNOLOGIE)
            .risque(UPDATED_RISQUE)
            .mesureSecurite(UPDATED_MESURE_SECURITE)
            .documentation(UPDATED_DOCUMENTATION)
            .aipd(UPDATED_AIPD)
            .information(UPDATED_INFORMATION)
            .procedureExerciceDroits(UPDATED_PROCEDURE_EXERCICE_DROITS)
            .remarques(UPDATED_REMARQUES)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateMAJ(UPDATED_DATE_MAJ);

        restTraitementMockMvc.perform(put("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraitement)))
            .andExpect(status().isOk());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeUpdate);
        Traitement testTraitement = traitementList.get(traitementList.size() - 1);
        assertThat(testTraitement.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTraitement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTraitement.getFinalite()).isEqualTo(UPDATED_FINALITE);
        assertThat(testTraitement.getFondementTraitement()).isEqualTo(UPDATED_FONDEMENT_TRAITEMENT);
        assertThat(testTraitement.getTypeTraitement()).isEqualTo(UPDATED_TYPE_TRAITEMENT);
        assertThat(testTraitement.getSousTraitant()).isEqualTo(UPDATED_SOUS_TRAITANT);
        assertThat(testTraitement.getTechnologie()).isEqualTo(UPDATED_TECHNOLOGIE);
        assertThat(testTraitement.getRisque()).isEqualTo(UPDATED_RISQUE);
        assertThat(testTraitement.getMesureSecurite()).isEqualTo(UPDATED_MESURE_SECURITE);
        assertThat(testTraitement.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
        assertThat(testTraitement.getAipd()).isEqualTo(UPDATED_AIPD);
        assertThat(testTraitement.getInformation()).isEqualTo(UPDATED_INFORMATION);
        assertThat(testTraitement.getProcedureExerciceDroits()).isEqualTo(UPDATED_PROCEDURE_EXERCICE_DROITS);
        assertThat(testTraitement.getRemarques()).isEqualTo(UPDATED_REMARQUES);
        assertThat(testTraitement.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testTraitement.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testTraitement.getDateMAJ()).isEqualTo(UPDATED_DATE_MAJ);
    }

    @Test
    @Transactional
    public void updateNonExistingTraitement() throws Exception {
        int databaseSizeBeforeUpdate = traitementRepository.findAll().size();

        // Create the Traitement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraitementMockMvc.perform(put("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isCreated());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraitement() throws Exception {
        // Initialize the database
        traitementService.save(traitement);

        int databaseSizeBeforeDelete = traitementRepository.findAll().size();

        // Get the traitement
        restTraitementMockMvc.perform(delete("/api/traitements/{id}", traitement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traitement.class);
        Traitement traitement1 = new Traitement();
        traitement1.setId(1L);
        Traitement traitement2 = new Traitement();
        traitement2.setId(traitement1.getId());
        assertThat(traitement1).isEqualTo(traitement2);
        traitement2.setId(2L);
        assertThat(traitement1).isNotEqualTo(traitement2);
        traitement1.setId(null);
        assertThat(traitement1).isNotEqualTo(traitement2);
    }
}
