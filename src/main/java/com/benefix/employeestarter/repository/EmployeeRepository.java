package com.benefix.employeestarter.repository;

import com.benefix.employeestarter.entity.EmployeeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

  Optional<EmployeeEntity> findByEmployeeNo(String employeeNo);

  boolean existsByEmail(String email);
}
