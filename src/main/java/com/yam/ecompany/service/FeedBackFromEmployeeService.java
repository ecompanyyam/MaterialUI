package com.yam.ecompany.service;

import com.yam.ecompany.domain.FeedBackFromEmployee;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FeedBackFromEmployee}.
 */
public interface FeedBackFromEmployeeService {
    /**
     * Save a feedBackFromEmployee.
     *
     * @param feedBackFromEmployee the entity to save.
     * @return the persisted entity.
     */
    FeedBackFromEmployee save(FeedBackFromEmployee feedBackFromEmployee);

    /**
     * Updates a feedBackFromEmployee.
     *
     * @param feedBackFromEmployee the entity to update.
     * @return the persisted entity.
     */
    FeedBackFromEmployee update(FeedBackFromEmployee feedBackFromEmployee);

    /**
     * Partially updates a feedBackFromEmployee.
     *
     * @param feedBackFromEmployee the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FeedBackFromEmployee> partialUpdate(FeedBackFromEmployee feedBackFromEmployee);

    /**
     * Get all the feedBackFromEmployees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedBackFromEmployee> findAll(Pageable pageable);

    /**
     * Get all the feedBackFromEmployees with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedBackFromEmployee> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" feedBackFromEmployee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeedBackFromEmployee> findOne(Long id);

    /**
     * Delete the "id" feedBackFromEmployee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
