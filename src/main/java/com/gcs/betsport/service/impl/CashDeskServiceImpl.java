package com.gcs.betsport.service.impl;

import com.gcs.betsport.service.CashDeskService;
import com.gcs.betsport.domain.CashDesk;
import com.gcs.betsport.repository.CashDeskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CashDesk.
 */
@Service
@Transactional
public class CashDeskServiceImpl implements CashDeskService {

    private final Logger log = LoggerFactory.getLogger(CashDeskServiceImpl.class);

    private final CashDeskRepository cashDeskRepository;

    public CashDeskServiceImpl(CashDeskRepository cashDeskRepository) {
        this.cashDeskRepository = cashDeskRepository;
    }

    /**
     * Save a cashDesk.
     *
     * @param cashDesk the entity to save
     * @return the persisted entity
     */
    @Override
    public CashDesk save(CashDesk cashDesk) {
        log.debug("Request to save CashDesk : {}", cashDesk);
        return cashDeskRepository.save(cashDesk);
    }

    /**
     * Get all the cashDesks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CashDesk> findAll(Pageable pageable) {
        log.debug("Request to get all CashDesks");
        return cashDeskRepository.findAll(pageable);
    }

    /**
     * Get one cashDesk by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CashDesk findOne(Long id) {
        log.debug("Request to get CashDesk : {}", id);
        return cashDeskRepository.findOne(id);
    }

    /**
     * Delete the cashDesk by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CashDesk : {}", id);
        cashDeskRepository.delete(id);
    }
}
