package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District,Integer> {

    @Query(value="SELECT * FROM `district` d WHERE d.province_id=:province AND d.status=:status",nativeQuery=true)
    public List<District> findAllByProvince(int province,int status);

}
