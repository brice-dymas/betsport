package com.gcs.betsport.repository;

import com.gcs.betsport.domain.TransactionType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TransactionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

}
