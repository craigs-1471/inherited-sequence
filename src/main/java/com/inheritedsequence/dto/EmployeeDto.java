package com.inheritedsequence.dto;

import com.inheritedsequence.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 * A DTO for the {@link com.inheritedsequence.entity.Employee} entity
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto extends PersonDto {
    private String username;
    private String password;
    private LineManagerDto lineManagerDto;
    public static EmployeeDto from(Employee employee) {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .age(employee.getAge())
                .username(employee.getUsername())
                .password(employee.getPassword())
                .build();
        if(Objects.nonNull(employee.getLineManager())) {

        }
        return employeeDto;
    }
}