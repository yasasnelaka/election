package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {

    @Query(value = "SELECT * FROM `citizen` c WHERE c.status=:status", nativeQuery = true)
    public List<Citizen> findAll(int status);

    @Query(value = "SELECT * FROM `citizen` c WHERE c.fingerprint_id=:fingerprint AND c.status=:status", nativeQuery = true)
    public Citizen getByFingerprint(String fingerprint, int status);
}
