package com.oshovskii.springSecurityApp.repository;

import com.oshovskii.springSecurityApp.models.ERole;
import com.oshovskii.springSecurityApp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}