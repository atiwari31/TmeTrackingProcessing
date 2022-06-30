package com.timetracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timetracking.bean.Image;
import com.timetracking.service.impl.ImageServiceImpl;

@RestController
@RequestMapping("/timetracker")
public class ImageController {
	
	@Autowired
	private ImageServiceImpl imageServiceImpl;
	
	
   @GetMapping("/image")
   public List<Image> getimage() {
	   List<Image> list= imageServiceImpl.findAll();
	   return list;
	
   }
   
   @PostMapping("/addimage")
   public void add(@RequestBody Image image) {
	
   }
   @PutMapping
   public void update() {
	
   }
}
