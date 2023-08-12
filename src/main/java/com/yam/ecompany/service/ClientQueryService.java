package com.yam.ecompany.service;

import com.yam.ecompany.domain.*; // for static metamodels
import com.yam.ecompany.domain.Client;
import com.yam.ecompany.repository.ClientRepository;
import com.yam.ecompany.service.criteria.ClientCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Client} entities in the database.
 * The main input is a {@link ClientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Client} or a {@link Page} of {@link Client} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientQueryService extends QueryService<Client> {

    private final Logger log = LoggerFactory.getLogger(ClientQueryService.class);

    private final ClientRepository clientRepository;

    public ClientQueryService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Return a {@link List} of {@link Client} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Client> findByCriteria(ClientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Client> specification = createSpecification(criteria);
        return clientRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Client} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Client> findByCriteria(ClientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Client> specification = createSpecification(criteria);
        return clientRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Client> specification = createSpecification(criteria);
        return clientRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Client> createSpecification(ClientCriteria criteria) {
        Specification<Client> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Client_.id));
            }
            if (criteria.getClientName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientName(), Client_.clientName));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Client_.height));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Client_.width));
            }
            if (criteria.getTaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaken(), Client_.taken));
            }
            if (criteria.getUploaded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploaded(), Client_.uploaded));
            }
            if (criteria.getDateOfSubmittal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfSubmittal(), Client_.dateOfSubmittal));
            }
            if (criteria.getApprovalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getApprovalStatus(), Client_.approvalStatus));
            }
            if (criteria.getRegistrationNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegistrationNumber(), Client_.registrationNumber));
            }
            if (criteria.getDateOfRegistration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfRegistration(), Client_.dateOfRegistration));
            }
            if (criteria.getDateOfExpiry() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfExpiry(), Client_.dateOfExpiry));
            }
        }
        return specification;
    }
}
