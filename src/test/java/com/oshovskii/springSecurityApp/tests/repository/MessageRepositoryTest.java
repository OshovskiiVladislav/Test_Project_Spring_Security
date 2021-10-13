package com.oshovskii.springSecurityApp.tests.repository;

import com.oshovskii.springSecurityApp.models.ERole;
import com.oshovskii.springSecurityApp.models.Message;
import com.oshovskii.springSecurityApp.repository.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void initDbMessagesTest() {
        List<Message> messageList = messageRepository.findAll();
        Assertions.assertEquals(2, messageList.size());
    }
}

