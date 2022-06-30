package com.timetracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timetracking.bean.Department;



@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {

	

}
