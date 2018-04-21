package com.gcs.betsport.service.impl;

import com.gcs.betsport.service.ManageService;
import com.gcs.betsport.domain.Manage;
import com.gcs.betsport.repository.ManageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Manage.
 */
@Service
@Transactional
public class ManageServiceImpl implements ManageService {

    private final Logger log = LoggerFactory.getLogger(ManageServiceImpl.class);

    private final ManageRepository manageRepository;

    public ManageServiceImpl(ManageRepository manageRepository) {
        this.manageRepository = manageRepository;
    }

    /**
     * Save a manage.
     *
     * @param manage the entity to save
     * @return the persisted entity
     */
    @Override
    public Manage save(Manage manage) {
        log.debug("Request to save Manage : {}", manage);
        return manageRepository.save(manage);
    }

    /**
     * Get all the manages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Manage> findAll(Pageable pageable) {
        log.debug("Request to get all Manages");
        return manageRepository.findAll(pageable);
    }

    /**
     * Get one manage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Manage findOne(Long id) {
        log.debug("Request to get Manage : {}", id);
        return manageRepository.findOne(id);
    }

    /**
     * Delete the manage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Manage : {}", id);
        manageRepository.delete(id);
    }
}
