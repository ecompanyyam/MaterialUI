package com.yam.ecompany.service.impl;

import com.yam.ecompany.domain.VendorAssessment;
import com.yam.ecompany.repository.VendorAssessmentRepository;
import com.yam.ecompany.service.VendorAssessmentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VendorAssessment}.
 */
@Service
@Transactional
public class VendorAssessmentServiceImpl implements VendorAssessmentService {

    private final Logger log = LoggerFactory.getLogger(VendorAssessmentServiceImpl.class);

    private final VendorAssessmentRepository vendorAssessmentRepository;

    public VendorAssessmentServiceImpl(VendorAssessmentRepository vendorAssessmentRepository) {
        this.vendorAssessmentRepository = vendorAssessmentRepository;
    }

    @Override
    public VendorAssessment save(VendorAssessment vendorAssessment) {
        log.debug("Request to save VendorAssessment : {}", vendorAssessment);
        return vendorAssessmentRepository.save(vendorAssessment);
    }

    @Override
    public VendorAssessment update(VendorAssessment vendorAssessment) {
        log.debug("Request to update VendorAssessment : {}", vendorAssessment);
        return vendorAssessmentRepository.save(vendorAssessment);
    }

    @Override
    public Optional<VendorAssessment> partialUpdate(VendorAssessment vendorAssessment) {
        log.debug("Request to partially update VendorAssessment : {}", vendorAssessment);

        return vendorAssessmentRepository
            .findById(vendorAssessment.getId())
            .map(existingVendorAssessment -> {
                if (vendorAssessment.getAssessmentDate() != null) {
                    existingVendorAssessment.setAssessmentDate(vendorAssessment.getAssessmentDate());
                }
                if (vendorAssessment.getAssessedBY() != null) {
                    existingVendorAssessment.setAssessedBY(vendorAssessment.getAssessedBY());
                }
                if (vendorAssessment.getJobKnowledge() != null) {
                    existingVendorAssessment.setJobKnowledge(vendorAssessment.getJobKnowledge());
                }
                if (vendorAssessment.getJobKnowledgeComment() != null) {
                    existingVendorAssessment.setJobKnowledgeComment(vendorAssessment.getJobKnowledgeComment());
                }
                if (vendorAssessment.getWorkQuality() != null) {
                    existingVendorAssessment.setWorkQuality(vendorAssessment.getWorkQuality());
                }
                if (vendorAssessment.getWorkQualityComment() != null) {
                    existingVendorAssessment.setWorkQualityComment(vendorAssessment.getWorkQualityComment());
                }
                if (vendorAssessment.getAttendancePunctuality() != null) {
                    existingVendorAssessment.setAttendancePunctuality(vendorAssessment.getAttendancePunctuality());
                }
                if (vendorAssessment.getAttendancePunctualityComment() != null) {
                    existingVendorAssessment.setAttendancePunctualityComment(vendorAssessment.getAttendancePunctualityComment());
                }
                if (vendorAssessment.getInitiative() != null) {
                    existingVendorAssessment.setInitiative(vendorAssessment.getInitiative());
                }
                if (vendorAssessment.getInitiativeComment() != null) {
                    existingVendorAssessment.setInitiativeComment(vendorAssessment.getInitiativeComment());
                }
                if (vendorAssessment.getCommunicationListeningSkills() != null) {
                    existingVendorAssessment.setCommunicationListeningSkills(vendorAssessment.getCommunicationListeningSkills());
                }
                if (vendorAssessment.getCommunicationListeningSkillsComment() != null) {
                    existingVendorAssessment.setCommunicationListeningSkillsComment(
                        vendorAssessment.getCommunicationListeningSkillsComment()
                    );
                }
                if (vendorAssessment.getDependability() != null) {
                    existingVendorAssessment.setDependability(vendorAssessment.getDependability());
                }
                if (vendorAssessment.getDependabilityComment() != null) {
                    existingVendorAssessment.setDependabilityComment(vendorAssessment.getDependabilityComment());
                }

                return existingVendorAssessment;
            })
            .map(vendorAssessmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VendorAssessment> findAll(Pageable pageable) {
        log.debug("Request to get all VendorAssessments");
        return vendorAssessmentRepository.findAll(pageable);
    }

    public Page<VendorAssessment> findAllWithEagerRelationships(Pageable pageable) {
        return vendorAssessmentRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VendorAssessment> findOne(Long id) {
        log.debug("Request to get VendorAssessment : {}", id);
        return vendorAssessmentRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VendorAssessment : {}", id);
        vendorAssessmentRepository.deleteById(id);
    }
}
