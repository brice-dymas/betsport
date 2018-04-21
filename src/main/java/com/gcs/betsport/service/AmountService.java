package com.gcs.betsport.service;

import com.gcs.betsport.domain.Amount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Amount.
 */
public interface AmountService {

    /**
     * Save a amount.
     *
     * @param amount the entity to save
     * @return the persisted entity
     */
    Amount save(Amount amount);

    /**
     * Get all the amounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Amount> findAll(Pageable pageable);

    /**
     * Get the "id" amount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Amount findOne(Long id);

    /**
     * Delete the "id" amount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
