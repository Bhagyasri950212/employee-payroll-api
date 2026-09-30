package com.example.employeepayroll.controller;

import com.example.employeepayroll.entity.Employee;
import com.example.employeepayroll.entity.EmploymentStatus;
import com.example.employeepayroll.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. Add employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    // 2. Get employee by ID
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // 3. Get all employees
    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) EmploymentStatus status) {

        if (department != null) {
            return employeeService.findByDepartment(department);
        }

        if (status != null) {
            return employeeService.findByStatus(status);
        }

        return employeeService.getAllEmployees();
    }

    // 4. Update employee
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {

        return employeeService.updateEmployee(id, employee);
    }

    // 5. Change employee status
    @PatchMapping("/{id}/status")
    public Employee updateStatus(
            @PathVariable Long id,
            @RequestParam EmploymentStatus status) {

        return employeeService.updateStatus(id, status);
    }
}