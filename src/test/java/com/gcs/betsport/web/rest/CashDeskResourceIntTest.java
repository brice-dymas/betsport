package com.gcs.betsport.web.rest;

import com.gcs.betsport.BetsportV2App;

import com.gcs.betsport.domain.CashDesk;
import com.gcs.betsport.repository.CashDeskRepository;
import com.gcs.betsport.service.CashDeskService;
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
import java.math.BigDecimal;
import java.util.List;

import static com.gcs.betsport.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gcs.betsport.domain.enumeration.State;
/**
 * Test class for the CashDeskResource REST controller.
 *
 * @see CashDeskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetsportV2App.class)
public class CashDeskResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CASH_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_CASH_AMOUNT = new BigDecimal(1);

    private static final State DEFAULT_STATE = State.ACTIVE;
    private static final State UPDATED_STATE = State.INACTIVE;

    @Autowired
    private CashDeskRepository cashDeskRepository;

    @Autowired
    private CashDeskService cashDeskService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCashDeskMockMvc;

    private CashDesk cashDesk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CashDeskResource cashDeskResource = new CashDeskResource(cashDeskService);
        this.restCashDeskMockMvc = MockMvcBuilders.standaloneSetup(cashDeskResource)
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
    public static CashDesk createEntity(EntityManager em) {
        CashDesk cashDesk = new CashDesk()
            .title(DEFAULT_TITLE)
            .cashAmount(DEFAULT_CASH_AMOUNT)
            .state(DEFAULT_STATE);
        return cashDesk;
    }

    @Before
    public void initTest() {
        cashDesk = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashDesk() throws Exception {
        int databaseSizeBeforeCreate = cashDeskRepository.findAll().size();

        // Create the CashDesk
        restCashDeskMockMvc.perform(post("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDesk)))
            .andExpect(status().isCreated());

        // Validate the CashDesk in the database
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeCreate + 1);
        CashDesk testCashDesk = cashDeskList.get(cashDeskList.size() - 1);
        assertThat(testCashDesk.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCashDesk.getCashAmount()).isEqualTo(DEFAULT_CASH_AMOUNT);
        assertThat(testCashDesk.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createCashDeskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashDeskRepository.findAll().size();

        // Create the CashDesk with an existing ID
        cashDesk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashDeskMockMvc.perform(post("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDesk)))
            .andExpect(status().isBadRequest());

        // Validate the CashDesk in the database
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashDeskRepository.findAll().size();
        // set the field null
        cashDesk.setTitle(null);

        // Create the CashDesk, which fails.

        restCashDeskMockMvc.perform(post("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDesk)))
            .andExpect(status().isBadRequest());

        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cashDeskRepository.findAll().size();
        // set the field null
        cashDesk.setState(null);

        // Create the CashDesk, which fails.

        restCashDeskMockMvc.perform(post("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDesk)))
            .andExpect(status().isBadRequest());

        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCashDesks() throws Exception {
        // Initialize the database
        cashDeskRepository.saveAndFlush(cashDesk);

        // Get all the cashDeskList
        restCashDeskMockMvc.perform(get("/api/cash-desks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashDesk.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].cashAmount").value(hasItem(DEFAULT_CASH_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getCashDesk() throws Exception {
        // Initialize the database
        cashDeskRepository.saveAndFlush(cashDesk);

        // Get the cashDesk
        restCashDeskMockMvc.perform(get("/api/cash-desks/{id}", cashDesk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cashDesk.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.cashAmount").value(DEFAULT_CASH_AMOUNT.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCashDesk() throws Exception {
        // Get the cashDesk
        restCashDeskMockMvc.perform(get("/api/cash-desks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashDesk() throws Exception {
        // Initialize the database
        cashDeskService.save(cashDesk);

        int databaseSizeBeforeUpdate = cashDeskRepository.findAll().size();

        // Update the cashDesk
        CashDesk updatedCashDesk = cashDeskRepository.findOne(cashDesk.getId());
        // Disconnect from session so that the updates on updatedCashDesk are not directly saved in db
        em.detach(updatedCashDesk);
        updatedCashDesk
            .title(UPDATED_TITLE)
            .cashAmount(UPDATED_CASH_AMOUNT)
            .state(UPDATED_STATE);

        restCashDeskMockMvc.perform(put("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCashDesk)))
            .andExpect(status().isOk());

        // Validate the CashDesk in the database
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeUpdate);
        CashDesk testCashDesk = cashDeskList.get(cashDeskList.size() - 1);
        assertThat(testCashDesk.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCashDesk.getCashAmount()).isEqualTo(UPDATED_CASH_AMOUNT);
        assertThat(testCashDesk.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCashDesk() throws Exception {
        int databaseSizeBeforeUpdate = cashDeskRepository.findAll().size();

        // Create the CashDesk

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCashDeskMockMvc.perform(put("/api/cash-desks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashDesk)))
            .andExpect(status().isCreated());

        // Validate the CashDesk in the database
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCashDesk() throws Exception {
        // Initialize the database
        cashDeskService.save(cashDesk);

        int databaseSizeBeforeDelete = cashDeskRepository.findAll().size();

        // Get the cashDesk
        restCashDeskMockMvc.perform(delete("/api/cash-desks/{id}", cashDesk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CashDesk> cashDeskList = cashDeskRepository.findAll();
        assertThat(cashDeskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashDesk.class);
        CashDesk cashDesk1 = new CashDesk();
        cashDesk1.setId(1L);
        CashDesk cashDesk2 = new CashDesk();
        cashDesk2.setId(cashDesk1.getId());
        assertThat(cashDesk1).isEqualTo(cashDesk2);
        cashDesk2.setId(2L);
        assertThat(cashDesk1).isNotEqualTo(cashDesk2);
        cashDesk1.setId(null);
        assertThat(cashDesk1).isNotEqualTo(cashDesk2);
    }
}
