package fr.sully.boot.web.rest;

import fr.sully.boot.SullyBootApp;

import fr.sully.boot.domain.Dependency;
import fr.sully.boot.repository.DependencyRepository;
import fr.sully.boot.service.DependencyService;
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
 * Test class for the DependencyResource REST controller.
 *
 * @see DependencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SullyBootApp.class)
public class DependencyResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private DependencyRepository dependencyRepository;

    @Autowired
    private DependencyService dependencyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDependencyMockMvc;

    private Dependency dependency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DependencyResource dependencyResource = new DependencyResource(dependencyService);
        this.restDependencyMockMvc = MockMvcBuilders.standaloneSetup(dependencyResource)
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
    public static Dependency createEntity(EntityManager em) {
        Dependency dependency = new Dependency()
            .nom(DEFAULT_NOM)
            .version(DEFAULT_VERSION);
        return dependency;
    }

    @Before
    public void initTest() {
        dependency = createEntity(em);
    }

    @Test
    @Transactional
    public void createDependency() throws Exception {
        int databaseSizeBeforeCreate = dependencyRepository.findAll().size();

        // Create the Dependency
        restDependencyMockMvc.perform(post("/api/dependencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependency)))
            .andExpect(status().isCreated());

        // Validate the Dependency in the database
        List<Dependency> dependencyList = dependencyRepository.findAll();
        assertThat(dependencyList).hasSize(databaseSizeBeforeCreate + 1);
        Dependency testDependency = dependencyList.get(dependencyList.size() - 1);
        assertThat(testDependency.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDependency.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createDependencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dependencyRepository.findAll().size();

        // Create the Dependency with an existing ID
        dependency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependencyMockMvc.perform(post("/api/dependencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependency)))
            .andExpect(status().isBadRequest());

        // Validate the Dependency in the database
        List<Dependency> dependencyList = dependencyRepository.findAll();
        assertThat(dependencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependencyRepository.findAll().size();
        // set the field null
        dependency.setNom(null);

        // Create the Dependency, which fails.

        restDependencyMockMvc.perform(post("/api/dependencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependency)))
            .andExpect(status().isBadRequest());

        List<Dependency> dependencyList = dependencyRepository.findAll();
        assertThat(dependencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDependencies() throws Exception {
        // Initialize the database
        dependencyRepository.saveAndFlush(dependency);

        // Get all the dependencyList
        restDependencyMockMvc.perform(get("/api/dependencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependency.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getDependency() throws Exception {
        // Initialize the database
        dependencyRepository.saveAndFlush(dependency);

        // Get the dependency
        restDependencyMockMvc.perform(get("/api/dependencies/{id}", dependency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dependency.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDependency() throws Exception {
        // Get the dependency
        restDependencyMockMvc.perform(get("/api/dependencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDependency() throws Exception {
        // Initialize the database
        dependencyService.save(dependency);

        int databaseSizeBeforeUpdate = dependencyRepository.findAll().size();

        // Update the dependency
        Dependency updatedDependency = dependencyRepository.findOne(dependency.getId());
        // Disconnect from session so that the updates on updatedDependency are not directly saved in db
        em.detach(updatedDependency);
        updatedDependency
            .nom(UPDATED_NOM)
            .version(UPDATED_VERSION);

        restDependencyMockMvc.perform(put("/api/dependencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDependency)))
            .andExpect(status().isOk());

        // Validate the Dependency in the database
        List<Dependency> dependencyList = dependencyRepository.findAll();
        assertThat(dependencyList).hasSize(databaseSizeBeforeUpdate);
        Dependency testDependency = dependencyList.get(dependencyList.size() - 1);
        assertThat(testDependency.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDependency.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingDependency() throws Exception {
        int databaseSizeBeforeUpdate = dependencyRepository.findAll().size();

        // Create the Dependency

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDependencyMockMvc.perform(put("/api/dependencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependency)))
            .andExpect(status().isCreated());

        // Validate the Dependency in the database
        List<Dependency> dependencyList = dependencyRepository.findAll();
        assertThat(dependencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDependency() throws Exception {
        // Initialize the database
        dependencyService.save(dependency);

        int databaseSizeBeforeDelete = dependencyRepository.findAll().size();

        // Get the dependency
        restDependencyMockMvc.perform(delete("/api/dependencies/{id}", dependency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dependency> dependencyList = dependencyRepository.findAll();
        assertThat(dependencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dependency.class);
        Dependency dependency1 = new Dependency();
        dependency1.setId(1L);
        Dependency dependency2 = new Dependency();
        dependency2.setId(dependency1.getId());
        assertThat(dependency1).isEqualTo(dependency2);
        dependency2.setId(2L);
        assertThat(dependency1).isNotEqualTo(dependency2);
        dependency1.setId(null);
        assertThat(dependency1).isNotEqualTo(dependency2);
    }
}
