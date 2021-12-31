package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionCandidateRepository extends JpaRepository<ElectionCandidate,Integer> {
}
