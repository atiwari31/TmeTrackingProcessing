package com.timetracking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timetracking.bean.Image;
import com.timetracking.genric.service.GenricService;
import com.timetracking.genric.service.ImageService;
import com.timetracking.repository.ImageRepositroy;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired 
	private ImageRepositroy imageRepositroy;

	@Override
	public Image save(Image entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image update(Image entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Image entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(List<Image> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Image> findAll() {
		// TODO Auto-generated method stub
		return imageRepositroy.findAll();
	}
	
	
	
	
	

}
