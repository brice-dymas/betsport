package com.gcs.betsport.web.rest;

import com.gcs.betsport.BetsportV2App;

import com.gcs.betsport.domain.Period;
import com.gcs.betsport.repository.PeriodRepository;
import com.gcs.betsport.service.PeriodService;
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
 * Test class for the PeriodResource REST controller.
 *
 * @see PeriodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetsportV2App.class)
public class PeriodResourceIntTest {

    private static final ZonedDateTime DEFAULT_BEGINING_HOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BEGINING_HOUR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ENDING_HOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENDING_HOUR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final State DEFAULT_STATE = State.ACTIVE;
    private static final State UPDATED_STATE = State.INACTIVE;

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeriodMockMvc;

    private Period period;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodResource periodResource = new PeriodResource(periodService);
        this.restPeriodMockMvc = MockMvcBuilders.standaloneSetup(periodResource)
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
    public static Period createEntity(EntityManager em) {
        Period period = new Period()
            .beginingHour(DEFAULT_BEGINING_HOUR)
            .endingHour(DEFAULT_ENDING_HOUR)
            .state(DEFAULT_STATE);
        return period;
    }

    @Before
    public void initTest() {
        period = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriod() throws Exception {
        int databaseSizeBeforeCreate = periodRepository.findAll().size();

        // Create the Period
        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isCreated());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeCreate + 1);
        Period testPeriod = periodList.get(periodList.size() - 1);
        assertThat(testPeriod.getBeginingHour()).isEqualTo(DEFAULT_BEGINING_HOUR);
        assertThat(testPeriod.getEndingHour()).isEqualTo(DEFAULT_ENDING_HOUR);
        assertThat(testPeriod.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createPeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodRepository.findAll().size();

        // Create the Period with an existing ID
        period.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isBadRequest());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBeginingHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodRepository.findAll().size();
        // set the field null
        period.setBeginingHour(null);

        // Create the Period, which fails.

        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isBadRequest());

        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndingHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodRepository.findAll().size();
        // set the field null
        period.setEndingHour(null);

        // Create the Period, which fails.

        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isBadRequest());

        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodRepository.findAll().size();
        // set the field null
        period.setState(null);

        // Create the Period, which fails.

        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isBadRequest());

        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriods() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList
        restPeriodMockMvc.perform(get("/api/periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(period.getId().intValue())))
            .andExpect(jsonPath("$.[*].beginingHour").value(hasItem(sameInstant(DEFAULT_BEGINING_HOUR))))
            .andExpect(jsonPath("$.[*].endingHour").value(hasItem(sameInstant(DEFAULT_ENDING_HOUR))))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getPeriod() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get the period
        restPeriodMockMvc.perform(get("/api/periods/{id}", period.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(period.getId().intValue()))
            .andExpect(jsonPath("$.beginingHour").value(sameInstant(DEFAULT_BEGINING_HOUR)))
            .andExpect(jsonPath("$.endingHour").value(sameInstant(DEFAULT_ENDING_HOUR)))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriod() throws Exception {
        // Get the period
        restPeriodMockMvc.perform(get("/api/periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriod() throws Exception {
        // Initialize the database
        periodService.save(period);

        int databaseSizeBeforeUpdate = periodRepository.findAll().size();

        // Update the period
        Period updatedPeriod = periodRepository.findOne(period.getId());
        // Disconnect from session so that the updates on updatedPeriod are not directly saved in db
        em.detach(updatedPeriod);
        updatedPeriod
            .beginingHour(UPDATED_BEGINING_HOUR)
            .endingHour(UPDATED_ENDING_HOUR)
            .state(UPDATED_STATE);

        restPeriodMockMvc.perform(put("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriod)))
            .andExpect(status().isOk());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeUpdate);
        Period testPeriod = periodList.get(periodList.size() - 1);
        assertThat(testPeriod.getBeginingHour()).isEqualTo(UPDATED_BEGINING_HOUR);
        assertThat(testPeriod.getEndingHour()).isEqualTo(UPDATED_ENDING_HOUR);
        assertThat(testPeriod.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriod() throws Exception {
        int databaseSizeBeforeUpdate = periodRepository.findAll().size();

        // Create the Period

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeriodMockMvc.perform(put("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isCreated());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePeriod() throws Exception {
        // Initialize the database
        periodService.save(period);

        int databaseSizeBeforeDelete = periodRepository.findAll().size();

        // Get the period
        restPeriodMockMvc.perform(delete("/api/periods/{id}", period.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Period.class);
        Period period1 = new Period();
        period1.setId(1L);
        Period period2 = new Period();
        period2.setId(period1.getId());
        assertThat(period1).isEqualTo(period2);
        period2.setId(2L);
        assertThat(period1).isNotEqualTo(period2);
        period1.setId(null);
        assertThat(period1).isNotEqualTo(period2);
    }
}
