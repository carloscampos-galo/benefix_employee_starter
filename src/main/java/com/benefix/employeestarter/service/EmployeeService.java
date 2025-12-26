package com.benefix.employeestarter.service;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmployeeService {

  CompletableFuture<List<EmployeeResponseDTO>> findAll();

  CompletableFuture<EmployeeResponseDTO> findByEmployeeNo(String employeeNo);

  CompletableFuture<EmployeeResponseDTO> create(CreateEmployeeRequestDTO request);

  CompletableFuture<EmployeeResponseDTO> update(String employeeNo, UpdateEmployeeRequestDTO request);

  CompletableFuture<Void> delete(String employeeNo);
}
