package com.gcs.betsport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gcs.betsport.domain.Assignment;
import com.gcs.betsport.service.AssignmentService;
import com.gcs.betsport.web.rest.errors.BadRequestAlertException;
import com.gcs.betsport.web.rest.util.HeaderUtil;
import com.gcs.betsport.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Assignment.
 */
@RestController
@RequestMapping("/api")
public class AssignmentResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentResource.class);

    private static final String ENTITY_NAME = "assignment";

    private final AssignmentService assignmentService;

    public AssignmentResource(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /**
     * POST  /assignments : Create a new assignment.
     *
     * @param assignment the assignment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assignment, or with status 400 (Bad Request) if the assignment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assignments")
    @Timed
    public ResponseEntity<Assignment> createAssignment(@Valid @RequestBody Assignment assignment) throws URISyntaxException {
        log.debug("REST request to save Assignment : {}", assignment);
        if (assignment.getId() != null) {
            throw new BadRequestAlertException("A new assignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Assignment result = assignmentService.save(assignment);
        return ResponseEntity.created(new URI("/api/assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assignments : Updates an existing assignment.
     *
     * @param assignment the assignment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assignment,
     * or with status 400 (Bad Request) if the assignment is not valid,
     * or with status 500 (Internal Server Error) if the assignment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assignments")
    @Timed
    public ResponseEntity<Assignment> updateAssignment(@Valid @RequestBody Assignment assignment) throws URISyntaxException {
        log.debug("REST request to update Assignment : {}", assignment);
        if (assignment.getId() == null) {
            return createAssignment(assignment);
        }
        Assignment result = assignmentService.save(assignment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assignment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assignments : get all the assignments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assignments in body
     */
    @GetMapping("/assignments")
    @Timed
    public ResponseEntity<List<Assignment>> getAllAssignments(Pageable pageable) {
        log.debug("REST request to get a page of Assignments");
        Page<Assignment> page = assignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assignments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assignments/:id : get the "id" assignment.
     *
     * @param id the id of the assignment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assignment, or with status 404 (Not Found)
     */
    @GetMapping("/assignments/{id}")
    @Timed
    public ResponseEntity<Assignment> getAssignment(@PathVariable Long id) {
        log.debug("REST request to get Assignment : {}", id);
        Assignment assignment = assignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assignment));
    }

    /**
     * DELETE  /assignments/:id : delete the "id" assignment.
     *
     * @param id the id of the assignment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assignments/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.debug("REST request to delete Assignment : {}", id);
        assignmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
