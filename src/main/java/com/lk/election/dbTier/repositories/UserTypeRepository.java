package com.lk.election.dbTier.repositories;

import com.lk.election.dbTier.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType,Integer> {
}
