package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province,Integer> {
}
