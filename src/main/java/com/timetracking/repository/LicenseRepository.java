package com.dcardprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dcardprocessing.bean.License;
import com.dcardprocessing.bean.UserScanActivity;
//@Repository
public interface LicenseRepository extends JpaRepository<License, Long>{

}
