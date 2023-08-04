package com.yam.ecompany.service;

import com.yam.ecompany.domain.SalesRepresentative;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SalesRepresentative}.
 */
public interface SalesRepresentativeService {
    /**
     * Save a salesRepresentative.
     *
     * @param salesRepresentative the entity to save.
     * @return the persisted entity.
     */
    SalesRepresentative save(SalesRepresentative salesRepresentative);

    /**
     * Updates a salesRepresentative.
     *
     * @param salesRepresentative the entity to update.
     * @return the persisted entity.
     */
    SalesRepresentative update(SalesRepresentative salesRepresentative);

    /**
     * Partially updates a salesRepresentative.
     *
     * @param salesRepresentative the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SalesRepresentative> partialUpdate(SalesRepresentative salesRepresentative);

    /**
     * Get all the salesRepresentatives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SalesRepresentative> findAll(Pageable pageable);

    /**
     * Get all the salesRepresentatives with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SalesRepresentative> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" salesRepresentative.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SalesRepresentative> findOne(Long id);

    /**
     * Delete the "id" salesRepresentative.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
