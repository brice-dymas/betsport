package com.gcs.betsport.service.impl;

import com.gcs.betsport.service.AmountService;
import com.gcs.betsport.domain.Amount;
import com.gcs.betsport.repository.AmountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Amount.
 */
@Service
@Transactional
public class AmountServiceImpl implements AmountService {

    private final Logger log = LoggerFactory.getLogger(AmountServiceImpl.class);

    private final AmountRepository amountRepository;

    public AmountServiceImpl(AmountRepository amountRepository) {
        this.amountRepository = amountRepository;
    }

    /**
     * Save a amount.
     *
     * @param amount the entity to save
     * @return the persisted entity
     */
    @Override
    public Amount save(Amount amount) {
        log.debug("Request to save Amount : {}", amount);
        return amountRepository.save(amount);
    }

    /**
     * Get all the amounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Amount> findAll(Pageable pageable) {
        log.debug("Request to get all Amounts");
        return amountRepository.findAll(pageable);
    }

    /**
     * Get one amount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Amount findOne(Long id) {
        log.debug("Request to get Amount : {}", id);
        return amountRepository.findOne(id);
    }

    /**
     * Delete the amount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Amount : {}", id);
        amountRepository.delete(id);
    }
}
