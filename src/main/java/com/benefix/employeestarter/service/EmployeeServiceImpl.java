package com.benefix.employeestarter.service;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import com.benefix.employeestarter.entity.EmployeeEntity;
import com.benefix.employeestarter.exception.EmailAlreadyExistsException;
import com.benefix.employeestarter.exception.EmployeeNotFoundException;
import com.benefix.employeestarter.mapper.EmployeeMapper;
import com.benefix.employeestarter.repository.EmployeeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;

  public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
    this.employeeRepository = employeeRepository;
    this.employeeMapper = employeeMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<EmployeeResponseDTO> findAll() {
    return employeeRepository.findAll().stream().map(employeeMapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public EmployeeResponseDTO findById(Long id) {
    return employeeRepository
        .findById(id)
        .map(employeeMapper::toResponse)
        .orElseThrow(EmployeeNotFoundException.withId(id));
  }

  @Override
  @Transactional(readOnly = true)
  public EmployeeResponseDTO findByEmployeeNo(String employeeNo) {
    return employeeRepository
        .findByEmployeeNo(employeeNo)
        .map(employeeMapper::toResponse)
        .orElseThrow(EmployeeNotFoundException.withEmployeeNo(employeeNo));
  }

  @Override
  @Transactional
  public EmployeeResponseDTO create(CreateEmployeeRequestDTO request) {
    if (employeeRepository.existsByEmail(request.email())) {
      throw new EmailAlreadyExistsException(request.email());
    }

    EmployeeEntity saved = employeeRepository.save(employeeMapper.toEntity(request));
    return employeeMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public EmployeeResponseDTO update(Long id, UpdateEmployeeRequestDTO request) {
    EmployeeEntity entity =
        employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException.withId(id));

    if (!entity.getEmail().equals(request.email())
        && employeeRepository.existsByEmail(request.email())) {
      throw new EmailAlreadyExistsException(request.email());
    }

    entity.update(employeeMapper.toBuilder(request));
    return employeeMapper.toResponse(entity);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    EmployeeEntity entity =
        employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException.withId(id));
    entity.delete();
  }
}
