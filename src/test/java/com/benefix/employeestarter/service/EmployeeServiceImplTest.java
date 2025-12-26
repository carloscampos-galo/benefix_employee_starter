package com.benefix.employeestarter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import com.benefix.employeestarter.entity.EmployeeEntity;
import com.benefix.employeestarter.enums.Gender;
import com.benefix.employeestarter.exception.EmailAlreadyExistsException;
import com.benefix.employeestarter.exception.EmployeeNotFoundException;
import com.benefix.employeestarter.mapper.EmployeeMapper;
import com.benefix.employeestarter.repository.EmployeeRepository;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmployeeServiceImplTest {

  @Mock private EmployeeRepository employeeRepository;

  @Mock private EmployeeMapper employeeMapper;

  @InjectMocks private EmployeeServiceImpl employeeService;

  private EmployeeEntity employeeEntity;
  private EmployeeResponseDTO employeeResponseDTO;
  private CreateEmployeeRequestDTO createRequest;
  private UpdateEmployeeRequestDTO updateRequest;

  @BeforeEach
  void setUp() {
    employeeEntity = mock(EmployeeEntity.class);
    when(employeeEntity.getId()).thenReturn(1L);
    when(employeeEntity.getEmployeeNo()).thenReturn("EMP25-001");
    when(employeeEntity.getEmail()).thenReturn("john.doe@example.com");

    employeeResponseDTO =
        new EmployeeResponseDTO(
            1L,
            "EMP25-001",
            "Mr",
            "John",
            "Doe",
            LocalDate.of(1990, 1, 15),
            Gender.M,
            "john.doe@example.com",
            "123 Main St",
            "London",
            "SW1A 1AA",
            "UK",
            ZonedDateTime.now(),
            ZonedDateTime.now());

    createRequest =
        new CreateEmployeeRequestDTO(
            "Mr",
            "John",
            "Doe",
            LocalDate.of(1990, 1, 15),
            Gender.M,
            "john.doe@example.com",
            "123 Main St",
            "London",
            "SW1A 1AA",
            "UK");

    updateRequest =
        new UpdateEmployeeRequestDTO(
            "Mr",
            "John",
            "Doe",
            LocalDate.of(1990, 1, 15),
            Gender.M,
            "john.doe@example.com",
            "456 New St",
            "Manchester",
            "M1 1AA",
            "UK");
  }

  @Nested
  class FindAll {

    @Test
    void findAll_returnsListOfEmployees() throws Exception {
      when(employeeRepository.findAll()).thenReturn(List.of(employeeEntity));
      when(employeeMapper.toResponse(employeeEntity)).thenReturn(employeeResponseDTO);

      CompletableFuture<List<EmployeeResponseDTO>> result = employeeService.findAll();

      List<EmployeeResponseDTO> employees = result.get();
      assertEquals(1, employees.size());
      assertEquals(employeeResponseDTO, employees.get(0));
      verify(employeeRepository).findAll();
    }

    @Test
    void findAll_returnsEmptyListWhenNoEmployees() throws Exception {
      when(employeeRepository.findAll()).thenReturn(List.of());

      CompletableFuture<List<EmployeeResponseDTO>> result = employeeService.findAll();

      assertTrue(result.get().isEmpty());
    }
  }

  @Nested
  class FindById {

    @Test
    void findById_returnsEmployee_whenFound() throws Exception {
      when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeEntity));
      when(employeeMapper.toResponse(employeeEntity)).thenReturn(employeeResponseDTO);

      CompletableFuture<EmployeeResponseDTO> result = employeeService.findById(1L);

      assertEquals(employeeResponseDTO, result.get());
      verify(employeeRepository).findById(1L);
    }

    @Test
    void findById_throwsException_whenNotFound() {
      when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

      assertThrows(EmployeeNotFoundException.class, () -> employeeService.findById(999L));
    }
  }

  @Nested
  class FindByEmployeeNo {

    @Test
    void findByEmployeeNo_returnsEmployee_whenFound() throws Exception {
      when(employeeRepository.findByEmployeeNo("EMP25-001")).thenReturn(Optional.of(employeeEntity));
      when(employeeMapper.toResponse(employeeEntity)).thenReturn(employeeResponseDTO);

      CompletableFuture<EmployeeResponseDTO> result = employeeService.findByEmployeeNo("EMP25-001");

      assertEquals(employeeResponseDTO, result.get());
      verify(employeeRepository).findByEmployeeNo("EMP25-001");
    }

    @Test
    void findByEmployeeNo_throwsException_whenNotFound() {
      when(employeeRepository.findByEmployeeNo("INVALID")).thenReturn(Optional.empty());

      assertThrows(
          EmployeeNotFoundException.class, () -> employeeService.findByEmployeeNo("INVALID"));
    }
  }

  @Nested
  class Create {

    @Test
    void create_savesAndReturnsEmployee() throws Exception {
      when(employeeRepository.existsByEmail(createRequest.email())).thenReturn(false);
      when(employeeMapper.toEntity(createRequest)).thenReturn(employeeEntity);
      when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
      when(employeeMapper.toResponse(employeeEntity)).thenReturn(employeeResponseDTO);

      CompletableFuture<EmployeeResponseDTO> result = employeeService.create(createRequest);

      assertEquals(employeeResponseDTO, result.get());
      verify(employeeRepository).save(employeeEntity);
    }

    @Test
    void create_throwsException_whenEmailExists() {
      when(employeeRepository.existsByEmail(createRequest.email())).thenReturn(true);

      assertThrows(EmailAlreadyExistsException.class, () -> employeeService.create(createRequest));
      verify(employeeRepository, never()).save(any());
    }
  }

  @Nested
  class Update {

    @Test
    void update_updatesAndReturnsEmployee() throws Exception {
      when(employeeRepository.findByEmployeeNo("EMP25-001")).thenReturn(Optional.of(employeeEntity));
      when(employeeMapper.toBuilder(updateRequest)).thenReturn(EmployeeEntity.builder());
      when(employeeMapper.toResponse(employeeEntity)).thenReturn(employeeResponseDTO);

      CompletableFuture<EmployeeResponseDTO> result =
          employeeService.update("EMP25-001", updateRequest);

      assertEquals(employeeResponseDTO, result.get());
      verify(employeeEntity).update(any(EmployeeEntity.Builder.class));
    }

    @Test
    void update_throwsException_whenEmployeeNotFound() {
      when(employeeRepository.findByEmployeeNo("INVALID")).thenReturn(Optional.empty());

      assertThrows(
          EmployeeNotFoundException.class,
          () -> employeeService.update("INVALID", updateRequest));
    }

    @Test
    void update_throwsException_whenEmailExistsForDifferentEmployee() {
      UpdateEmployeeRequestDTO requestWithNewEmail =
          new UpdateEmployeeRequestDTO(
              "Mr",
              "John",
              "Doe",
              LocalDate.of(1990, 1, 15),
              Gender.M,
              "different@example.com",
              "456 New St",
              "Manchester",
              "M1 1AA",
              "UK");

      when(employeeRepository.findByEmployeeNo("EMP25-001")).thenReturn(Optional.of(employeeEntity));
      when(employeeRepository.existsByEmail("different@example.com")).thenReturn(true);

      assertThrows(
          EmailAlreadyExistsException.class,
          () -> employeeService.update("EMP25-001", requestWithNewEmail));
    }

    @Test
    void update_allowsSameEmail_whenEmailUnchanged() throws Exception {
      when(employeeRepository.findByEmployeeNo("EMP25-001")).thenReturn(Optional.of(employeeEntity));
      when(employeeMapper.toBuilder(updateRequest)).thenReturn(EmployeeEntity.builder());
      when(employeeMapper.toResponse(employeeEntity)).thenReturn(employeeResponseDTO);

      CompletableFuture<EmployeeResponseDTO> result =
          employeeService.update("EMP25-001", updateRequest);

      assertNotNull(result.get());
      verify(employeeRepository, never()).existsByEmail(any());
    }
  }

  @Nested
  class Delete {

    @Test
    void delete_softDeletesEmployee() throws Exception {
      when(employeeRepository.findByEmployeeNo("EMP25-001")).thenReturn(Optional.of(employeeEntity));

      CompletableFuture<Void> result = employeeService.delete("EMP25-001");

      result.get();
      verify(employeeEntity).delete();
    }

    @Test
    void delete_throwsException_whenEmployeeNotFound() {
      when(employeeRepository.findByEmployeeNo("INVALID")).thenReturn(Optional.empty());

      assertThrows(EmployeeNotFoundException.class, () -> employeeService.delete("INVALID"));
    }
  }
}
