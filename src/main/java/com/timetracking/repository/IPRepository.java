package com.dcardprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcardprocessing.bean.License;
import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.bean.UserScanActivity;

public interface IPRepository extends JpaRepository<ScanActivity, Long> {

}
