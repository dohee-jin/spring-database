package com.spring.database.jpa.chap03.repository;

import com.spring.database.jpa.chap03.entity.Department;
import com.spring.database.jpa.chap03.entity.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
