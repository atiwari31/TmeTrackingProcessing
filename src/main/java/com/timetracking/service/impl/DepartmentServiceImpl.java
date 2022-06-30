package com.timetracking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetracking.bean.Department;
import com.timetracking.genric.service.DepartmentService;
import com.timetracking.repository.DepartmentRepository;


@Service
public class DepartmentServiceImpl implements DepartmentService{
          
	@Autowired
	private  DepartmentRepository departmentService;

	@Override
	public Department save(Department entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Department update(Department entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Department entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(List<Department> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

