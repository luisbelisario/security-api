package com.reservei.securityapi.securityapi.repository;

import com.reservei.securityapi.securityapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
