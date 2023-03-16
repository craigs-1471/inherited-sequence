package com.inheritedsequence.entity;

import com.inheritedsequence.dto.LineManagerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="line_managers")
public class LineManager extends Person {
    private String username;
    private String password;
    @OneToMany(mappedBy="lineManager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public static LineManager from(LineManagerDto lineManagerDto) {
        LineManager lineManager = LineManager.builder()
                .id(lineManagerDto.getId())
                .firstName(lineManagerDto.getFirstName())
                .lastName(lineManagerDto.getLastName())
                .age(lineManagerDto.getAge())
                .username(lineManagerDto.getUsername())
                .password(lineManagerDto.getPassword())
                .build();
        if(!lineManagerDto.employeesDtoIsNull()) {
            if(!lineManagerDto.getEmployeesDto().isEmpty()) {
                lineManagerDto.getEmployeesDto().forEach(employeeDto -> {
                    lineManager.addEmployee(Employee.from(employeeDto));
                });
            }
        }
        return lineManager;
    }
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    public boolean employeesIsNull() {
        if(employees == null) {
            return true;
        }
        else {
            return false;
        }
    }
}
