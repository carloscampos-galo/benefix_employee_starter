package com.benefix.employeestarter.entity.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.ValueGenerationType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValueGenerationType(generatedBy = EmployeeNoGenerator.class)
public @interface GeneratedEmployeeNo {}
