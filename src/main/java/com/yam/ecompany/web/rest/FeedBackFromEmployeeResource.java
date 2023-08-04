package com.yam.ecompany.web.rest;

import com.yam.ecompany.domain.FeedBackFromEmployee;
import com.yam.ecompany.repository.FeedBackFromEmployeeRepository;
import com.yam.ecompany.service.FeedBackFromEmployeeQueryService;
import com.yam.ecompany.service.FeedBackFromEmployeeService;
import com.yam.ecompany.service.criteria.FeedBackFromEmployeeCriteria;
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
 * REST controller for managing {@link com.yam.ecompany.domain.FeedBackFromEmployee}.
 */
@RestController
@RequestMapping("/api")
public class FeedBackFromEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(FeedBackFromEmployeeResource.class);

    private static final String ENTITY_NAME = "feedBackFromEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedBackFromEmployeeService feedBackFromEmployeeService;

    private final FeedBackFromEmployeeRepository feedBackFromEmployeeRepository;

    private final FeedBackFromEmployeeQueryService feedBackFromEmployeeQueryService;

    public FeedBackFromEmployeeResource(
        FeedBackFromEmployeeService feedBackFromEmployeeService,
        FeedBackFromEmployeeRepository feedBackFromEmployeeRepository,
        FeedBackFromEmployeeQueryService feedBackFromEmployeeQueryService
    ) {
        this.feedBackFromEmployeeService = feedBackFromEmployeeService;
        this.feedBackFromEmployeeRepository = feedBackFromEmployeeRepository;
        this.feedBackFromEmployeeQueryService = feedBackFromEmployeeQueryService;
    }

    /**
     * {@code POST  /feed-back-from-employees} : Create a new feedBackFromEmployee.
     *
     * @param feedBackFromEmployee the feedBackFromEmployee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedBackFromEmployee, or with status {@code 400 (Bad Request)} if the feedBackFromEmployee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feed-back-from-employees")
    public ResponseEntity<FeedBackFromEmployee> createFeedBackFromEmployee(@Valid @RequestBody FeedBackFromEmployee feedBackFromEmployee)
        throws URISyntaxException {
        log.debug("REST request to save FeedBackFromEmployee : {}", feedBackFromEmployee);
        if (feedBackFromEmployee.getId() != null) {
            throw new BadRequestAlertException("A new feedBackFromEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedBackFromEmployee result = feedBackFromEmployeeService.save(feedBackFromEmployee);
        return ResponseEntity
            .created(new URI("/api/feed-back-from-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feed-back-from-employees/:id} : Updates an existing feedBackFromEmployee.
     *
     * @param id the id of the feedBackFromEmployee to save.
     * @param feedBackFromEmployee the feedBackFromEmployee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackFromEmployee,
     * or with status {@code 400 (Bad Request)} if the feedBackFromEmployee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedBackFromEmployee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feed-back-from-employees/{id}")
    public ResponseEntity<FeedBackFromEmployee> updateFeedBackFromEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeedBackFromEmployee feedBackFromEmployee
    ) throws URISyntaxException {
        log.debug("REST request to update FeedBackFromEmployee : {}, {}", id, feedBackFromEmployee);
        if (feedBackFromEmployee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedBackFromEmployee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedBackFromEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedBackFromEmployee result = feedBackFromEmployeeService.update(feedBackFromEmployee);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedBackFromEmployee.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feed-back-from-employees/:id} : Partial updates given fields of an existing feedBackFromEmployee, field will ignore if it is null
     *
     * @param id the id of the feedBackFromEmployee to save.
     * @param feedBackFromEmployee the feedBackFromEmployee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackFromEmployee,
     * or with status {@code 400 (Bad Request)} if the feedBackFromEmployee is not valid,
     * or with status {@code 404 (Not Found)} if the feedBackFromEmployee is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedBackFromEmployee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feed-back-from-employees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedBackFromEmployee> partialUpdateFeedBackFromEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeedBackFromEmployee feedBackFromEmployee
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedBackFromEmployee partially : {}, {}", id, feedBackFromEmployee);
        if (feedBackFromEmployee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedBackFromEmployee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedBackFromEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedBackFromEmployee> result = feedBackFromEmployeeService.partialUpdate(feedBackFromEmployee);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedBackFromEmployee.getId().toString())
        );
    }

    /**
     * {@code GET  /feed-back-from-employees} : get all the feedBackFromEmployees.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedBackFromEmployees in body.
     */
    @GetMapping("/feed-back-from-employees")
    public ResponseEntity<List<FeedBackFromEmployee>> getAllFeedBackFromEmployees(
        FeedBackFromEmployeeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FeedBackFromEmployees by criteria: {}", criteria);

        Page<FeedBackFromEmployee> page = feedBackFromEmployeeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feed-back-from-employees/count} : count all the feedBackFromEmployees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feed-back-from-employees/count")
    public ResponseEntity<Long> countFeedBackFromEmployees(FeedBackFromEmployeeCriteria criteria) {
        log.debug("REST request to count FeedBackFromEmployees by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedBackFromEmployeeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feed-back-from-employees/:id} : get the "id" feedBackFromEmployee.
     *
     * @param id the id of the feedBackFromEmployee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedBackFromEmployee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feed-back-from-employees/{id}")
    public ResponseEntity<FeedBackFromEmployee> getFeedBackFromEmployee(@PathVariable Long id) {
        log.debug("REST request to get FeedBackFromEmployee : {}", id);
        Optional<FeedBackFromEmployee> feedBackFromEmployee = feedBackFromEmployeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedBackFromEmployee);
    }

    /**
     * {@code DELETE  /feed-back-from-employees/:id} : delete the "id" feedBackFromEmployee.
     *
     * @param id the id of the feedBackFromEmployee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feed-back-from-employees/{id}")
    public ResponseEntity<Void> deleteFeedBackFromEmployee(@PathVariable Long id) {
        log.debug("REST request to delete FeedBackFromEmployee : {}", id);
        feedBackFromEmployeeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
