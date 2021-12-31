package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election,Integer> {
}
