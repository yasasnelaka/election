package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate,Integer> {
}
