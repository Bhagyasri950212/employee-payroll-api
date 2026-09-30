package com.example.employeepayroll.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Table(name = "payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @NotNull(message = "Basic salary is required")
    @DecimalMin(value = "0.0", message = "Basic salary cannot be negative")
    @Column(nullable = false)
    private BigDecimal basicSalary;

    @NotNull(message = "Allowances are required")
    @DecimalMin(value = "0.0", message = "Allowances cannot be negative")
    @Column(nullable = false)
    private BigDecimal allowances = BigDecimal.ZERO;

    @NotNull(message = "Deductions are required")
    @DecimalMin(value = "0.0", message = "Deductions cannot be negative")
    @Column(nullable = false)
    private BigDecimal deductions = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal netSalary;

    @Column(nullable = false)
    private String payMonth;

    @PrePersist
    @PreUpdate
    protected void calculateNetSalary() {
        netSalary = basicSalary
                .add(allowances)
                .subtract(deductions);
    }

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getAllowances() {
        return allowances;
    }

    public void setAllowances(BigDecimal allowances) {
        this.allowances = allowances;
    }

    public BigDecimal getDeductions() {
        return deductions;
    }

    public void setDeductions(BigDecimal deductions) {
        this.deductions = deductions;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }
}