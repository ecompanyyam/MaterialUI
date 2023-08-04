package com.yam.ecompany.repository;

import com.yam.ecompany.domain.SalesRepresentative;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesRepresentative entity.
 */
@Repository
public interface SalesRepresentativeRepository
    extends JpaRepository<SalesRepresentative, Long>, JpaSpecificationExecutor<SalesRepresentative> {
    default Optional<SalesRepresentative> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SalesRepresentative> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SalesRepresentative> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select salesRepresentative from SalesRepresentative salesRepresentative left join fetch salesRepresentative.vendorsName",
        countQuery = "select count(salesRepresentative) from SalesRepresentative salesRepresentative"
    )
    Page<SalesRepresentative> findAllWithToOneRelationships(Pageable pageable);

    @Query("select salesRepresentative from SalesRepresentative salesRepresentative left join fetch salesRepresentative.vendorsName")
    List<SalesRepresentative> findAllWithToOneRelationships();

    @Query(
        "select salesRepresentative from SalesRepresentative salesRepresentative left join fetch salesRepresentative.vendorsName where salesRepresentative.id =:id"
    )
    Optional<SalesRepresentative> findOneWithToOneRelationships(@Param("id") Long id);
}
