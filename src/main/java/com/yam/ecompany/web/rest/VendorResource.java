package com.yam.ecompany.web.rest;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.yam.ecompany.domain.Vendor;
import com.yam.ecompany.repository.VendorRepository;
import com.yam.ecompany.service.VendorQueryService;
import com.yam.ecompany.service.VendorService;
import com.yam.ecompany.service.criteria.VendorCriteria;
import com.yam.ecompany.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
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
 * REST controller for managing {@link com.yam.ecompany.domain.Vendor}.
 */
@RestController
@RequestMapping("/api")
public class VendorResource {

    private final Logger log = LoggerFactory.getLogger(VendorResource.class);

    private static final String ENTITY_NAME = "vendor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendorService vendorService;

    private final VendorRepository vendorRepository;

    private final VendorQueryService vendorQueryService;

    public VendorResource(VendorService vendorService, VendorRepository vendorRepository, VendorQueryService vendorQueryService) {
        this.vendorService = vendorService;
        this.vendorRepository = vendorRepository;
        this.vendorQueryService = vendorQueryService;
    }

    /**
     * {@code POST  /vendors} : Create a new vendor.
     *
     * @param vendor the vendor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendor, or with status {@code 400 (Bad Request)} if the vendor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vendors")
    public ResponseEntity<Vendor> createVendor(@Valid @RequestBody Vendor vendor) throws URISyntaxException {
        log.debug("REST request to save Vendor : {}", vendor);
        if (vendor.getId() != null) {
            throw new BadRequestAlertException("A new vendor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            vendor = setMetadata(vendor);
        } catch (ImageProcessingException | IOException | MetadataException ipe) {
            log.error(ipe.getMessage());
        }

        Vendor result = vendorService.save(vendor);
        return ResponseEntity
            .created(new URI("/api/vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    private Vendor setMetadata(Vendor vendor) throws ImageProcessingException, IOException, MetadataException {
        String str = DatatypeConverter.printBase64Binary(vendor.getVendorLogo());
        byte[] data2 = DatatypeConverter.parseBase64Binary(str);
        InputStream inputStream = new ByteArrayInputStream(data2);
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        Metadata metadata = ImageMetadataReader.readMetadata(bis);
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        if (directory != null) {
            Date date = directory.getDateDigitized();
            if (date != null) {
                vendor.setTaken(date.toInstant());
            }
        }

        if (vendor.getTaken() == null) {
            log.debug("Photo EXIF date digitized not available, setting taken on date to now...");
            vendor.setTaken(Instant.now());
        }

        vendor.setUploaded(Instant.now());

        JpegDirectory jpgDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
        if (jpgDirectory != null) {
            vendor.setHeight(jpgDirectory.getImageHeight());
            vendor.setWidth(jpgDirectory.getImageWidth());
        }

        return vendor;
    }

    /**
     * {@code PUT  /vendors/:id} : Updates an existing vendor.
     *
     * @param id the id of the vendor to save.
     * @param vendor the vendor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendor,
     * or with status {@code 400 (Bad Request)} if the vendor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vendors/{id}")
    public ResponseEntity<Vendor> updateVendor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vendor vendor
    ) throws URISyntaxException {
        log.debug("REST request to update Vendor : {}, {}", id, vendor);
        if (vendor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Vendor result = vendorService.update(vendor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vendors/:id} : Partial updates given fields of an existing vendor, field will ignore if it is null
     *
     * @param id the id of the vendor to save.
     * @param vendor the vendor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendor,
     * or with status {@code 400 (Bad Request)} if the vendor is not valid,
     * or with status {@code 404 (Not Found)} if the vendor is not found,
     * or with status {@code 500 (Internal Server Error)} if the vendor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vendors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vendor> partialUpdateVendor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vendor vendor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vendor partially : {}, {}", id, vendor);
        if (vendor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vendor> result = vendorService.partialUpdate(vendor);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendor.getId().toString())
        );
    }

    /**
     * {@code GET  /vendors} : get all the vendors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendors in body.
     */
    @GetMapping("/vendors")
    public ResponseEntity<List<Vendor>> getAllVendors(
        VendorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Vendors by criteria: {}", criteria);

        Page<Vendor> page = vendorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vendors/count} : count all the vendors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vendors/count")
    public ResponseEntity<Long> countVendors(VendorCriteria criteria) {
        log.debug("REST request to count Vendors by criteria: {}", criteria);
        return ResponseEntity.ok().body(vendorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vendors/:id} : get the "id" vendor.
     *
     * @param id the id of the vendor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vendors/{id}")
    public ResponseEntity<Vendor> getVendor(@PathVariable Long id) {
        log.debug("REST request to get Vendor : {}", id);
        Optional<Vendor> vendor = vendorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendor);
    }

    /**
     * {@code DELETE  /vendors/:id} : delete the "id" vendor.
     *
     * @param id the id of the vendor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vendors/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        log.debug("REST request to delete Vendor : {}", id);
        vendorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
