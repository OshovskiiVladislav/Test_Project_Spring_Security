package com.oshovskii.springSecurityApp.tests.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MessageControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void getAllMessagesTest() throws Exception{
        mvc
                .perform(get("/api/v1/messages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..*").isArray())
                .andExpect(jsonPath("$..messagesList[*].text", hasSize(2)))
                .andExpect(jsonPath("$..messagesList[0].username", is(List.of("Johny"))))
                .andExpect(jsonPath("$..messagesList[0].text", is(List.of("Hello world!!"))))
                .andExpect(jsonPath("$..messagesList[1].text", is(List.of("Cat likes Java=)"))));
    }

    @Test
    @WithAnonymousUser
    public void getAllMessagesAnonymousTest() throws Exception{
        mvc.perform(get("/api/v1/messages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void getMessageByIdTest() throws Exception{
        mvc.perform(get("/api/v1/messages/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..text", is(List.of("Hello world!!"))))
                .andExpect(jsonPath("$..username", is(List.of("Johny"))));
    }

}
