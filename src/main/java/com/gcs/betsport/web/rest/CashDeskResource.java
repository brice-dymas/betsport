package com.gcs.betsport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gcs.betsport.domain.CashDesk;
import com.gcs.betsport.service.CashDeskService;
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
 * REST controller for managing CashDesk.
 */
@RestController
@RequestMapping("/api")
public class CashDeskResource {

    private final Logger log = LoggerFactory.getLogger(CashDeskResource.class);

    private static final String ENTITY_NAME = "cashDesk";

    private final CashDeskService cashDeskService;

    public CashDeskResource(CashDeskService cashDeskService) {
        this.cashDeskService = cashDeskService;
    }

    /**
     * POST  /cash-desks : Create a new cashDesk.
     *
     * @param cashDesk the cashDesk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cashDesk, or with status 400 (Bad Request) if the cashDesk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cash-desks")
    @Timed
    public ResponseEntity<CashDesk> createCashDesk(@Valid @RequestBody CashDesk cashDesk) throws URISyntaxException {
        log.debug("REST request to save CashDesk : {}", cashDesk);
        if (cashDesk.getId() != null) {
            throw new BadRequestAlertException("A new cashDesk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashDesk result = cashDeskService.save(cashDesk);
        return ResponseEntity.created(new URI("/api/cash-desks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cash-desks : Updates an existing cashDesk.
     *
     * @param cashDesk the cashDesk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cashDesk,
     * or with status 400 (Bad Request) if the cashDesk is not valid,
     * or with status 500 (Internal Server Error) if the cashDesk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cash-desks")
    @Timed
    public ResponseEntity<CashDesk> updateCashDesk(@Valid @RequestBody CashDesk cashDesk) throws URISyntaxException {
        log.debug("REST request to update CashDesk : {}", cashDesk);
        if (cashDesk.getId() == null) {
            return createCashDesk(cashDesk);
        }
        CashDesk result = cashDeskService.save(cashDesk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cashDesk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cash-desks : get all the cashDesks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cashDesks in body
     */
    @GetMapping("/cash-desks")
    @Timed
    public ResponseEntity<List<CashDesk>> getAllCashDesks(Pageable pageable) {
        log.debug("REST request to get a page of CashDesks");
        Page<CashDesk> page = cashDeskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cash-desks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cash-desks/:id : get the "id" cashDesk.
     *
     * @param id the id of the cashDesk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cashDesk, or with status 404 (Not Found)
     */
    @GetMapping("/cash-desks/{id}")
    @Timed
    public ResponseEntity<CashDesk> getCashDesk(@PathVariable Long id) {
        log.debug("REST request to get CashDesk : {}", id);
        CashDesk cashDesk = cashDeskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cashDesk));
    }

    /**
     * DELETE  /cash-desks/:id : delete the "id" cashDesk.
     *
     * @param id the id of the cashDesk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cash-desks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCashDesk(@PathVariable Long id) {
        log.debug("REST request to delete CashDesk : {}", id);
        cashDeskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
