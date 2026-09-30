package com.example.employeepayroll.service;

import com.example.employeepayroll.entity.Employee;
import com.example.employeepayroll.entity.Payroll;
import com.example.employeepayroll.repository.EmployeeRepository;
import com.example.employeepayroll.repository.PayrollRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;

    public PayrollService(PayrollRepository payrollRepository,
                          EmployeeRepository employeeRepository) {
        this.payrollRepository = payrollRepository;
        this.employeeRepository = employeeRepository;
    }

    public Payroll createPayroll(Long employeeId, Payroll payroll) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + employeeId));

        payroll.setEmployee(employee);

        return payrollRepository.save(payroll);
    }

    public Payroll getPayrollById(Long id) {
        return payrollRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payroll not found with id: " + id));
    }

    public List<Payroll> getPayrollByEmployee(Long employeeId) {

        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException(
                    "Employee not found with id: " + employeeId);
        }

        return payrollRepository.findByEmployeeId(employeeId);
    }

    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }
}