package com.inheritedsequence.dto;

import com.inheritedsequence.entity.LineManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link com.inheritedsequence.entity.LineManager} entity
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LineManagerDto extends PersonDto {
    private String username;
    private String password;
    private List<EmployeeDto> employeesDto = new ArrayList<>();

    public static LineManagerDto from(LineManager lineManager) {
        LineManagerDto lineManagerDto = LineManagerDto.builder()
                .id(lineManager.getId())
                .firstName(lineManager.getFirstName())
                .lastName(lineManager.getLastName())
                .age(lineManager.getAge())
                .username(lineManager.getUsername())
                .password(lineManager.getPassword())
                .build();

        if(!lineManager.employeesIsNull()) {
            if(!lineManager.getEmployees().isEmpty()) {
                lineManager.getEmployees().forEach(employee -> {
                    lineManagerDto.addEmployeeDto(EmployeeDto.from(employee));
                });
            }
        }
        return lineManagerDto;
    }
    public void addEmployeeDto(EmployeeDto employeeDto) {
        employeesDto.add(employeeDto);
    }
    public boolean employeesDtoIsNull() {
        if(employeesDto == null) {
            return true;
        }
        else {
            return false;
        }
    }
}