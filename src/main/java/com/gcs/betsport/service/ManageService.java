package com.gcs.betsport.service;

import com.gcs.betsport.domain.Manage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Manage.
 */
public interface ManageService {

    /**
     * Save a manage.
     *
     * @param manage the entity to save
     * @return the persisted entity
     */
    Manage save(Manage manage);

    /**
     * Get all the manages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Manage> findAll(Pageable pageable);

    /**
     * Get the "id" manage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Manage findOne(Long id);

    /**
     * Delete the "id" manage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
