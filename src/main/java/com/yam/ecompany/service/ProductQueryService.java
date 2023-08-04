package com.yam.ecompany.service;

import com.yam.ecompany.domain.*; // for static metamodels
import com.yam.ecompany.domain.Product;
import com.yam.ecompany.repository.ProductRepository;
import com.yam.ecompany.service.criteria.ProductCriteria;
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
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Product} or a {@link Page} of {@link Product} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Return a {@link List} of {@link Product} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Product> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Product} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Product> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), Product_.productName));
            }
            if (criteria.getProductOrigin() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOrigin(), Product_.productOrigin));
            }
            if (criteria.getProductStockStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getProductStockStatus(), Product_.productStockStatus));
            }
            if (criteria.getProductAvgDeliveryTime() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getProductAvgDeliveryTime(), Product_.productAvgDeliveryTime));
            }
            if (criteria.getProductManufacturer() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getProductManufacturer(), Product_.productManufacturer));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Product_.height));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWidth(), Product_.width));
            }
            if (criteria.getTaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaken(), Product_.taken));
            }
            if (criteria.getUploaded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploaded(), Product_.uploaded));
            }
            if (criteria.getVendorsNameId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVendorsNameId(),
                            root -> root.join(Product_.vendorsName, JoinType.LEFT).get(Vendor_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
