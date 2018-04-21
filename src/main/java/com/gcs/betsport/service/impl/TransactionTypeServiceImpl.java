package com.gcs.betsport.service.impl;

import com.gcs.betsport.service.TransactionTypeService;
import com.gcs.betsport.domain.TransactionType;
import com.gcs.betsport.repository.TransactionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TransactionType.
 */
@Service
@Transactional
public class TransactionTypeServiceImpl implements TransactionTypeService {

    private final Logger log = LoggerFactory.getLogger(TransactionTypeServiceImpl.class);

    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeServiceImpl(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    /**
     * Save a transactionType.
     *
     * @param transactionType the entity to save
     * @return the persisted entity
     */
    @Override
    public TransactionType save(TransactionType transactionType) {
        log.debug("Request to save TransactionType : {}", transactionType);
        return transactionTypeRepository.save(transactionType);
    }

    /**
     * Get all the transactionTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionType> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionTypes");
        return transactionTypeRepository.findAll(pageable);
    }

    /**
     * Get one transactionType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TransactionType findOne(Long id) {
        log.debug("Request to get TransactionType : {}", id);
        return transactionTypeRepository.findOne(id);
    }

    /**
     * Delete the transactionType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionType : {}", id);
        transactionTypeRepository.delete(id);
    }
}
