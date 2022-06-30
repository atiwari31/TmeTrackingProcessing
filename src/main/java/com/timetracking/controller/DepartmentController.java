package com.timetracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timetracking.bean.Department;
import com.timetracking.service.impl.DepartmentServiceImpl;

@RestController
@RequestMapping("/timetracker")
public class DepartmentController {
	
	@Autowired
	private DepartmentServiceImpl departmentServiceImpl;
	
	@GetMapping("/Department")
	public List<Department> getDepartment(){
		List<Department> list=departmentServiceImpl.findAll();
		return list;
	}
	
	@PostMapping("/adddepartment")
	public void add(@RequestBody Department department) {
		
	}
@PutMapping
public void update() {
	
}
}
