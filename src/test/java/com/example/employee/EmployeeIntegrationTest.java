package com.example.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testCreateAndGetEmployee() throws Exception {
        mockMvc.perform(post("/employees")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"salary\":75000,\"position\":\"Developer\",\"passportData\":\"AB1234567\",\"phoneNumber\":\"123-456-7890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));

        mockMvc.perform(get("/employees")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")));
    }
}
