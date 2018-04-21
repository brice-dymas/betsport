package com.gcs.betsport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gcs.betsport.domain.Period;
import com.gcs.betsport.service.PeriodService;
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
 * REST controller for managing Period.
 */
@RestController
@RequestMapping("/api")
public class PeriodResource {

    private final Logger log = LoggerFactory.getLogger(PeriodResource.class);

    private static final String ENTITY_NAME = "period";

    private final PeriodService periodService;

    public PeriodResource(PeriodService periodService) {
        this.periodService = periodService;
    }

    /**
     * POST  /periods : Create a new period.
     *
     * @param period the period to create
     * @return the ResponseEntity with status 201 (Created) and with body the new period, or with status 400 (Bad Request) if the period has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periods")
    @Timed
    public ResponseEntity<Period> createPeriod(@Valid @RequestBody Period period) throws URISyntaxException {
        log.debug("REST request to save Period : {}", period);
        if (period.getId() != null) {
            throw new BadRequestAlertException("A new period cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Period result = periodService.save(period);
        return ResponseEntity.created(new URI("/api/periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periods : Updates an existing period.
     *
     * @param period the period to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated period,
     * or with status 400 (Bad Request) if the period is not valid,
     * or with status 500 (Internal Server Error) if the period couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periods")
    @Timed
    public ResponseEntity<Period> updatePeriod(@Valid @RequestBody Period period) throws URISyntaxException {
        log.debug("REST request to update Period : {}", period);
        if (period.getId() == null) {
            return createPeriod(period);
        }
        Period result = periodService.save(period);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, period.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periods : get all the periods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of periods in body
     */
    @GetMapping("/periods")
    @Timed
    public ResponseEntity<List<Period>> getAllPeriods(Pageable pageable) {
        log.debug("REST request to get a page of Periods");
        Page<Period> page = periodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/periods");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /periods/:id : get the "id" period.
     *
     * @param id the id of the period to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the period, or with status 404 (Not Found)
     */
    @GetMapping("/periods/{id}")
    @Timed
    public ResponseEntity<Period> getPeriod(@PathVariable Long id) {
        log.debug("REST request to get Period : {}", id);
        Period period = periodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(period));
    }

    /**
     * DELETE  /periods/:id : delete the "id" period.
     *
     * @param id the id of the period to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periods/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriod(@PathVariable Long id) {
        log.debug("REST request to delete Period : {}", id);
        periodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
