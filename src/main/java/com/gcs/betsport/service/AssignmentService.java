package com.gcs.betsport.service;

import com.gcs.betsport.domain.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Assignment.
 */
public interface AssignmentService {

    /**
     * Save a assignment.
     *
     * @param assignment the entity to save
     * @return the persisted entity
     */
    Assignment save(Assignment assignment);

    /**
     * Get all the assignments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Assignment> findAll(Pageable pageable);

    /**
     * Get the "id" assignment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Assignment findOne(Long id);

    /**
     * Delete the "id" assignment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
