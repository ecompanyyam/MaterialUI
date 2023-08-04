package com.yam.ecompany.repository;

import com.yam.ecompany.domain.VendorAssessment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VendorAssessment entity.
 */
@Repository
public interface VendorAssessmentRepository extends JpaRepository<VendorAssessment, Long>, JpaSpecificationExecutor<VendorAssessment> {
    default Optional<VendorAssessment> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<VendorAssessment> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<VendorAssessment> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select vendorAssessment from VendorAssessment vendorAssessment left join fetch vendorAssessment.vendorsName",
        countQuery = "select count(vendorAssessment) from VendorAssessment vendorAssessment"
    )
    Page<VendorAssessment> findAllWithToOneRelationships(Pageable pageable);

    @Query("select vendorAssessment from VendorAssessment vendorAssessment left join fetch vendorAssessment.vendorsName")
    List<VendorAssessment> findAllWithToOneRelationships();

    @Query(
        "select vendorAssessment from VendorAssessment vendorAssessment left join fetch vendorAssessment.vendorsName where vendorAssessment.id =:id"
    )
    Optional<VendorAssessment> findOneWithToOneRelationships(@Param("id") Long id);
}
