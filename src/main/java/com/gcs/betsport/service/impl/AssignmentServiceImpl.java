package com.gcs.betsport.service.impl;

import com.gcs.betsport.service.AssignmentService;
import com.gcs.betsport.domain.Assignment;
import com.gcs.betsport.repository.AssignmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Assignment.
 */
@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Save a assignment.
     *
     * @param assignment the entity to save
     * @return the persisted entity
     */
    @Override
    public Assignment save(Assignment assignment) {
        log.debug("Request to save Assignment : {}", assignment);
        return assignmentRepository.save(assignment);
    }

    /**
     * Get all the assignments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Assignment> findAll(Pageable pageable) {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findAll(pageable);
    }

    /**
     * Get one assignment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Assignment findOne(Long id) {
        log.debug("Request to get Assignment : {}", id);
        return assignmentRepository.findOne(id);
    }

    /**
     * Delete the assignment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assignment : {}", id);
        assignmentRepository.delete(id);
    }
}
