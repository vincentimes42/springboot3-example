package com.example.demo.Service;

import com.example.demo.Domain.Employee;
import com.example.demo.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository EmployeeRepository;

    @Override
    public Employee createEmployee(Employee Employee) {
        return EmployeeRepository.save(Employee);
    }

    @Override
    public Employee getEmployeeById(Long EmployeeId) {
        Optional<Employee> optionalEmployee = EmployeeRepository.findById(EmployeeId);
        return optionalEmployee.get();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return EmployeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee Employee) {
        Employee existingEmployee = EmployeeRepository.findById(Employee.getId()).get();
        existingEmployee.setFirstName(Employee.getFirstName());
        existingEmployee.setLastName(Employee.getLastName());
        existingEmployee.setEmail(Employee.getEmail());
        Employee updatedEmployee = EmployeeRepository.save(existingEmployee);
        return updatedEmployee;
    }

    @Override
    public void deleteEmployee(Long EmployeeId) {
        EmployeeRepository.deleteById(EmployeeId);
    }
    
}
