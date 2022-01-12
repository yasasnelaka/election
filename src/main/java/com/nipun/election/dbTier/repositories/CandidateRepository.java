package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate,Integer> {

    @Query(value="SELECT * FROM `candidate` c WHERE c.status=:status",nativeQuery=true)
    public List<Candidate> findAll(int status);

}
