package com.lk.election.dbTier.repositories;

import com.lk.election.dbTier.entities.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ElectionCandidateRepository extends JpaRepository<ElectionCandidate,Integer> {

    @Query(value="SELECT * FROM `election_candidate` ec WHERE ec.election_id=:electionId AND ec.candidate_id=:candidateId AND ec.status=:status",nativeQuery=true)
    public ElectionCandidate getByElectionAndCandidate(int electionId,int candidateId,int status);

    @Query(value="SELECT * FROM `election_candidate` ec WHERE ec.election_id=:electionId AND ec.status=:status",nativeQuery=true)
    public List<ElectionCandidate> getAllByElection(int electionId, int status);

    @Query(value="SELECT sum(ec.votes) AS 'X' FROM election_candidate ec where ec.election_id = :electionId AND ec.status=:status AND ec.candidate_id in (SELECT c.id from candidate c where c.election_party_id = :partyId and c.status=:status)",nativeQuery=true)
    public Integer getTotalVotesByElectionAndParty(int electionId,int partyId, int status);

    @Query(value="SELECT sum(ec.votes) AS 'X' FROM election_candidate ec where ec.election_id = :electionId AND ec.status=:status AND ec.candidate_id in (SELECT c.id from candidate c where c.status=:status)",nativeQuery=true)
    public Integer getTotalVotesByElection(int electionId, int status);

}
