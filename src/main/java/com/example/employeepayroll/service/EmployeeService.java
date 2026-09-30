package com.example.employeepayroll.service;

import com.example.employeepayroll.entity.Employee;
import com.example.employeepayroll.entity.EmploymentStatus;
import com.example.employeepayroll.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Add employee
    public Employee createEmployee(Employee employee) {

        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new RuntimeException("Employee with this email already exists");
        }

        return employeeRepository.save(employee);
    }

    // Get employee by ID
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + id));
    }

    // Get all employees
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    // Update employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee existingEmployee = getEmployeeById(id);

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setJoiningDate(updatedEmployee.getJoiningDate());
        existingEmployee.setEmploymentStatus(
                updatedEmployee.getEmploymentStatus()
        );

        return employeeRepository.save(existingEmployee);
    }

    // Change employment status
    public Employee updateStatus(Long id, EmploymentStatus status) {

        Employee employee = getEmployeeById(id);

        employee.setEmploymentStatus(status);

        return employeeRepository.save(employee);
    }

    // Search by department
    public List<Employee> findByDepartment(String department) {

        return employeeRepository.findByDepartmentIgnoreCase(department);
    }

    // Search by employment status
    public List<Employee> findByStatus(EmploymentStatus status) {

        return employeeRepository.findByEmploymentStatus(status);
    }
}