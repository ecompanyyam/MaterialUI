package com.yam.ecompany.service.impl;

import com.yam.ecompany.domain.SalesRepresentative;
import com.yam.ecompany.repository.SalesRepresentativeRepository;
import com.yam.ecompany.service.SalesRepresentativeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SalesRepresentative}.
 */
@Service
@Transactional
public class SalesRepresentativeServiceImpl implements SalesRepresentativeService {

    private final Logger log = LoggerFactory.getLogger(SalesRepresentativeServiceImpl.class);

    private final SalesRepresentativeRepository salesRepresentativeRepository;

    public SalesRepresentativeServiceImpl(SalesRepresentativeRepository salesRepresentativeRepository) {
        this.salesRepresentativeRepository = salesRepresentativeRepository;
    }

    @Override
    public SalesRepresentative save(SalesRepresentative salesRepresentative) {
        log.debug("Request to save SalesRepresentative : {}", salesRepresentative);
        return salesRepresentativeRepository.save(salesRepresentative);
    }

    @Override
    public SalesRepresentative update(SalesRepresentative salesRepresentative) {
        log.debug("Request to update SalesRepresentative : {}", salesRepresentative);
        return salesRepresentativeRepository.save(salesRepresentative);
    }

    @Override
    public Optional<SalesRepresentative> partialUpdate(SalesRepresentative salesRepresentative) {
        log.debug("Request to partially update SalesRepresentative : {}", salesRepresentative);

        return salesRepresentativeRepository
            .findById(salesRepresentative.getId())
            .map(existingSalesRepresentative -> {
                if (salesRepresentative.getFullName() != null) {
                    existingSalesRepresentative.setFullName(salesRepresentative.getFullName());
                }
                if (salesRepresentative.getJobTitle() != null) {
                    existingSalesRepresentative.setJobTitle(salesRepresentative.getJobTitle());
                }
                if (salesRepresentative.getEmail() != null) {
                    existingSalesRepresentative.setEmail(salesRepresentative.getEmail());
                }
                if (salesRepresentative.getPhone() != null) {
                    existingSalesRepresentative.setPhone(salesRepresentative.getPhone());
                }
                if (salesRepresentative.getOfficeLocation() != null) {
                    existingSalesRepresentative.setOfficeLocation(salesRepresentative.getOfficeLocation());
                }
                if (salesRepresentative.getAddressLine1() != null) {
                    existingSalesRepresentative.setAddressLine1(salesRepresentative.getAddressLine1());
                }
                if (salesRepresentative.getOtherDetails() != null) {
                    existingSalesRepresentative.setOtherDetails(salesRepresentative.getOtherDetails());
                }

                return existingSalesRepresentative;
            })
            .map(salesRepresentativeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesRepresentative> findAll(Pageable pageable) {
        log.debug("Request to get all SalesRepresentatives");
        return salesRepresentativeRepository.findAll(pageable);
    }

    public Page<SalesRepresentative> findAllWithEagerRelationships(Pageable pageable) {
        return salesRepresentativeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalesRepresentative> findOne(Long id) {
        log.debug("Request to get SalesRepresentative : {}", id);
        return salesRepresentativeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesRepresentative : {}", id);
        salesRepresentativeRepository.deleteById(id);
    }
}
