package com.benefix.employeestarter.entity.generator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Year;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;
import org.hibernate.jdbc.ReturningWork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeNoGeneratorTest {

  @Mock private SharedSessionContractImplementor session;

  @Mock private Connection connection;

  @Mock private PreparedStatement statement;

  @Mock private ResultSet resultSet;

  private EmployeeNoGenerator generator;

  @BeforeEach
  void setUp() {
    generator = new EmployeeNoGenerator();
  }

  @Test
  void generate_whenCurrentValueIsNotNull_returnsCurrentValue() {
    String existingValue = "EMP-20250001";

    Object result = generator.generate(session, null, existingValue, EventType.INSERT);

    assertEquals(existingValue, result);
    verifyNoInteractions(session);
  }

  @Test
  void generate_whenCurrentValueIsNull_generatesNewEmployeeNumber() throws Exception {
    long sequenceValue = 42L;
    int expectedYear = Year.now().getValue();
    String expectedFormat = String.format("EMP-%d%04d", expectedYear, sequenceValue);

    when(session.doReturningWork(any())).thenAnswer(invocation -> {
      ReturningWork<Long> work = invocation.getArgument(0);
      return work.execute(connection);
    });
    when(connection.prepareStatement(any())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getLong(1)).thenReturn(sequenceValue);

    Object result = generator.generate(session, null, null, EventType.INSERT);

    assertEquals(expectedFormat, result);
  }

  @Test
  void generate_formatsSequenceWithLeadingZeros() throws Exception {
    long sequenceValue = 1L;
    int expectedYear = Year.now().getValue();
    String expectedFormat = String.format("EMP-%d0001", expectedYear);

    when(session.doReturningWork(any())).thenAnswer(invocation -> {
      ReturningWork<Long> work = invocation.getArgument(0);
      return work.execute(connection);
    });
    when(connection.prepareStatement(any())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getLong(1)).thenReturn(sequenceValue);

    Object result = generator.generate(session, null, null, EventType.INSERT);

    assertEquals(expectedFormat, result);
  }

  @Test
  void getEventTypes_returnsInsertOnly() {
    assertEquals(EventTypeSets.INSERT_ONLY, generator.getEventTypes());
  }
}
