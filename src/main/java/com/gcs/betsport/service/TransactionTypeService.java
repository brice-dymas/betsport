package com.gcs.betsport.service;

import com.gcs.betsport.domain.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TransactionType.
 */
public interface TransactionTypeService {

    /**
     * Save a transactionType.
     *
     * @param transactionType the entity to save
     * @return the persisted entity
     */
    TransactionType save(TransactionType transactionType);

    /**
     * Get all the transactionTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TransactionType> findAll(Pageable pageable);

    /**
     * Get the "id" transactionType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TransactionType findOne(Long id);

    /**
     * Delete the "id" transactionType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
