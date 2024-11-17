package com.daniil.services.validators;

import com.daniil.entities.Employee;
import com.daniil.repositories.DivisionRepository;
import com.daniil.repositories.EmployeeRepository;
import com.daniil.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeValidator {

    private final EmployeeRepository employeeRepository;
    private final DivisionRepository divisionRepository;
    private final PositionRepository positionRepository;

    @Autowired
    public EmployeeValidator(EmployeeRepository employeeRepository,
                           DivisionRepository divisionRepository,
                           PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.divisionRepository = divisionRepository;
        this.positionRepository = positionRepository;
    }

    public boolean isSaveValid (Employee employee) {
        if (employee == null)
            return false;

        return employee.getId() == 0 && employee.getName() != null && employee.getLastname() != null &&
                employee.getContactNumber() != null && employee.getPosition() != null &&
                employee.getDivision() != null && employee.getWorkStatus() != null;
    }

    public boolean isDeleteValid(long employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    public boolean isChangeValid(Employee employee) {
        if (employee == null)
            return false;

        if (employee.getId() == 0 || employee.getName() == null || employee.getLastname() == null ||
                employee.getContactNumber() == null || employee.getPosition() == null ||
                employee.getDivision() == null || employee.getWorkStatus() == null)
            return false;

        if (!positionRepository.existsById(employee.getPosition().getId()) ||
                !divisionRepository.existsById(employee.getDivision().getId()))
            return false;

        return employeeRepository.existsById(employee.getId());
    }
}
