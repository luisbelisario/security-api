package com.reservei.securityapi.securityapi.repository;

import com.reservei.securityapi.securityapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPublicId(String publicId);
}
