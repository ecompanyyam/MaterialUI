package com.yam.ecompany.web.rest;

import com.yam.ecompany.domain.BankDetail;
import com.yam.ecompany.repository.BankDetailRepository;
import com.yam.ecompany.service.BankDetailQueryService;
import com.yam.ecompany.service.BankDetailService;
import com.yam.ecompany.service.criteria.BankDetailCriteria;
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
 * REST controller for managing {@link com.yam.ecompany.domain.BankDetail}.
 */
@RestController
@RequestMapping("/api")
public class BankDetailResource {

    private final Logger log = LoggerFactory.getLogger(BankDetailResource.class);

    private static final String ENTITY_NAME = "bankDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankDetailService bankDetailService;

    private final BankDetailRepository bankDetailRepository;

    private final BankDetailQueryService bankDetailQueryService;

    public BankDetailResource(
        BankDetailService bankDetailService,
        BankDetailRepository bankDetailRepository,
        BankDetailQueryService bankDetailQueryService
    ) {
        this.bankDetailService = bankDetailService;
        this.bankDetailRepository = bankDetailRepository;
        this.bankDetailQueryService = bankDetailQueryService;
    }

    /**
     * {@code POST  /bank-details} : Create a new bankDetail.
     *
     * @param bankDetail the bankDetail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankDetail, or with status {@code 400 (Bad Request)} if the bankDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-details")
    public ResponseEntity<BankDetail> createBankDetail(@Valid @RequestBody BankDetail bankDetail) throws URISyntaxException {
        log.debug("REST request to save BankDetail : {}", bankDetail);
        if (bankDetail.getId() != null) {
            throw new BadRequestAlertException("A new bankDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankDetail result = bankDetailService.save(bankDetail);
        return ResponseEntity
            .created(new URI("/api/bank-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-details/:id} : Updates an existing bankDetail.
     *
     * @param id the id of the bankDetail to save.
     * @param bankDetail the bankDetail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankDetail,
     * or with status {@code 400 (Bad Request)} if the bankDetail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankDetail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-details/{id}")
    public ResponseEntity<BankDetail> updateBankDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankDetail bankDetail
    ) throws URISyntaxException {
        log.debug("REST request to update BankDetail : {}, {}", id, bankDetail);
        if (bankDetail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankDetail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankDetail result = bankDetailService.update(bankDetail);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankDetail.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-details/:id} : Partial updates given fields of an existing bankDetail, field will ignore if it is null
     *
     * @param id the id of the bankDetail to save.
     * @param bankDetail the bankDetail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankDetail,
     * or with status {@code 400 (Bad Request)} if the bankDetail is not valid,
     * or with status {@code 404 (Not Found)} if the bankDetail is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankDetail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankDetail> partialUpdateBankDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankDetail bankDetail
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankDetail partially : {}, {}", id, bankDetail);
        if (bankDetail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankDetail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankDetail> result = bankDetailService.partialUpdate(bankDetail);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankDetail.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-details} : get all the bankDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankDetails in body.
     */
    @GetMapping("/bank-details")
    public ResponseEntity<List<BankDetail>> getAllBankDetails(
        BankDetailCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BankDetails by criteria: {}", criteria);

        Page<BankDetail> page = bankDetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-details/count} : count all the bankDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bank-details/count")
    public ResponseEntity<Long> countBankDetails(BankDetailCriteria criteria) {
        log.debug("REST request to count BankDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(bankDetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bank-details/:id} : get the "id" bankDetail.
     *
     * @param id the id of the bankDetail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankDetail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-details/{id}")
    public ResponseEntity<BankDetail> getBankDetail(@PathVariable Long id) {
        log.debug("REST request to get BankDetail : {}", id);
        Optional<BankDetail> bankDetail = bankDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankDetail);
    }

    /**
     * {@code DELETE  /bank-details/:id} : delete the "id" bankDetail.
     *
     * @param id the id of the bankDetail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-details/{id}")
    public ResponseEntity<Void> deleteBankDetail(@PathVariable Long id) {
        log.debug("REST request to delete BankDetail : {}", id);
        bankDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
