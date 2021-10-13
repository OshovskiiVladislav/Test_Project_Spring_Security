package com.oshovskii.springSecurityApp.tests.services;


import com.oshovskii.springSecurityApp.models.ERole;
import com.oshovskii.springSecurityApp.models.Role;
import com.oshovskii.springSecurityApp.models.User;
import com.oshovskii.springSecurityApp.repository.UserRepository;
import com.oshovskii.springSecurityApp.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(classes = UserDetailsServiceImpl.class)
public class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    private static final String DEMO_USERNAME = "John";
    private static final String DEMO_USER_EMAIL = "john@gmail.com";
    private static final String DEMO_USER_PASSWORD = "100";
    private static final Long DEMO_USER_ID = 1L;
    private static final ERole DEMO_USER_ROLE = ERole.ROLE_USER;
    private static final Integer DEMO_USER_ROLE_ID = 1;

    @Test
    public void testGetUser() {
        Role demoRole = new Role();
        demoRole.setName(DEMO_USER_ROLE);
        demoRole.setId(DEMO_USER_ROLE_ID);
        Set<Role> demoRoles = new HashSet<>();
        demoRoles.add(demoRole);


        User demoUser = new User();
        demoUser.setId(DEMO_USER_ID);
        demoUser.setUsername(DEMO_USERNAME);
        demoUser.setEmail(DEMO_USER_EMAIL);
        demoUser.setPassword(DEMO_USER_PASSWORD);
        demoUser.setRoles(demoRoles);

        Mockito
                .doReturn(Optional.of(demoUser))
                .when(userRepository)
                .findByUsername(DEMO_USERNAME);

        User user = userDetailsService.findByUsername(DEMO_USERNAME).get();

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(ArgumentMatchers.eq(DEMO_USERNAME));
        Assertions.assertEquals(DEMO_USER_EMAIL, user.getEmail());
        Assertions.assertEquals(DEMO_USERNAME, user.getUsername());
        Assertions.assertEquals(demoRoles, user.getRoles());

        UserDetails userDetails = userDetailsService.loadUserByUsername(DEMO_USERNAME);
        Assertions.assertEquals(DEMO_USERNAME, userDetails.getUsername());
        Assertions.assertEquals(DEMO_USER_PASSWORD, userDetails.getPassword());
        Assertions.assertEquals(
                user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList()),
                userDetails.getAuthorities());
    }
}
