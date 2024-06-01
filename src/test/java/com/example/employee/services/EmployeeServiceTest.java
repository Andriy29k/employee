package com.example.employee.services;

import com.example.employee.models.Employee;
import com.example.employee.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;
    @Test
    public void findById() {
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee found = employeeService.findById(1L);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
    }

    @Test
    public void testSave() {
        Employee employee = new Employee();
        employee.setName("John Doe");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee saved = employeeService.save(employee);
        assertThat(saved.getName()).isEqualTo("John Doe");
    }
}