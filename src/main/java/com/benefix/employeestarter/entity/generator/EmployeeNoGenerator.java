package com.benefix.employeestarter.entity.generator;

import java.time.Year;
import java.util.EnumSet;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;

public class EmployeeNoGenerator implements BeforeExecutionGenerator {

  private static final String SEQUENCE_QUERY = "SELECT nextval('employee_no_seq')";
  private static final String FORMAT = "EMP%02d-%03d";

  @Override
  public Object generate(
      SharedSessionContractImplementor session,
      Object owner,
      Object currentValue,
      EventType eventType) {

    if (currentValue != null) {
      return currentValue;
    }

    int year = Year.now().getValue() % 100;
    // Safer: use JDBC directly to avoid session state issues
    Long nextVal =
        session.doReturningWork(
            connection -> {
              try (var stmt = connection.prepareStatement(SEQUENCE_QUERY);
                  var rs = stmt.executeQuery()) {
                rs.next();
                return rs.getLong(1);
              }
            });
    return String.format(FORMAT, year, nextVal);
  }

  @Override
  public EnumSet<EventType> getEventTypes() {
    return EventTypeSets.INSERT_ONLY;
  }
}
