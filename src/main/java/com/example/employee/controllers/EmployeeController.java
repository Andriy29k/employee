package com.example.employee.controllers;

import com.example.employee.models.Employee;
import com.example.employee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.save(employee);
        return ResponseEntity.ok(createdEmployee).getBody();
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee existingEmployee = employeeService.findById(id);
        if (existingEmployee != null) {
            existingEmployee.setName(employee.getName());
            existingEmployee.setPosition(employee.getPosition());
            existingEmployee.setSalary(employee.getSalary());
            return employeeService.save(existingEmployee);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
    }
}
