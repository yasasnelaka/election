package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value="SELECT * FROM user u where u.email = :email and u.status = :status",nativeQuery=true)
    public User getByEmail(String email,int status);

    @Query(value="SELECT * FROM user u where u.fingerprint_id = :fingerprintId and u.status = :status LIMIT 1",nativeQuery=true)
    public User getByFingerprintId(String fingerprintId,int status);

}
