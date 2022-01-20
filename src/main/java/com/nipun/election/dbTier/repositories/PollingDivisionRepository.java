package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.District;
import com.nipun.election.dbTier.entities.PollingDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PollingDivisionRepository extends JpaRepository<PollingDivision, Integer> {

    @Query(value = "SELECT * FROM `polling_division` p WHERE p.district_id=:district AND p.status=:status", nativeQuery = true)
    public List<PollingDivision> findAllByDistrict(int district, int status);

}
