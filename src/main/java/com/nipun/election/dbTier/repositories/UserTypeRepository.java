package com.nipun.election.dbTier.repositories;

import com.nipun.election.dbTier.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType,Integer> {
}
