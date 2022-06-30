package com.timetracking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetracking.bean.Account;
import com.timetracking.genric.service.AccountService;
import com.timetracking.genric.service.GenricService;
import com.timetracking.repository.AccountRepositroy;


@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired 
	private AccountRepositroy accountRepositroy;

	@Override
	public Account save(Account entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account update(Account entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Account entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(List<Account> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Account find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return accountRepositroy.findAll();
	}

	
	
	
	
	

}


