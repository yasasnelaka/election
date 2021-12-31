package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenRepository extends JpaRepository<Citizen,Integer> {
}
