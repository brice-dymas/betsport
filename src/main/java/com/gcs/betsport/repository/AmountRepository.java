package com.gcs.betsport.repository;

import com.gcs.betsport.domain.Amount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Amount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmountRepository extends JpaRepository<Amount, Long> {

}
