package com.gcs.betsport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gcs.betsport.domain.TransactionType;
import com.gcs.betsport.service.TransactionTypeService;
import com.gcs.betsport.web.rest.errors.BadRequestAlertException;
import com.gcs.betsport.web.rest.util.HeaderUtil;
import com.gcs.betsport.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TransactionType.
 */
@RestController
@RequestMapping("/api")
public class TransactionTypeResource {

    private final Logger log = LoggerFactory.getLogger(TransactionTypeResource.class);

    private static final String ENTITY_NAME = "transactionType";

    private final TransactionTypeService transactionTypeService;

    public TransactionTypeResource(TransactionTypeService transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    /**
     * POST  /transaction-types : Create a new transactionType.
     *
     * @param transactionType the transactionType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionType, or with status 400 (Bad Request) if the transactionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-types")
    @Timed
    public ResponseEntity<TransactionType> createTransactionType(@Valid @RequestBody TransactionType transactionType) throws URISyntaxException {
        log.debug("REST request to save TransactionType : {}", transactionType);
        if (transactionType.getId() != null) {
            throw new BadRequestAlertException("A new transactionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionType result = transactionTypeService.save(transactionType);
        return ResponseEntity.created(new URI("/api/transaction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-types : Updates an existing transactionType.
     *
     * @param transactionType the transactionType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionType,
     * or with status 400 (Bad Request) if the transactionType is not valid,
     * or with status 500 (Internal Server Error) if the transactionType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-types")
    @Timed
    public ResponseEntity<TransactionType> updateTransactionType(@Valid @RequestBody TransactionType transactionType) throws URISyntaxException {
        log.debug("REST request to update TransactionType : {}", transactionType);
        if (transactionType.getId() == null) {
            return createTransactionType(transactionType);
        }
        TransactionType result = transactionTypeService.save(transactionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-types : get all the transactionTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of transactionTypes in body
     */
    @GetMapping("/transaction-types")
    @Timed
    public ResponseEntity<List<TransactionType>> getAllTransactionTypes(Pageable pageable) {
        log.debug("REST request to get a page of TransactionTypes");
        Page<TransactionType> page = transactionTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transaction-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transaction-types/:id : get the "id" transactionType.
     *
     * @param id the id of the transactionType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionType, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-types/{id}")
    @Timed
    public ResponseEntity<TransactionType> getTransactionType(@PathVariable Long id) {
        log.debug("REST request to get TransactionType : {}", id);
        TransactionType transactionType = transactionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transactionType));
    }

    /**
     * DELETE  /transaction-types/:id : delete the "id" transactionType.
     *
     * @param id the id of the transactionType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactionType(@PathVariable Long id) {
        log.debug("REST request to delete TransactionType : {}", id);
        transactionTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
