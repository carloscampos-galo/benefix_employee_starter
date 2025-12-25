package com.benefix.employeestarter.controller;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/employees")
public interface EmployeeController {

  @GetMapping
  CompletableFuture<List<EmployeeResponseDTO>> findAll();

  @GetMapping("/{id}")
  CompletableFuture<EmployeeResponseDTO> findById(@PathVariable Long id);

  @GetMapping("/employee-no/{employeeNo}")
  CompletableFuture<EmployeeResponseDTO> findByEmployeeNo(@PathVariable String employeeNo);

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CompletableFuture<EmployeeResponseDTO> create(@Valid @RequestBody CreateEmployeeRequestDTO request);

  @PutMapping("/{id}")
  CompletableFuture<EmployeeResponseDTO> update(
      @PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequestDTO request);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  CompletableFuture<Void> delete(@PathVariable Long id);
}
