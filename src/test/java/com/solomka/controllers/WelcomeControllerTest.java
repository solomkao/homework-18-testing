package com.solomka.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class WelcomeControllerTest extends BaseControllerTest {
    @Autowired
    private WelcomeController welcomeController;

    @Test
    public void testBookController() {
        Assertions.assertNotNull(this.welcomeController);
    }

    @Test
    public void test() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/helloworld"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
