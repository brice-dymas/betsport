package com.gcs.betsport.repository;

import com.gcs.betsport.domain.Period;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Period entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {

}
