package com.daniil.services;

import com.daniil.entities.Employee;
import com.daniil.repositories.EmployeeRepository;
import com.daniil.services.validators.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeValidator employeeValidator;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeValidator employeeValidator) {
        this.employeeRepository = employeeRepository;
        this.employeeValidator = employeeValidator;
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Page<Employee> findAllEmployeesPageable(Pageable pageable) {
        return employeeRepository.findAllEmployeesPageable(pageable);
    }

    public Optional<Employee> findEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    //boolean-заглушки, стоит поменять на полноценную обработку с исключениями
    public boolean saveEmployee(Employee employee) {
        if (!employeeValidator.isSaveValid(employee))
            return false;

        employeeRepository.save(employee);
        return true;
    }

    public boolean deleteEmployee(long employeeId) {
        if (!employeeValidator.isDeleteValid(employeeId))
            return false;

        employeeRepository.deleteById(employeeId);
        return true;
    }

    public boolean changeEmployee(Employee employee) {
        if (!employeeValidator.isChangeValid(employee))
            return false;

        employeeRepository.updateEmployee(employee.getName(), employee.getLastname(),
                employee.getContactNumber(), employee.getWorkStatus(), employee.getPosition(),
                employee.getDivision(), employee.getId());
        return true;
    }
}
