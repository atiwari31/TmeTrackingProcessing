package com.dcardprocessing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcardprocessing.bean.UserScanActivity;



public interface UserScanActivityRepository extends JpaRepository<UserScanActivity, Long>{
    @Query(value="SELECT MAX(id) FROM User_Scan_Activity ORDER BY id DESC", nativeQuery = true)
    int findMaxID();
	
}
