package com.gcs.betsport.web.rest;

import com.gcs.betsport.BetsportV2App;

import com.gcs.betsport.domain.Assignment;
import com.gcs.betsport.repository.AssignmentRepository;
import com.gcs.betsport.service.AssignmentService;
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
import java.time.LocalDate;
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
 * Test class for the AssignmentResource REST controller.
 *
 * @see AssignmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetsportV2App.class)
public class AssignmentResourceIntTest {

    private static final LocalDate DEFAULT_ASSIGNED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ASSIGNED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_ASSIGNED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ASSIGNED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final State DEFAULT_STATE = State.ACTIVE;
    private static final State UPDATED_STATE = State.INACTIVE;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssignmentMockMvc;

    private Assignment assignment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssignmentResource assignmentResource = new AssignmentResource(assignmentService);
        this.restAssignmentMockMvc = MockMvcBuilders.standaloneSetup(assignmentResource)
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
    public static Assignment createEntity(EntityManager em) {
        Assignment assignment = new Assignment()
            .assignedDate(DEFAULT_ASSIGNED_DATE)
            .assignedTime(DEFAULT_ASSIGNED_TIME)
            .state(DEFAULT_STATE);
        return assignment;
    }

    @Before
    public void initTest() {
        assignment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignment() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isCreated());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getAssignedDate()).isEqualTo(DEFAULT_ASSIGNED_DATE);
        assertThat(testAssignment.getAssignedTime()).isEqualTo(DEFAULT_ASSIGNED_TIME);
        assertThat(testAssignment.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createAssignmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment with an existing ID
        assignment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAssignedTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setAssignedTime(null);

        // Create the Assignment, which fails.

        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setState(null);

        // Create the Assignment, which fails.

        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssignments() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get all the assignmentList
        restAssignmentMockMvc.perform(get("/api/assignments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].assignedDate").value(hasItem(DEFAULT_ASSIGNED_DATE.toString())))
            .andExpect(jsonPath("$.[*].assignedTime").value(hasItem(sameInstant(DEFAULT_ASSIGNED_TIME))))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", assignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assignment.getId().intValue()))
            .andExpect(jsonPath("$.assignedDate").value(DEFAULT_ASSIGNED_DATE.toString()))
            .andExpect(jsonPath("$.assignedTime").value(sameInstant(DEFAULT_ASSIGNED_TIME)))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssignment() throws Exception {
        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignment() throws Exception {
        // Initialize the database
        assignmentService.save(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assignment
        Assignment updatedAssignment = assignmentRepository.findOne(assignment.getId());
        // Disconnect from session so that the updates on updatedAssignment are not directly saved in db
        em.detach(updatedAssignment);
        updatedAssignment
            .assignedDate(UPDATED_ASSIGNED_DATE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .state(UPDATED_STATE);

        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssignment)))
            .andExpect(status().isOk());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getAssignedDate()).isEqualTo(UPDATED_ASSIGNED_DATE);
        assertThat(testAssignment.getAssignedTime()).isEqualTo(UPDATED_ASSIGNED_TIME);
        assertThat(testAssignment.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Create the Assignment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isCreated());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssignment() throws Exception {
        // Initialize the database
        assignmentService.save(assignment);

        int databaseSizeBeforeDelete = assignmentRepository.findAll().size();

        // Get the assignment
        restAssignmentMockMvc.perform(delete("/api/assignments/{id}", assignment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assignment.class);
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        Assignment assignment2 = new Assignment();
        assignment2.setId(assignment1.getId());
        assertThat(assignment1).isEqualTo(assignment2);
        assignment2.setId(2L);
        assertThat(assignment1).isNotEqualTo(assignment2);
        assignment1.setId(null);
        assertThat(assignment1).isNotEqualTo(assignment2);
    }
}
