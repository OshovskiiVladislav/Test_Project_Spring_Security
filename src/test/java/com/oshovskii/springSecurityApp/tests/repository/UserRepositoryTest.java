package com.oshovskii.springSecurityApp.tests.repository;

import com.oshovskii.springSecurityApp.models.User;
import com.oshovskii.springSecurityApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private static final String NAME = "Johny";
    private static final String EMAIL = "johny@gmail.com";
    private static final Long ID = 1L;

    @Test
    public void getUserTest(){
        Optional<User> userJohny = userRepository.findByUsername(NAME);
        Assertions.assertEquals(ID, userJohny.get().getId());
        Assertions.assertEquals(NAME, userJohny.get().getUsername());
        Assertions.assertEquals(EMAIL, userJohny.get().getEmail());
    }

    @Test
    public void existUserByUsernameTest(){
        Assertions.assertEquals(true, userRepository.existsByEmail(EMAIL));
        Assertions.assertEquals(true, userRepository.existsByUsername(NAME));
    }
}
