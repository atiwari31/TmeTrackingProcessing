package com.timetracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.timetracking.bean.Account;
import com.timetracking.service.impl.AccountServiceImpl;


@RestController
@RequestMapping("/timetracker")
public class AccountController {
	

	   @Autowired
		private  AccountServiceImpl accountServiceImpl;
		
		
	   @GetMapping("/account")
	   @ResponseBody
	   public ResponseEntity<List<Account>> getaccount() {
		   List<Account> list= accountServiceImpl.findAll();
		   return  ResponseEntity.ok().body(list);
		
	   }
	   
	   @PostMapping("/addaccount")
	   public void addAccount(@RequestBody Account account) {
		
	   }
	  
	}


