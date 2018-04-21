package com.gcs.betsport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gcs.betsport.domain.Manage;
import com.gcs.betsport.service.ManageService;
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
 * REST controller for managing Manage.
 */
@RestController
@RequestMapping("/api")
public class ManageResource {

    private final Logger log = LoggerFactory.getLogger(ManageResource.class);

    private static final String ENTITY_NAME = "manage";

    private final ManageService manageService;

    public ManageResource(ManageService manageService) {
        this.manageService = manageService;
    }

    /**
     * POST  /manages : Create a new manage.
     *
     * @param manage the manage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manage, or with status 400 (Bad Request) if the manage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/manages")
    @Timed
    public ResponseEntity<Manage> createManage(@Valid @RequestBody Manage manage) throws URISyntaxException {
        log.debug("REST request to save Manage : {}", manage);
        if (manage.getId() != null) {
            throw new BadRequestAlertException("A new manage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Manage result = manageService.save(manage);
        return ResponseEntity.created(new URI("/api/manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /manages : Updates an existing manage.
     *
     * @param manage the manage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated manage,
     * or with status 400 (Bad Request) if the manage is not valid,
     * or with status 500 (Internal Server Error) if the manage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/manages")
    @Timed
    public ResponseEntity<Manage> updateManage(@Valid @RequestBody Manage manage) throws URISyntaxException {
        log.debug("REST request to update Manage : {}", manage);
        if (manage.getId() == null) {
            return createManage(manage);
        }
        Manage result = manageService.save(manage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, manage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /manages : get all the manages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of manages in body
     */
    @GetMapping("/manages")
    @Timed
    public ResponseEntity<List<Manage>> getAllManages(Pageable pageable) {
        log.debug("REST request to get a page of Manages");
        Page<Manage> page = manageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /manages/:id : get the "id" manage.
     *
     * @param id the id of the manage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the manage, or with status 404 (Not Found)
     */
    @GetMapping("/manages/{id}")
    @Timed
    public ResponseEntity<Manage> getManage(@PathVariable Long id) {
        log.debug("REST request to get Manage : {}", id);
        Manage manage = manageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(manage));
    }

    /**
     * DELETE  /manages/:id : delete the "id" manage.
     *
     * @param id the id of the manage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/manages/{id}")
    @Timed
    public ResponseEntity<Void> deleteManage(@PathVariable Long id) {
        log.debug("REST request to delete Manage : {}", id);
        manageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
