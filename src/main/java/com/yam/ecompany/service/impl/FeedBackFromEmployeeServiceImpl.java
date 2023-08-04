package com.yam.ecompany.service.impl;

import com.yam.ecompany.domain.FeedBackFromEmployee;
import com.yam.ecompany.repository.FeedBackFromEmployeeRepository;
import com.yam.ecompany.service.FeedBackFromEmployeeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedBackFromEmployee}.
 */
@Service
@Transactional
public class FeedBackFromEmployeeServiceImpl implements FeedBackFromEmployeeService {

    private final Logger log = LoggerFactory.getLogger(FeedBackFromEmployeeServiceImpl.class);

    private final FeedBackFromEmployeeRepository feedBackFromEmployeeRepository;

    public FeedBackFromEmployeeServiceImpl(FeedBackFromEmployeeRepository feedBackFromEmployeeRepository) {
        this.feedBackFromEmployeeRepository = feedBackFromEmployeeRepository;
    }

    @Override
    public FeedBackFromEmployee save(FeedBackFromEmployee feedBackFromEmployee) {
        log.debug("Request to save FeedBackFromEmployee : {}", feedBackFromEmployee);
        return feedBackFromEmployeeRepository.save(feedBackFromEmployee);
    }

    @Override
    public FeedBackFromEmployee update(FeedBackFromEmployee feedBackFromEmployee) {
        log.debug("Request to update FeedBackFromEmployee : {}", feedBackFromEmployee);
        return feedBackFromEmployeeRepository.save(feedBackFromEmployee);
    }

    @Override
    public Optional<FeedBackFromEmployee> partialUpdate(FeedBackFromEmployee feedBackFromEmployee) {
        log.debug("Request to partially update FeedBackFromEmployee : {}", feedBackFromEmployee);

        return feedBackFromEmployeeRepository
            .findById(feedBackFromEmployee.getId())
            .map(existingFeedBackFromEmployee -> {
                if (feedBackFromEmployee.getRefContractPONumber() != null) {
                    existingFeedBackFromEmployee.setRefContractPONumber(feedBackFromEmployee.getRefContractPONumber());
                }
                if (feedBackFromEmployee.getFeedBackCategory() != null) {
                    existingFeedBackFromEmployee.setFeedBackCategory(feedBackFromEmployee.getFeedBackCategory());
                }
                if (feedBackFromEmployee.getComment() != null) {
                    existingFeedBackFromEmployee.setComment(feedBackFromEmployee.getComment());
                }

                return existingFeedBackFromEmployee;
            })
            .map(feedBackFromEmployeeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedBackFromEmployee> findAll(Pageable pageable) {
        log.debug("Request to get all FeedBackFromEmployees");
        return feedBackFromEmployeeRepository.findAll(pageable);
    }

    public Page<FeedBackFromEmployee> findAllWithEagerRelationships(Pageable pageable) {
        return feedBackFromEmployeeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedBackFromEmployee> findOne(Long id) {
        log.debug("Request to get FeedBackFromEmployee : {}", id);
        return feedBackFromEmployeeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeedBackFromEmployee : {}", id);
        feedBackFromEmployeeRepository.deleteById(id);
    }
}
