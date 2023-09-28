package com.example.demo.Service;

import com.example.demo.Domain.Employee;

import java.util.List;

public interface EmployeeService {
        Employee createEmployee(Employee Employee);

        Employee getEmployeeById(Long EmployeeId);

        List<Employee> getAllEmployees();

        Employee updateEmployee(Employee Employee);

        void deleteEmployee(Long EmployeeId);
}

