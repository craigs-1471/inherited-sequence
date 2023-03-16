package com.inheritedsequence.controller;

import com.inheritedsequence.dto.EmployeeDto;
import com.inheritedsequence.entity.Employee;
import com.inheritedsequence.service.EmployeeService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = Employee.from(employeeDto);
        employeeService.addEmployee(employee);
        return new ResponseEntity<>(EmployeeDto.from(employee), HttpStatus.CREATED);
    }
    @PostMapping("/add-many")
    public ResponseEntity<List<EmployeeDto>> addEmployees(@RequestBody List<EmployeeDto> employeesDto) {
        List<Employee> employees = employeesDto.stream().map(Employee::from).collect(Collectors.toList());
        employeeService.addEmployees(employees);
        return new ResponseEntity<>(employees.stream().map(EmployeeDto::from).collect(Collectors.toList()), HttpStatus.CREATED);
    }

    @PostMapping("/add-file")
    public ResponseEntity<String> uploadEmployees(@RequestParam("file")MultipartFile file) throws Exception {
        int currentEmployeeCount = employeeService.getCurrentEmployeeCount();
        List<EmployeeDto> employeeDtos = employeeService.uploadEmployees(file);
        int employeesRecorded = employeeDtos.size();
        int newEmployeeCount = employeeService.getCurrentEmployeeCount();
        int employeesPersisted = newEmployeeCount - currentEmployeeCount;
        String uploadResponse = String.format("No. entities identified: %s\nNo. entities persisted: %s", employeesRecorded, employeesPersisted);
        return new ResponseEntity<>(uploadResponse, HttpStatus.OK);
    }

}
