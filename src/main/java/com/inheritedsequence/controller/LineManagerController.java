package com.inheritedsequence.controller;

import com.inheritedsequence.dto.EmployeeDto;
import com.inheritedsequence.dto.LineManagerDto;
import com.inheritedsequence.entity.LineManager;
import com.inheritedsequence.repository.EmployeeRepository;
import com.inheritedsequence.service.LineManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/line-managers")
public class LineManagerController {
    private LineManagerService lineManagerService;
    private final EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<LineManagerDto> addLineManager(@RequestBody LineManagerDto lineManagerDto) {
        LineManager lineManager = LineManager.from(lineManagerDto);
        lineManagerService.addLineManager(lineManager);
        return new ResponseEntity<>(LineManagerDto.from(lineManager), HttpStatus.CREATED);
    }

    @PostMapping("/add-many")
    public ResponseEntity<List<LineManagerDto>> addLineManagers(@RequestBody List<LineManagerDto> lineManagersDto) {
        List<LineManager> lineManagers = lineManagersDto.stream().map(LineManager::from).collect(Collectors.toList());
        lineManagerService.addLineManagers(lineManagers);
        return new ResponseEntity<>(lineManagers.stream().map(LineManagerDto::from).collect(Collectors.toList()), HttpStatus.CREATED);
    }
    @PostMapping("/add-file")
    public ResponseEntity<String> uploadLineManagers(@RequestParam("file") MultipartFile file) throws Exception {
        int currentLineManagerCount = lineManagerService.getCurrentLineManagerCount();
        List<LineManagerDto> lineManagersDto = lineManagerService.uploadLineManagers(file);
        int lineManagersRecorded = lineManagersDto.size();
        int newLineManagerCount = lineManagerService.getCurrentLineManagerCount();
        int lineManagersPersisted = newLineManagerCount - currentLineManagerCount;
        String uploadResponse = String.format("No. entities identified: %s\nNo. entities persisted: %s", lineManagersRecorded, lineManagersPersisted);
        return new ResponseEntity<>(uploadResponse, HttpStatus.OK);
    }
    @GetMapping
    public int getCount() {
        return lineManagerService.getCurrentLineManagerCount();
    }
}
