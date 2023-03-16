package com.inheritedsequence.service;

import com.inheritedsequence.dto.EmployeeDto;
import com.inheritedsequence.entity.Employee;
import com.inheritedsequence.repository.EmployeeRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> addEmployees(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }
    public List<EmployeeDto> uploadEmployees(MultipartFile file) throws IOException {
        List<EmployeeDto> employeesDto = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser csvParser = new CsvParser(settings);
        List<Record> parseAllRecords = csvParser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setFirstName(record.getString("first_name"));
            employeeDto.setLastName(record.getString("last_name"));
            employeeDto.setAge(Integer.parseInt(record.getString("age")));
            employeeDto.setUsername(record.getString("username"));
            employeeDto.setPassword(record.getString("password"));
            employeesDto.add(employeeDto);
        });
        List<Employee> employees = employeesDto.stream().map(Employee::from).collect(Collectors.toList());
        addEmployees(employees);
        return employeesDto;
    }

    public int getCurrentEmployeeCount() {
        return (int) employeeRepository.count();
    }
}
