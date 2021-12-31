package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.ElectionParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionPartyRepository extends JpaRepository<ElectionParty,Integer> {
}
