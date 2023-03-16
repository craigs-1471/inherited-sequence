package com.inheritedsequence.service;

import com.inheritedsequence.dto.EmployeeDto;
import com.inheritedsequence.dto.LineManagerDto;
import com.inheritedsequence.entity.Employee;
import com.inheritedsequence.entity.LineManager;
import com.inheritedsequence.repository.LineManagerRepository;
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
public class LineManagerService {
    private LineManagerRepository lineManagerRepository;

    public LineManager addLineManager(LineManager lineManager) {
        return lineManagerRepository.save(lineManager);
    }

    public List<LineManager> addLineManagers(List<LineManager> lineManagers) {
        return lineManagerRepository.saveAll(lineManagers);
    }

    public int getCurrentLineManagerCount() {
        return (int) lineManagerRepository.count();
    }

    public List<LineManagerDto> uploadLineManagers(MultipartFile file) throws IOException {
        List<LineManagerDto> lineManagersDto = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser csvParser = new CsvParser(settings);
        List<Record> parseAllRecords = csvParser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            LineManagerDto lineManagerDto = new LineManagerDto();
            lineManagerDto.setFirstName(record.getString("first_name"));
            lineManagerDto.setLastName(record.getString("last_name"));
            lineManagerDto.setAge(Integer.parseInt(record.getString("age")));
            lineManagerDto.setUsername(record.getString("username"));
            lineManagerDto.setPassword(record.getString("password"));
            lineManagersDto.add(lineManagerDto);
        });
        List<LineManager> lineManagers = lineManagersDto.stream().map(LineManager::from).collect(Collectors.toList());
        addLineManagers(lineManagers);
        return lineManagersDto;
    }
}
