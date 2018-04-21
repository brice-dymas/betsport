package com.gcs.betsport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gcs.betsport.domain.Amount;
import com.gcs.betsport.service.AmountService;
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
 * REST controller for managing Amount.
 */
@RestController
@RequestMapping("/api")
public class AmountResource {

    private final Logger log = LoggerFactory.getLogger(AmountResource.class);

    private static final String ENTITY_NAME = "amount";

    private final AmountService amountService;

    public AmountResource(AmountService amountService) {
        this.amountService = amountService;
    }

    /**
     * POST  /amounts : Create a new amount.
     *
     * @param amount the amount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amount, or with status 400 (Bad Request) if the amount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amounts")
    @Timed
    public ResponseEntity<Amount> createAmount(@Valid @RequestBody Amount amount) throws URISyntaxException {
        log.debug("REST request to save Amount : {}", amount);
        if (amount.getId() != null) {
            throw new BadRequestAlertException("A new amount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Amount result = amountService.save(amount);
        return ResponseEntity.created(new URI("/api/amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amounts : Updates an existing amount.
     *
     * @param amount the amount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amount,
     * or with status 400 (Bad Request) if the amount is not valid,
     * or with status 500 (Internal Server Error) if the amount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amounts")
    @Timed
    public ResponseEntity<Amount> updateAmount(@Valid @RequestBody Amount amount) throws URISyntaxException {
        log.debug("REST request to update Amount : {}", amount);
        if (amount.getId() == null) {
            return createAmount(amount);
        }
        Amount result = amountService.save(amount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amounts : get all the amounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of amounts in body
     */
    @GetMapping("/amounts")
    @Timed
    public ResponseEntity<List<Amount>> getAllAmounts(Pageable pageable) {
        log.debug("REST request to get a page of Amounts");
        Page<Amount> page = amountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /amounts/:id : get the "id" amount.
     *
     * @param id the id of the amount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amount, or with status 404 (Not Found)
     */
    @GetMapping("/amounts/{id}")
    @Timed
    public ResponseEntity<Amount> getAmount(@PathVariable Long id) {
        log.debug("REST request to get Amount : {}", id);
        Amount amount = amountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(amount));
    }

    /**
     * DELETE  /amounts/:id : delete the "id" amount.
     *
     * @param id the id of the amount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmount(@PathVariable Long id) {
        log.debug("REST request to delete Amount : {}", id);
        amountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
