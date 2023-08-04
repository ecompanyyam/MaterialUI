package com.yam.ecompany.service;

import com.yam.ecompany.domain.BankDetail;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BankDetail}.
 */
public interface BankDetailService {
    /**
     * Save a bankDetail.
     *
     * @param bankDetail the entity to save.
     * @return the persisted entity.
     */
    BankDetail save(BankDetail bankDetail);

    /**
     * Updates a bankDetail.
     *
     * @param bankDetail the entity to update.
     * @return the persisted entity.
     */
    BankDetail update(BankDetail bankDetail);

    /**
     * Partially updates a bankDetail.
     *
     * @param bankDetail the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankDetail> partialUpdate(BankDetail bankDetail);

    /**
     * Get all the bankDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankDetail> findAll(Pageable pageable);

    /**
     * Get all the bankDetails with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankDetail> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" bankDetail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankDetail> findOne(Long id);

    /**
     * Delete the "id" bankDetail.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
