package com.example.employeepayroll.controller;

import com.example.employeepayroll.entity.Payroll;
import com.example.employeepayroll.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/employee/{employeeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Payroll createPayroll(
            @PathVariable Long employeeId,
            @Valid @RequestBody Payroll payroll) {

        return payrollService.createPayroll(employeeId, payroll);
    }

    @GetMapping("/{id}")
    public Payroll getPayroll(@PathVariable Long id) {
        return payrollService.getPayrollById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Payroll> getEmployeePayroll(
            @PathVariable Long employeeId) {

        return payrollService.getPayrollByEmployee(employeeId);
    }

    @GetMapping
    public List<Payroll> getAllPayrolls() {
        return payrollService.getAllPayrolls();
    }
}