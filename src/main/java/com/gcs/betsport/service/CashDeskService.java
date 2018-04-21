package com.gcs.betsport.service;

import com.gcs.betsport.domain.CashDesk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CashDesk.
 */
public interface CashDeskService {

    /**
     * Save a cashDesk.
     *
     * @param cashDesk the entity to save
     * @return the persisted entity
     */
    CashDesk save(CashDesk cashDesk);

    /**
     * Get all the cashDesks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CashDesk> findAll(Pageable pageable);

    /**
     * Get the "id" cashDesk.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CashDesk findOne(Long id);

    /**
     * Delete the "id" cashDesk.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
