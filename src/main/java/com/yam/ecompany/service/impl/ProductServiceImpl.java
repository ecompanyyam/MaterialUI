package com.yam.ecompany.service.impl;

import com.yam.ecompany.domain.Product;
import com.yam.ecompany.repository.ProductRepository;
import com.yam.ecompany.service.ProductService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        log.debug("Request to save Product : {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        log.debug("Request to update Product : {}", product);
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> partialUpdate(Product product) {
        log.debug("Request to partially update Product : {}", product);

        return productRepository
            .findById(product.getId())
            .map(existingProduct -> {
                if (product.getProductName() != null) {
                    existingProduct.setProductName(product.getProductName());
                }
                if (product.getProductRemark() != null) {
                    existingProduct.setProductRemark(product.getProductRemark());
                }
                if (product.getProductOrigin() != null) {
                    existingProduct.setProductOrigin(product.getProductOrigin());
                }
                if (product.getProductStockStatus() != null) {
                    existingProduct.setProductStockStatus(product.getProductStockStatus());
                }
                if (product.getProductAvgDeliveryTime() != null) {
                    existingProduct.setProductAvgDeliveryTime(product.getProductAvgDeliveryTime());
                }
                if (product.getProductManufacturer() != null) {
                    existingProduct.setProductManufacturer(product.getProductManufacturer());
                }
                if (product.getProductImage() != null) {
                    existingProduct.setProductImage(product.getProductImage());
                }
                if (product.getProductImageContentType() != null) {
                    existingProduct.setProductImageContentType(product.getProductImageContentType());
                }
                if (product.getHeight() != null) {
                    existingProduct.setHeight(product.getHeight());
                }
                if (product.getWidth() != null) {
                    existingProduct.setWidth(product.getWidth());
                }
                if (product.getTaken() != null) {
                    existingProduct.setTaken(product.getTaken());
                }
                if (product.getUploaded() != null) {
                    existingProduct.setUploaded(product.getUploaded());
                }
                if (product.getProductAttachments() != null) {
                    existingProduct.setProductAttachments(product.getProductAttachments());
                }
                if (product.getProductAttachmentsContentType() != null) {
                    existingProduct.setProductAttachmentsContentType(product.getProductAttachmentsContentType());
                }

                return existingProduct;
            })
            .map(productRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable);
    }

    public Page<Product> findAllWithEagerRelationships(Pageable pageable) {
        return productRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
