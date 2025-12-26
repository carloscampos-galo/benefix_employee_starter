package com.benefix.employeestarter.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import com.benefix.employeestarter.enums.Gender;
import com.benefix.employeestarter.service.EmployeeService;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerImplTest {

  @Mock private EmployeeService employeeService;

  @InjectMocks private EmployeeControllerImpl employeeController;

  private EmployeeResponseDTO employeeResponseDTO;
  private CreateEmployeeRequestDTO createRequest;
  private UpdateEmployeeRequestDTO updateRequest;

  @BeforeEach
  void setUp() {
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
    void findAll_delegatesToService() throws Exception {
      CompletableFuture<List<EmployeeResponseDTO>> expected =
          CompletableFuture.completedFuture(List.of(employeeResponseDTO));
      when(employeeService.findAll()).thenReturn(expected);

      CompletableFuture<List<EmployeeResponseDTO>> result = employeeController.findAll();

      assertEquals(expected, result);
      assertEquals(1, result.get().size());
      verify(employeeService).findAll();
    }

    @Test
    void findAll_returnsEmptyList() throws Exception {
      CompletableFuture<List<EmployeeResponseDTO>> expected =
          CompletableFuture.completedFuture(List.of());
      when(employeeService.findAll()).thenReturn(expected);

      CompletableFuture<List<EmployeeResponseDTO>> result = employeeController.findAll();

      assertTrue(result.get().isEmpty());
    }
  }

  @Nested
  class FindByEmployeeNo {

    @Test
    void findByEmployeeNo_delegatesToService() throws Exception {
      CompletableFuture<EmployeeResponseDTO> expected =
          CompletableFuture.completedFuture(employeeResponseDTO);
      when(employeeService.findByEmployeeNo("EMP25-001")).thenReturn(expected);

      CompletableFuture<EmployeeResponseDTO> result =
          employeeController.findByEmployeeNo("EMP25-001");

      assertEquals(expected, result);
      assertEquals(employeeResponseDTO, result.get());
      verify(employeeService).findByEmployeeNo("EMP25-001");
    }
  }

  @Nested
  class Create {

    @Test
    void create_delegatesToService() throws Exception {
      CompletableFuture<EmployeeResponseDTO> expected =
          CompletableFuture.completedFuture(employeeResponseDTO);
      when(employeeService.create(createRequest)).thenReturn(expected);

      CompletableFuture<EmployeeResponseDTO> result = employeeController.create(createRequest);

      assertEquals(expected, result);
      assertEquals(employeeResponseDTO, result.get());
      verify(employeeService).create(createRequest);
    }
  }

  @Nested
  class Update {

    @Test
    void update_delegatesToService() throws Exception {
      CompletableFuture<EmployeeResponseDTO> expected =
          CompletableFuture.completedFuture(employeeResponseDTO);
      when(employeeService.update("EMP25-001", updateRequest)).thenReturn(expected);

      CompletableFuture<EmployeeResponseDTO> result =
          employeeController.update("EMP25-001", updateRequest);

      assertEquals(expected, result);
      assertEquals(employeeResponseDTO, result.get());
      verify(employeeService).update("EMP25-001", updateRequest);
    }
  }

  @Nested
  class Delete {

    @Test
    void delete_delegatesToService() throws Exception {
      CompletableFuture<Void> expected = CompletableFuture.completedFuture(null);
      when(employeeService.delete("EMP25-001")).thenReturn(expected);

      CompletableFuture<Void> result = employeeController.delete("EMP25-001");

      assertEquals(expected, result);
      assertNull(result.get());
      verify(employeeService).delete("EMP25-001");
    }
  }
}
