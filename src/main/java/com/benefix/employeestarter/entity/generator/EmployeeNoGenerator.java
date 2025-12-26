package com.benefix.employeestarter.entity.generator;

import java.sql.SQLException;
import java.time.Year;
import java.util.EnumSet;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;

public class EmployeeNoGenerator implements BeforeExecutionGenerator {

  private static final String SEQUENCE_QUERY = "SELECT nextval('employee_no_seq')";
  private static final String FORMAT = "EMP-%d%04d";

  @Override
  public Object generate(
      SharedSessionContractImplementor session,
      Object owner,
      Object currentValue,
      EventType eventType) {

    if (currentValue != null) {
      return currentValue;
    }

    int year = Year.now().getValue();
    Long nextVal =
        session.doReturningWork(
            connection -> {
              try (var stmt = connection.prepareStatement(SEQUENCE_QUERY);
                  var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                  throw new SQLException("Failed to retrieve next value from employee_no_seq");
                }
                return rs.getLong(1);
              } catch (SQLException e) {
                throw new HibernateException("Failed to generate employee number", e);
              }
            });
    return String.format(FORMAT, year, nextVal);
  }

  @Override
  public EnumSet<EventType> getEventTypes() {
    return EventTypeSets.INSERT_ONLY;
  }
}
