package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.PollingDivision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollingDivisionRepository extends JpaRepository<PollingDivision,Integer> {
}
