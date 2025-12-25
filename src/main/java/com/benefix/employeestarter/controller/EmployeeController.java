package com.benefix.employeestarter.controller;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/employees")
public interface EmployeeController {

  @GetMapping
  List<EmployeeResponseDTO> findAll();

  @GetMapping("/{id}")
  EmployeeResponseDTO findById(@PathVariable Long id);

  @GetMapping("/employee-no/{employeeNo}")
  EmployeeResponseDTO findByEmployeeNo(@PathVariable String employeeNo);

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  EmployeeResponseDTO create(@Valid @RequestBody CreateEmployeeRequestDTO request);

  @PutMapping("/{id}")
  EmployeeResponseDTO update(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequestDTO request);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void delete(@PathVariable Long id);
}
