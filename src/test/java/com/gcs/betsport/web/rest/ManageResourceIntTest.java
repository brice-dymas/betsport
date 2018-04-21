package com.gcs.betsport.web.rest;

import com.gcs.betsport.BetsportV2App;

import com.gcs.betsport.domain.Manage;
import com.gcs.betsport.repository.ManageRepository;
import com.gcs.betsport.service.ManageService;
import com.gcs.betsport.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.gcs.betsport.web.rest.TestUtil.sameInstant;
import static com.gcs.betsport.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gcs.betsport.domain.enumeration.State;
/**
 * Test class for the ManageResource REST controller.
 *
 * @see ManageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetsportV2App.class)
public class ManageResourceIntTest {

    private static final ZonedDateTime DEFAULT_MANAGEMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MANAGEMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final State DEFAULT_STATE = State.ACTIVE;
    private static final State UPDATED_STATE = State.INACTIVE;

    @Autowired
    private ManageRepository manageRepository;

    @Autowired
    private ManageService manageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restManageMockMvc;

    private Manage manage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ManageResource manageResource = new ManageResource(manageService);
        this.restManageMockMvc = MockMvcBuilders.standaloneSetup(manageResource)
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
    public static Manage createEntity(EntityManager em) {
        Manage manage = new Manage()
            .managementDate(DEFAULT_MANAGEMENT_DATE)
            .state(DEFAULT_STATE);
        return manage;
    }

    @Before
    public void initTest() {
        manage = createEntity(em);
    }

    @Test
    @Transactional
    public void createManage() throws Exception {
        int databaseSizeBeforeCreate = manageRepository.findAll().size();

        // Create the Manage
        restManageMockMvc.perform(post("/api/manages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isCreated());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeCreate + 1);
        Manage testManage = manageList.get(manageList.size() - 1);
        assertThat(testManage.getManagementDate()).isEqualTo(DEFAULT_MANAGEMENT_DATE);
        assertThat(testManage.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createManageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = manageRepository.findAll().size();

        // Create the Manage with an existing ID
        manage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManageMockMvc.perform(post("/api/manages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isBadRequest());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkManagementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = manageRepository.findAll().size();
        // set the field null
        manage.setManagementDate(null);

        // Create the Manage, which fails.

        restManageMockMvc.perform(post("/api/manages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isBadRequest());

        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = manageRepository.findAll().size();
        // set the field null
        manage.setState(null);

        // Create the Manage, which fails.

        restManageMockMvc.perform(post("/api/manages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isBadRequest());

        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllManages() throws Exception {
        // Initialize the database
        manageRepository.saveAndFlush(manage);

        // Get all the manageList
        restManageMockMvc.perform(get("/api/manages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manage.getId().intValue())))
            .andExpect(jsonPath("$.[*].managementDate").value(hasItem(sameInstant(DEFAULT_MANAGEMENT_DATE))))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getManage() throws Exception {
        // Initialize the database
        manageRepository.saveAndFlush(manage);

        // Get the manage
        restManageMockMvc.perform(get("/api/manages/{id}", manage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(manage.getId().intValue()))
            .andExpect(jsonPath("$.managementDate").value(sameInstant(DEFAULT_MANAGEMENT_DATE)))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingManage() throws Exception {
        // Get the manage
        restManageMockMvc.perform(get("/api/manages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManage() throws Exception {
        // Initialize the database
        manageService.save(manage);

        int databaseSizeBeforeUpdate = manageRepository.findAll().size();

        // Update the manage
        Manage updatedManage = manageRepository.findOne(manage.getId());
        // Disconnect from session so that the updates on updatedManage are not directly saved in db
        em.detach(updatedManage);
        updatedManage
            .managementDate(UPDATED_MANAGEMENT_DATE)
            .state(UPDATED_STATE);

        restManageMockMvc.perform(put("/api/manages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedManage)))
            .andExpect(status().isOk());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
        Manage testManage = manageList.get(manageList.size() - 1);
        assertThat(testManage.getManagementDate()).isEqualTo(UPDATED_MANAGEMENT_DATE);
        assertThat(testManage.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingManage() throws Exception {
        int databaseSizeBeforeUpdate = manageRepository.findAll().size();

        // Create the Manage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restManageMockMvc.perform(put("/api/manages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isCreated());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteManage() throws Exception {
        // Initialize the database
        manageService.save(manage);

        int databaseSizeBeforeDelete = manageRepository.findAll().size();

        // Get the manage
        restManageMockMvc.perform(delete("/api/manages/{id}", manage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Manage.class);
        Manage manage1 = new Manage();
        manage1.setId(1L);
        Manage manage2 = new Manage();
        manage2.setId(manage1.getId());
        assertThat(manage1).isEqualTo(manage2);
        manage2.setId(2L);
        assertThat(manage1).isNotEqualTo(manage2);
        manage1.setId(null);
        assertThat(manage1).isNotEqualTo(manage2);
    }
}
