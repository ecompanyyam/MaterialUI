package com.yam.ecompany.web.rest;

import com.yam.ecompany.domain.SalesRepresentative;
import com.yam.ecompany.repository.SalesRepresentativeRepository;
import com.yam.ecompany.service.SalesRepresentativeQueryService;
import com.yam.ecompany.service.SalesRepresentativeService;
import com.yam.ecompany.service.criteria.SalesRepresentativeCriteria;
import com.yam.ecompany.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.yam.ecompany.domain.SalesRepresentative}.
 */
@RestController
@RequestMapping("/api")
public class SalesRepresentativeResource {

    private final Logger log = LoggerFactory.getLogger(SalesRepresentativeResource.class);

    private static final String ENTITY_NAME = "salesRepresentative";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesRepresentativeService salesRepresentativeService;

    private final SalesRepresentativeRepository salesRepresentativeRepository;

    private final SalesRepresentativeQueryService salesRepresentativeQueryService;

    public SalesRepresentativeResource(
        SalesRepresentativeService salesRepresentativeService,
        SalesRepresentativeRepository salesRepresentativeRepository,
        SalesRepresentativeQueryService salesRepresentativeQueryService
    ) {
        this.salesRepresentativeService = salesRepresentativeService;
        this.salesRepresentativeRepository = salesRepresentativeRepository;
        this.salesRepresentativeQueryService = salesRepresentativeQueryService;
    }

    /**
     * {@code POST  /sales-representatives} : Create a new salesRepresentative.
     *
     * @param salesRepresentative the salesRepresentative to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesRepresentative, or with status {@code 400 (Bad Request)} if the salesRepresentative has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales-representatives")
    public ResponseEntity<SalesRepresentative> createSalesRepresentative(@Valid @RequestBody SalesRepresentative salesRepresentative)
        throws URISyntaxException {
        log.debug("REST request to save SalesRepresentative : {}", salesRepresentative);
        if (salesRepresentative.getId() != null) {
            throw new BadRequestAlertException("A new salesRepresentative cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesRepresentative result = salesRepresentativeService.save(salesRepresentative);
        return ResponseEntity
            .created(new URI("/api/sales-representatives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-representatives/:id} : Updates an existing salesRepresentative.
     *
     * @param id the id of the salesRepresentative to save.
     * @param salesRepresentative the salesRepresentative to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesRepresentative,
     * or with status {@code 400 (Bad Request)} if the salesRepresentative is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesRepresentative couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales-representatives/{id}")
    public ResponseEntity<SalesRepresentative> updateSalesRepresentative(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SalesRepresentative salesRepresentative
    ) throws URISyntaxException {
        log.debug("REST request to update SalesRepresentative : {}, {}", id, salesRepresentative);
        if (salesRepresentative.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesRepresentative.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesRepresentativeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalesRepresentative result = salesRepresentativeService.update(salesRepresentative);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesRepresentative.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sales-representatives/:id} : Partial updates given fields of an existing salesRepresentative, field will ignore if it is null
     *
     * @param id the id of the salesRepresentative to save.
     * @param salesRepresentative the salesRepresentative to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesRepresentative,
     * or with status {@code 400 (Bad Request)} if the salesRepresentative is not valid,
     * or with status {@code 404 (Not Found)} if the salesRepresentative is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesRepresentative couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sales-representatives/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesRepresentative> partialUpdateSalesRepresentative(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SalesRepresentative salesRepresentative
    ) throws URISyntaxException {
        log.debug("REST request to partial update SalesRepresentative partially : {}, {}", id, salesRepresentative);
        if (salesRepresentative.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesRepresentative.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesRepresentativeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesRepresentative> result = salesRepresentativeService.partialUpdate(salesRepresentative);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salesRepresentative.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-representatives} : get all the salesRepresentatives.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesRepresentatives in body.
     */
    @GetMapping("/sales-representatives")
    public ResponseEntity<List<SalesRepresentative>> getAllSalesRepresentatives(
        SalesRepresentativeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SalesRepresentatives by criteria: {}", criteria);

        Page<SalesRepresentative> page = salesRepresentativeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales-representatives/count} : count all the salesRepresentatives.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sales-representatives/count")
    public ResponseEntity<Long> countSalesRepresentatives(SalesRepresentativeCriteria criteria) {
        log.debug("REST request to count SalesRepresentatives by criteria: {}", criteria);
        return ResponseEntity.ok().body(salesRepresentativeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sales-representatives/:id} : get the "id" salesRepresentative.
     *
     * @param id the id of the salesRepresentative to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesRepresentative, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales-representatives/{id}")
    public ResponseEntity<SalesRepresentative> getSalesRepresentative(@PathVariable Long id) {
        log.debug("REST request to get SalesRepresentative : {}", id);
        Optional<SalesRepresentative> salesRepresentative = salesRepresentativeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesRepresentative);
    }

    /**
     * {@code DELETE  /sales-representatives/:id} : delete the "id" salesRepresentative.
     *
     * @param id the id of the salesRepresentative to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales-representatives/{id}")
    public ResponseEntity<Void> deleteSalesRepresentative(@PathVariable Long id) {
        log.debug("REST request to delete SalesRepresentative : {}", id);
        salesRepresentativeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
