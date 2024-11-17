package com.daniil.controllers;

import com.daniil.entities.Division;
import com.daniil.entities.Employee;
import com.daniil.entities.Position;
import com.daniil.entities.enums.WorkStatus;
import com.daniil.services.DivisionService;
import com.daniil.services.EmployeeService;
import com.daniil.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeesController {
    private EmployeeService employeeService;
    private DivisionService divisionService;
    private PositionService positionService;

    @Autowired
    public EmployeesController(EmployeeService employeeService,
                               DivisionService divisionService,
                               PositionService positionService) {
        this.employeeService = employeeService;
        this.divisionService = divisionService;
        this.positionService = positionService;
    }

    @GetMapping("/employees")
    public String employeesGet() {
        return "redirect:/employees/1";
    }

    @GetMapping("/employees/{pageNumber}")
    public String employeesPageGet(Model page,
                               @PathVariable int pageNumber) {
        if (pageNumber <= 0) {
            return "redirect:/employees/1";
        }

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 12);
        Page<Employee> currentPage = employeeService.findAllEmployeesPageable(pageRequest);

        if (currentPage.getContent().isEmpty() && pageNumber > 1) {
            return "redirect:/employees/1";
        }

        page.addAttribute("employees", currentPage.getContent());
        page.addAttribute("pageNumber", pageNumber);
        page.addAttribute("totalPages", currentPage.getTotalPages());
        return "employees";
    }

    @DeleteMapping("/employees/employee")
    public String employeesDelete(@RequestParam(value = "id") long id,
                                  @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/" + pageNumber;
    }

    @GetMapping("/employees/employee")
    public String employeeGet(Model page,
                              @RequestParam(value = "id", required = false) Long id,
                              @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber) {
        Employee employee = new Employee();
        String action = "add";

        if (id != null) {
            employee = employeeService.findEmployeeById(id).get();
            action = "edit";
        }

        page.addAttribute("employee", employee);
        page.addAttribute("divisions", divisionService.findAllDivisions());
        page.addAttribute("positions", positionService.findAllPositions());
        page.addAttribute("workStatuses", WorkStatus.values());
        page.addAttribute("action", action);
        page.addAttribute("pageNumber", pageNumber);
        return "employee";
    }

    @PostMapping("/employees/employee")
    public String employeeAdd(@ModelAttribute Employee employee,
                              @RequestParam(value = "divisionId") Long divisionId,
                              @RequestParam(value = "positionId") Long positionId,
                              @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber) {
        Division division = divisionService.findDivisionById(divisionId).get();
        Position position = positionService.findPositionById(positionId).get();
        employee.setDivision(division);
        employee.setPosition(position);
        employeeService.saveEmployee(employee);
        return "redirect:/employees/" + pageNumber;
    }

    @PatchMapping("/employees/employee")
    public String employeeChange(@ModelAttribute Employee employee,
                                 @RequestParam(value = "divisionId") Long divisionId,
                                 @RequestParam(value = "positionId") Long positionId,
                                 @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber) {
        Division division = divisionService.findDivisionById(divisionId).get();
        Position position = positionService.findPositionById(positionId).get();
        employee.setDivision(division);
        employee.setPosition(position);
        employeeService.changeEmployee(employee);
        return "redirect:/employees/" + pageNumber;
    }
}
