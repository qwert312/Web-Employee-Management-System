package com.daniil.repositories;

import com.daniil.entities.Division;
import com.daniil.entities.Employee;
import com.daniil.entities.Position;
import com.daniil.entities.enums.WorkStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e")
    Page<Employee> findAllEmployeesPageable(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.name = :name, e.lastname = :lastname, " +
            "e.contactNumber = :contactNumber, e.workStatus = :workStatus, " +
            "e.position = :position, e.division = :division WHERE e.id = :id")
    void updateEmployee(String name, String lastname, String contactNumber,
                    WorkStatus workStatus, Position position, Division division, long id);
}
