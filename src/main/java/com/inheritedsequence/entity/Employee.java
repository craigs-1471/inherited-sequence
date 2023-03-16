package com.inheritedsequence.entity;

import com.inheritedsequence.dto.EmployeeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="employees")
public class Employee extends Person {
    private String username;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "line_manager_id")
    private LineManager lineManager;
    public static Employee from(EmployeeDto employeeDto) {
        Employee employee = Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .age(employeeDto.getAge())
                .username(employeeDto.getUsername())
                .password(employeeDto.getPassword())
                .build();
        return employee;
    }
}
