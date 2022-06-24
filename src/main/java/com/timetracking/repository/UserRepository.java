package com.dcardprocessing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dcardprocessing.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	
	@Query(value="SELECT * FROM user WHERE created_by=?1",nativeQuery = true)
	List<User> findByCreatedId(int createdId);
	 
	@Query(value="SELECT * FROM user WHERE role='Admin'",nativeQuery = true)
	  List<User> findByAllAdmin();
}
