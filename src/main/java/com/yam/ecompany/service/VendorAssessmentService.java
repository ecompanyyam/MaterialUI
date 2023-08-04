package com.yam.ecompany.service;

import com.yam.ecompany.domain.VendorAssessment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link VendorAssessment}.
 */
public interface VendorAssessmentService {
    /**
     * Save a vendorAssessment.
     *
     * @param vendorAssessment the entity to save.
     * @return the persisted entity.
     */
    VendorAssessment save(VendorAssessment vendorAssessment);

    /**
     * Updates a vendorAssessment.
     *
     * @param vendorAssessment the entity to update.
     * @return the persisted entity.
     */
    VendorAssessment update(VendorAssessment vendorAssessment);

    /**
     * Partially updates a vendorAssessment.
     *
     * @param vendorAssessment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VendorAssessment> partialUpdate(VendorAssessment vendorAssessment);

    /**
     * Get all the vendorAssessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VendorAssessment> findAll(Pageable pageable);

    /**
     * Get all the vendorAssessments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VendorAssessment> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" vendorAssessment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VendorAssessment> findOne(Long id);

    /**
     * Delete the "id" vendorAssessment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
