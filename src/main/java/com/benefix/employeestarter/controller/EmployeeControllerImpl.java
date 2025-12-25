package com.benefix.employeestarter.controller;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import com.benefix.employeestarter.service.EmployeeService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeControllerImpl implements EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeControllerImpl(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @Override
  public CompletableFuture<List<EmployeeResponseDTO>> findAll() {
    return employeeService.findAll();
  }

  @Override
  public CompletableFuture<EmployeeResponseDTO> findByEmployeeNo(String employeeNo) {
    return employeeService.findByEmployeeNo(employeeNo);
  }

  @Override
  public CompletableFuture<EmployeeResponseDTO> create(CreateEmployeeRequestDTO request) {
    return employeeService.create(request);
  }

  @Override
  public CompletableFuture<EmployeeResponseDTO> update(String employeeNo, UpdateEmployeeRequestDTO request) {
    return employeeService.update(employeeNo, request);
  }

  @Override
  public CompletableFuture<Void> delete(String employeeNo) {
    return employeeService.delete(employeeNo);
  }
}
