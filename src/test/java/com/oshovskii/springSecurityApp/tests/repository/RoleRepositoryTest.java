package com.oshovskii.springSecurityApp.tests.repository;

import com.oshovskii.springSecurityApp.models.ERole;
import com.oshovskii.springSecurityApp.models.Role;
import com.oshovskii.springSecurityApp.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void roleTest() {
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
        Assertions.assertEquals(ERole.ROLE_USER, role.get().getName());
    }
}
