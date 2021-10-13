package com.oshovskii.springSecurityApp.tests.services;

import com.oshovskii.springSecurityApp.models.ERole;
import com.oshovskii.springSecurityApp.models.Message;
import com.oshovskii.springSecurityApp.models.Role;
import com.oshovskii.springSecurityApp.models.User;
import com.oshovskii.springSecurityApp.payload.response.MessageResponse;
import com.oshovskii.springSecurityApp.repository.MessageRepository;
import com.oshovskii.springSecurityApp.repository.UserRepository;
import com.oshovskii.springSecurityApp.services.MessageService;
import com.oshovskii.springSecurityApp.services.UserDetailsServiceImpl;
import com.oshovskii.springSecurityApp.utils.MessageMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = MessageService.class)
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MessageMapper messageMapper;

    private static final String DEMO_USERNAME = "John";
    private static final String DEMO_USER_EMAIL = "john@gmail.com";
    private static final String DEMO_USER_PASSWORD = "100";
    private static final Long DEMO_USER_ID = 1L;
    private static final ERole DEMO_USER_ROLE = ERole.ROLE_USER;
    private static final Long DEMO_MESSAGE_ID = 10L;
    private static final String DEMO_MESSAGE_TEXT = "Cat likes Spring=)";

    @Test
    public void testGetMessage(){
        Role demoRole = new Role();
        demoRole.setName(DEMO_USER_ROLE);
        Set<Role> demoRoles = new HashSet<>();
        demoRoles.add(demoRole);

        User demoUser = new User();
        demoUser.setId(DEMO_USER_ID);
        demoUser.setUsername(DEMO_USERNAME);
        demoUser.setEmail(DEMO_USER_EMAIL);
        demoUser.setPassword(DEMO_USER_PASSWORD);
        demoUser.setRoles(demoRoles);

        Message demoMessage = new Message();
        demoMessage.setId(DEMO_MESSAGE_ID);
        demoMessage.setText(DEMO_MESSAGE_TEXT);
        demoMessage.setUser(demoUser);

        Mockito
                .doReturn(Optional.of(demoUser))
                .when(userRepository)
                .findByUsername(DEMO_USERNAME);

        Mockito
                .doReturn(Optional.of(demoMessage))
                .when(messageRepository)
                .findById(DEMO_MESSAGE_ID);

        MessageResponse demoMessageResponse = new MessageResponse();
        demoMessageResponse.setText(demoMessage.getText());
        demoMessageResponse.setId(demoMessage.getId());
        demoMessageResponse.setUsername(demoMessage.getUser().getUsername());

        Mockito
                .doReturn(demoMessageResponse)
                .when(messageMapper)
                .messageToMessageResponse(demoMessage);

        MessageResponse messageResponse = messageService.findMessageById(DEMO_MESSAGE_ID);

        Mockito.verify(messageRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(DEMO_MESSAGE_ID));
        Assertions.assertEquals(DEMO_MESSAGE_TEXT,messageResponse.getText());
        Assertions.assertEquals(DEMO_MESSAGE_ID,messageResponse.getId());
        Assertions.assertEquals(DEMO_USERNAME,messageResponse.getUsername());
    }
}
