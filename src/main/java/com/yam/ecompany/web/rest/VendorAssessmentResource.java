package com.yam.ecompany.web.rest;

import com.yam.ecompany.domain.VendorAssessment;
import com.yam.ecompany.repository.VendorAssessmentRepository;
import com.yam.ecompany.service.VendorAssessmentQueryService;
import com.yam.ecompany.service.VendorAssessmentService;
import com.yam.ecompany.service.criteria.VendorAssessmentCriteria;
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
 * REST controller for managing {@link com.yam.ecompany.domain.VendorAssessment}.
 */
@RestController
@RequestMapping("/api")
public class VendorAssessmentResource {

    private final Logger log = LoggerFactory.getLogger(VendorAssessmentResource.class);

    private static final String ENTITY_NAME = "vendorAssessment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendorAssessmentService vendorAssessmentService;

    private final VendorAssessmentRepository vendorAssessmentRepository;

    private final VendorAssessmentQueryService vendorAssessmentQueryService;

    public VendorAssessmentResource(
        VendorAssessmentService vendorAssessmentService,
        VendorAssessmentRepository vendorAssessmentRepository,
        VendorAssessmentQueryService vendorAssessmentQueryService
    ) {
        this.vendorAssessmentService = vendorAssessmentService;
        this.vendorAssessmentRepository = vendorAssessmentRepository;
        this.vendorAssessmentQueryService = vendorAssessmentQueryService;
    }

    /**
     * {@code POST  /vendor-assessments} : Create a new vendorAssessment.
     *
     * @param vendorAssessment the vendorAssessment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendorAssessment, or with status {@code 400 (Bad Request)} if the vendorAssessment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vendor-assessments")
    public ResponseEntity<VendorAssessment> createVendorAssessment(@Valid @RequestBody VendorAssessment vendorAssessment)
        throws URISyntaxException {
        log.debug("REST request to save VendorAssessment : {}", vendorAssessment);
        if (vendorAssessment.getId() != null) {
            throw new BadRequestAlertException("A new vendorAssessment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendorAssessment result = vendorAssessmentService.save(vendorAssessment);
        return ResponseEntity
            .created(new URI("/api/vendor-assessments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vendor-assessments/:id} : Updates an existing vendorAssessment.
     *
     * @param id the id of the vendorAssessment to save.
     * @param vendorAssessment the vendorAssessment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendorAssessment,
     * or with status {@code 400 (Bad Request)} if the vendorAssessment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendorAssessment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vendor-assessments/{id}")
    public ResponseEntity<VendorAssessment> updateVendorAssessment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VendorAssessment vendorAssessment
    ) throws URISyntaxException {
        log.debug("REST request to update VendorAssessment : {}, {}", id, vendorAssessment);
        if (vendorAssessment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendorAssessment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendorAssessmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VendorAssessment result = vendorAssessmentService.update(vendorAssessment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendorAssessment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vendor-assessments/:id} : Partial updates given fields of an existing vendorAssessment, field will ignore if it is null
     *
     * @param id the id of the vendorAssessment to save.
     * @param vendorAssessment the vendorAssessment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendorAssessment,
     * or with status {@code 400 (Bad Request)} if the vendorAssessment is not valid,
     * or with status {@code 404 (Not Found)} if the vendorAssessment is not found,
     * or with status {@code 500 (Internal Server Error)} if the vendorAssessment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vendor-assessments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VendorAssessment> partialUpdateVendorAssessment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VendorAssessment vendorAssessment
    ) throws URISyntaxException {
        log.debug("REST request to partial update VendorAssessment partially : {}, {}", id, vendorAssessment);
        if (vendorAssessment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendorAssessment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendorAssessmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VendorAssessment> result = vendorAssessmentService.partialUpdate(vendorAssessment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendorAssessment.getId().toString())
        );
    }

    /**
     * {@code GET  /vendor-assessments} : get all the vendorAssessments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendorAssessments in body.
     */
    @GetMapping("/vendor-assessments")
    public ResponseEntity<List<VendorAssessment>> getAllVendorAssessments(
        VendorAssessmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VendorAssessments by criteria: {}", criteria);

        Page<VendorAssessment> page = vendorAssessmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vendor-assessments/count} : count all the vendorAssessments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vendor-assessments/count")
    public ResponseEntity<Long> countVendorAssessments(VendorAssessmentCriteria criteria) {
        log.debug("REST request to count VendorAssessments by criteria: {}", criteria);
        return ResponseEntity.ok().body(vendorAssessmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vendor-assessments/:id} : get the "id" vendorAssessment.
     *
     * @param id the id of the vendorAssessment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendorAssessment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vendor-assessments/{id}")
    public ResponseEntity<VendorAssessment> getVendorAssessment(@PathVariable Long id) {
        log.debug("REST request to get VendorAssessment : {}", id);
        Optional<VendorAssessment> vendorAssessment = vendorAssessmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendorAssessment);
    }

    /**
     * {@code DELETE  /vendor-assessments/:id} : delete the "id" vendorAssessment.
     *
     * @param id the id of the vendorAssessment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vendor-assessments/{id}")
    public ResponseEntity<Void> deleteVendorAssessment(@PathVariable Long id) {
        log.debug("REST request to delete VendorAssessment : {}", id);
        vendorAssessmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
