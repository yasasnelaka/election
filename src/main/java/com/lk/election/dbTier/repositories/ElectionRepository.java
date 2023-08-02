package com.lk.election.dbTier.repositories;

import com.lk.election.dbTier.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ElectionRepository extends JpaRepository<Election,Integer> {

    @Query(value="SELECT * FROM `election` e WHERE e.status=:status",nativeQuery=true)
    public List<Election> findAll(int status);

    @Query(value="SELECT * FROM `election` e WHERE e.election_status=:electionStatus AND e.status=:status LIMIt 1",nativeQuery=true)
    public Election getOneByElectionStatus(int electionStatus,int status);

}
