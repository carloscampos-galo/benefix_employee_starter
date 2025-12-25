package com.benefix.employeestarter.controller;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import com.benefix.employeestarter.service.EmployeeService;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeControllerImpl implements EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeControllerImpl(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @Override
  public List<EmployeeResponseDTO> findAll() {
    return employeeService.findAll();
  }

  @Override
  public EmployeeResponseDTO findById(Long id) {
    return employeeService.findById(id);
  }

  @Override
  public EmployeeResponseDTO findByEmployeeNo(String employeeNo) {
    return employeeService.findByEmployeeNo(employeeNo);
  }

  @Override
  public EmployeeResponseDTO create(CreateEmployeeRequestDTO request) {
    return employeeService.create(request);
  }

  @Override
  public EmployeeResponseDTO update(Long id, UpdateEmployeeRequestDTO request) {
    return employeeService.update(id, request);
  }

  @Override
  public void delete(Long id) {
    employeeService.delete(id);
  }
}
