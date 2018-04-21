package com.gcs.betsport.web.rest;

import com.gcs.betsport.BetsportV2App;

import com.gcs.betsport.domain.Amount;
import com.gcs.betsport.repository.AmountRepository;
import com.gcs.betsport.service.AmountService;
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
 * Test class for the AmountResource REST controller.
 *
 * @see AmountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetsportV2App.class)
public class AmountResourceIntTest {

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    private static final State DEFAULT_STATE = State.ACTIVE;
    private static final State UPDATED_STATE = State.INACTIVE;

    @Autowired
    private AmountRepository amountRepository;

    @Autowired
    private AmountService amountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmountMockMvc;

    private Amount amount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmountResource amountResource = new AmountResource(amountService);
        this.restAmountMockMvc = MockMvcBuilders.standaloneSetup(amountResource)
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
    public static Amount createEntity(EntityManager em) {
        Amount amount = new Amount()
            .value(DEFAULT_VALUE)
            .state(DEFAULT_STATE);
        return amount;
    }

    @Before
    public void initTest() {
        amount = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmount() throws Exception {
        int databaseSizeBeforeCreate = amountRepository.findAll().size();

        // Create the Amount
        restAmountMockMvc.perform(post("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isCreated());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeCreate + 1);
        Amount testAmount = amountList.get(amountList.size() - 1);
        assertThat(testAmount.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAmount.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amountRepository.findAll().size();

        // Create the Amount with an existing ID
        amount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmountMockMvc.perform(post("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isBadRequest());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = amountRepository.findAll().size();
        // set the field null
        amount.setValue(null);

        // Create the Amount, which fails.

        restAmountMockMvc.perform(post("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isBadRequest());

        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = amountRepository.findAll().size();
        // set the field null
        amount.setState(null);

        // Create the Amount, which fails.

        restAmountMockMvc.perform(post("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isBadRequest());

        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmounts() throws Exception {
        // Initialize the database
        amountRepository.saveAndFlush(amount);

        // Get all the amountList
        restAmountMockMvc.perform(get("/api/amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amount.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getAmount() throws Exception {
        // Initialize the database
        amountRepository.saveAndFlush(amount);

        // Get the amount
        restAmountMockMvc.perform(get("/api/amounts/{id}", amount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amount.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmount() throws Exception {
        // Get the amount
        restAmountMockMvc.perform(get("/api/amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmount() throws Exception {
        // Initialize the database
        amountService.save(amount);

        int databaseSizeBeforeUpdate = amountRepository.findAll().size();

        // Update the amount
        Amount updatedAmount = amountRepository.findOne(amount.getId());
        // Disconnect from session so that the updates on updatedAmount are not directly saved in db
        em.detach(updatedAmount);
        updatedAmount
            .value(UPDATED_VALUE)
            .state(UPDATED_STATE);

        restAmountMockMvc.perform(put("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmount)))
            .andExpect(status().isOk());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeUpdate);
        Amount testAmount = amountList.get(amountList.size() - 1);
        assertThat(testAmount.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAmount.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAmount() throws Exception {
        int databaseSizeBeforeUpdate = amountRepository.findAll().size();

        // Create the Amount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAmountMockMvc.perform(put("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isCreated());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAmount() throws Exception {
        // Initialize the database
        amountService.save(amount);

        int databaseSizeBeforeDelete = amountRepository.findAll().size();

        // Get the amount
        restAmountMockMvc.perform(delete("/api/amounts/{id}", amount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amount.class);
        Amount amount1 = new Amount();
        amount1.setId(1L);
        Amount amount2 = new Amount();
        amount2.setId(amount1.getId());
        assertThat(amount1).isEqualTo(amount2);
        amount2.setId(2L);
        assertThat(amount1).isNotEqualTo(amount2);
        amount1.setId(null);
        assertThat(amount1).isNotEqualTo(amount2);
    }
}
