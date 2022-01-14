package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.ElectionParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ElectionPartyRepository extends JpaRepository<ElectionParty,Integer> {

    @Query(value="select * from election_party ep where ep.status=:status and ep.id in (select c.election_party_id from candidate c where c.status=:status and c.id in (select ec.candidate_id from election_candidate ec where ec.status=:status and ec.election_id=:electionId))",nativeQuery=true)
    public List<ElectionParty> findAllByElection(int electionId, int status);

}
