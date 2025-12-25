package com.benefix.employeestarter.service;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import java.util.List;

public interface EmployeeService {

  List<EmployeeResponseDTO> findAll();

  EmployeeResponseDTO findById(Long id);

  EmployeeResponseDTO findByEmployeeNo(String employeeNo);

  EmployeeResponseDTO create(CreateEmployeeRequestDTO request);

  EmployeeResponseDTO update(Long id, UpdateEmployeeRequestDTO request);

  void delete(Long id);
}
