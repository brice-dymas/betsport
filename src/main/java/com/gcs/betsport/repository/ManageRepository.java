package com.gcs.betsport.repository;

import com.gcs.betsport.domain.Manage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Manage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManageRepository extends JpaRepository<Manage, Long> {

}
