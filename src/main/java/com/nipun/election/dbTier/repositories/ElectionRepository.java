package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.Citizen;
import com.nipun.election.dbTier.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ElectionRepository extends JpaRepository<Election,Integer> {

    @Query(value="SELECT * FROM `election` e WHERE e.status=:status",nativeQuery=true)
    public List<Election> findAll(int status);

}
