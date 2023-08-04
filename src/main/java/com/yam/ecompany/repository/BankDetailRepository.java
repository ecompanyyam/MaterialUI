package com.yam.ecompany.repository;

import com.yam.ecompany.domain.BankDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankDetail entity.
 */
@Repository
public interface BankDetailRepository extends JpaRepository<BankDetail, Long>, JpaSpecificationExecutor<BankDetail> {
    default Optional<BankDetail> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BankDetail> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BankDetail> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select bankDetail from BankDetail bankDetail left join fetch bankDetail.vendorsName",
        countQuery = "select count(bankDetail) from BankDetail bankDetail"
    )
    Page<BankDetail> findAllWithToOneRelationships(Pageable pageable);

    @Query("select bankDetail from BankDetail bankDetail left join fetch bankDetail.vendorsName")
    List<BankDetail> findAllWithToOneRelationships();

    @Query("select bankDetail from BankDetail bankDetail left join fetch bankDetail.vendorsName where bankDetail.id =:id")
    Optional<BankDetail> findOneWithToOneRelationships(@Param("id") Long id);
}
