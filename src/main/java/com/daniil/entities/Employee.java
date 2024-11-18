package com.daniil.entities;

import com.daniil.entities.enums.WorkStatus;
import jakarta.persistence.*;


@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(name = "contact_number", nullable = false,
            columnDefinition = "VARCHAR(12) CHECK (contact_number ~ '^\\+7[0-9]{10}$')")
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false,
            foreignKey = @ForeignKey(name = "employee_division_fk"))
    private Division division;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false,
            foreignKey = @ForeignKey(name = "employee_position_fk"))
    private Position position;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;

    public Employee() {
    }

    public Employee(long id, String name, String lastname, String contactNumber,
                    Division division, Position position, WorkStatus workStatus) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.contactNumber = contactNumber;
        this.division = division;
        this.position = position;
        this.workStatus = workStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }
}
