package com.daniil.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.Set;

@Entity
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
//    @SequenceGenerator(name = "department_seq", sequenceName = "department_id_seq")
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false,
            columnDefinition = "INT CHECK (cabinet BETWEEN 100 AND 999)")
    private int cabinet;

    @Column(name = "contact_number", nullable = false,
            columnDefinition = "VARCHAR(12) CHECK (contact_number ~ '^\\+7[0-9]{10}$')")
    private String contactNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "division")
    private Set<Employee> employees;

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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getCabinet() {
        return cabinet;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
