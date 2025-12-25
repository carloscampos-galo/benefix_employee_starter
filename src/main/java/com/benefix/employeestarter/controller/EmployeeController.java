package com.benefix.employeestarter.controller;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee", description = "Employee management APIs")
@RequestMapping("/api/employees")
public interface EmployeeController {

  @Operation(summary = "Get all employees", description = "Retrieves a list of all active employees")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved employees")
  @GetMapping
  CompletableFuture<List<EmployeeResponseDTO>> findAll();

  @Operation(summary = "Get employee by ID", description = "Retrieves an employee by their database ID")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employee"),
    @ApiResponse(responseCode = "404", description = "Employee not found")
  })
  @GetMapping("/{id}")
  CompletableFuture<EmployeeResponseDTO> findById(@Parameter(description = "Employee ID") @PathVariable Long id);

  @Operation(summary = "Get employee by employee number", description = "Retrieves an employee by their employee number")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employee"),
    @ApiResponse(responseCode = "404", description = "Employee not found")
  })
  @GetMapping("/employee-no/{employeeNo}")
  CompletableFuture<EmployeeResponseDTO> findByEmployeeNo(
      @Parameter(description = "Employee number") @PathVariable String employeeNo);

  @Operation(summary = "Create employee", description = "Creates a new employee")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Employee created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request data"),
    @ApiResponse(responseCode = "409", description = "Email already exists")
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CompletableFuture<EmployeeResponseDTO> create(@Valid @RequestBody CreateEmployeeRequestDTO request);

  @Operation(summary = "Update employee", description = "Updates an existing employee")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request data"),
    @ApiResponse(responseCode = "404", description = "Employee not found"),
    @ApiResponse(responseCode = "409", description = "Email already exists")
  })
  @PutMapping("/{id}")
  CompletableFuture<EmployeeResponseDTO> update(
      @Parameter(description = "Employee ID") @PathVariable Long id,
      @Valid @RequestBody UpdateEmployeeRequestDTO request);

  @Operation(summary = "Delete employee", description = "Soft deletes an employee")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Employee not found")
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  CompletableFuture<Void> delete(@Parameter(description = "Employee ID") @PathVariable Long id);
}
