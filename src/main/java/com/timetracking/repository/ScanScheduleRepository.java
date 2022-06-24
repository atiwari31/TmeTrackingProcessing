package com.dcardprocessing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dcardprocessing.bean.ScanSchedule;
@Repository
public interface ScanScheduleRepository extends JpaRepository<ScanSchedule, Long>{

    @Query(value="SELECT * FROM scan_schedule WHERE scan_date=CURDATE()" 
   , nativeQuery = true)
    List<ScanSchedule> getScanScheduleByDate(); 
    
    @Query(value="SELECT * FROM scan_schedule WHERE user_id=?1" 
    		   ,nativeQuery = true)
    		    List<ScanSchedule> getScheduleByUserId(int userId); 
}
