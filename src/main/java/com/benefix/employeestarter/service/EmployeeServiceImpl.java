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
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
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
  @Async
  @Transactional(readOnly = true)
  public CompletableFuture<List<EmployeeResponseDTO>> findAll() {
    List<EmployeeResponseDTO> result =
        employeeRepository.findAll().stream().map(employeeMapper::toResponse).toList();
    return CompletableFuture.completedFuture(result);
  }

  @Override
  @Async
  @Transactional(readOnly = true)
  public CompletableFuture<EmployeeResponseDTO> findById(Long id) {
    EmployeeResponseDTO result =
        employeeRepository
            .findById(id)
            .map(employeeMapper::toResponse)
            .orElseThrow(EmployeeNotFoundException.withId(id));
    return CompletableFuture.completedFuture(result);
  }

  @Override
  @Async
  @Transactional(readOnly = true)
  public CompletableFuture<EmployeeResponseDTO> findByEmployeeNo(String employeeNo) {
    EmployeeResponseDTO result =
        employeeRepository
            .findByEmployeeNo(employeeNo)
            .map(employeeMapper::toResponse)
            .orElseThrow(EmployeeNotFoundException.withEmployeeNo(employeeNo));
    return CompletableFuture.completedFuture(result);
  }

  @Override
  @Async
  @Transactional
  public CompletableFuture<EmployeeResponseDTO> create(CreateEmployeeRequestDTO request) {
    if (employeeRepository.existsByEmail(request.email())) {
      throw new EmailAlreadyExistsException(request.email());
    }

    EmployeeEntity saved = employeeRepository.save(employeeMapper.toEntity(request));
    return CompletableFuture.completedFuture(employeeMapper.toResponse(saved));
  }

  @Override
  @Async
  @Transactional
  public CompletableFuture<EmployeeResponseDTO> update(String employeeNo, UpdateEmployeeRequestDTO request) {
    EmployeeEntity entity =
        employeeRepository.findByEmployeeNo(employeeNo).orElseThrow(EmployeeNotFoundException.withEmployeeNo(employeeNo));

    if (!entity.getEmail().equals(request.email())
        && employeeRepository.existsByEmail(request.email())) {
      throw new EmailAlreadyExistsException(request.email());
    }

    entity.update(employeeMapper.toBuilder(request));
    return CompletableFuture.completedFuture(employeeMapper.toResponse(entity));
  }

  @Override
  @Async
  @Transactional
  public CompletableFuture<Void> delete(String employeeNo) {
    EmployeeEntity entity =
        employeeRepository.findByEmployeeNo(employeeNo).orElseThrow(EmployeeNotFoundException.withEmployeeNo(employeeNo));
    entity.delete();
    return CompletableFuture.completedFuture(null);
  }
}
