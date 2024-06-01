package com.example.employee.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.employee.models.Employee;
import com.example.employee.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testGetAllEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        when(employeeService.findAll()).thenReturn(Collections.singletonList(employee));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");

        when(employeeService.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"salary\":75000,\"position\":\"Developer\",\"passportData\":\"AB1234567\",\"phoneNumber\":\"123-456-7890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;

        doNothing().when(employeeService).deleteById(employeeId);

        mockMvc.perform(delete("/employees/{id}", employeeId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        verify(employeeService).deleteById(employeeId);
    }
}