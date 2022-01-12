package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElectionCandidateRepository extends JpaRepository<ElectionCandidate,Integer> {

    @Query(value="SELECT * FROM `election_candidate` ec WHERE ec.election_id=:electionId AND ec.candidate_id=:candidateId AND ec.status=:status",nativeQuery=true)
    public ElectionCandidate getByElectionAndCandidate(int electionId,int candidateId,int status);

}
