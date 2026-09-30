package com.example.employeepayroll.repository;

import com.example.employeepayroll.entity.Employee;
import com.example.employeepayroll.entity.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartmentIgnoreCase(String department);

    List<Employee> findByEmploymentStatus(EmploymentStatus status);
}