package com.example.sweater;

import com.example.sweater.controllers.MessageController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/message-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/message-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageController messageController;

    @Test
    public void messagesPageTest() throws Exception {
        mockMvc.perform(get("/messages"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='navbarSupportedContent']/div").string("admin"));
    }

    @Test
    public void messageListTest() throws Exception {
        mockMvc.perform(get("/messages"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(4));
    }

    @Test
    public void filterMessageTest() throws Exception {
        mockMvc.perform(get("/messages").param("tag", "another"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(1))
                .andExpect(xpath("//div[@id='message-list']/div").exists());
    }

    @Test
    public void addMessageToListTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/messages")
                .file("file", "123".getBytes())
                .param("text", "fifth")
                .param("tag", "new_one")
                .with(csrf());
    }
}
