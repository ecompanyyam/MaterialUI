package com.yam.ecompany.repository;

import com.yam.ecompany.domain.Vendor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vendor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {}
