package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District,Integer> {
}
