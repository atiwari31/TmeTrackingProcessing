package com.dcardprocessing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcardprocessing.bean.ScanActivity;
import com.dcardprocessing.bean.ScannedFile;
import com.dcardprocessing.bean.User;




public interface ScanActivityRepository extends JpaRepository<ScanActivity, Long>{
    @Query(value="SELECT * FROM scan_activity WHERE is_deleted='false' AND scan_type_id=2 AND (current_status='SCAN' OR current_status='REQUEST FOR APPROVAL')" 
   , nativeQuery = true)
    List<ScanActivity> findByUserIsDeletedFlag();
    
    @Query(value="SELECT * FROM scan_activity WHERE is_deleted='false' AND scan_type_id=1 AND (current_status='SCAN' OR current_status='REQUEST FOR APPROVAL')" 
    		   , nativeQuery = true)
    		    List<ScanActivity> findByUserIsDeletedFlagIP();

    @Query(value="SELECT * FROM Scan_Activity WHERE scan_type_id=2  and is_deleted='true' and (update_status='REQUEST FOR APPROVAL' or update_status='Deleted') ", nativeQuery = true)
    List<ScanActivity> findByIsDeletedFlag();
    
 
  
    @Query(value="SELECT * FROM scan_activity WHERE id IN (SELECT MAX(id) AS id  FROM scan_activity WHERE is_deleted='false' AND scan_type_id=3 GROUP BY user_scan_activity_id) AND STATUS='SCAN'", nativeQuery = true)
    List<ScanActivity> findByStatus();
    
    @Query(value="SELECT * FROM Scan_Activity WHERE is_deleted='true' and scan_type_id=1 and (update_status='REQUEST FOR APPROVAL' or update_status='Deleted') ", nativeQuery = true)
    List<ScanActivity> findByIsDeletedFlagIP();
    
    @Query(value="SELECT * FROM Scan_Activity WHERE is_deleted='true' and scan_type_id=3 and (update_status='REQUEST FOR APPROVAL' or update_status='Deleted') ", nativeQuery = true)
    List<ScanActivity> findByIsDeletedFlagMYSQLADMIN();
    
    @Query(value="SELECT * FROM Scan_Activity WHERE is_deleted='false' AND scan_type_id=3 AND (current_status='SCAN' OR current_status='REQUEST FOR APPROVAL')", nativeQuery = true)
    List<ScanActivity> findByIsDeletedFlagMYSQL();
    
    @Query(value="SELECT * FROM Scan_Activity WHERE is_deleted='false' and scan_type_id=4 and status='REQUEST FOR APPROVAL'", nativeQuery = true)
    List<ScanActivity> findByIsDeletedFlagOracle();

    @Query(value="SELECT MAX(id) FROM Scan_Activity ORDER BY id DESC", nativeQuery = true)
    int findLatestID();
    

    @Query(value="SELECT * FROM scan_activity WHERE  user_scan_activity_id=?1 order by scan_detail", nativeQuery = true)
    List<ScanActivity>  findByUserScanActivityId(int user_scan_activity_id);
    
    @Query(value="update scan_activity set is_deleted='true' WHERE  user_scan_activity_id=?1 ", nativeQuery = true)
    void  updateByUserScanActivityId(int user_scan_activity_id);
    
    @Query(value="SELECT * FROM scan_activity WHERE scan_type_detail=?1 AND scan_detail=?2" 
    		   , nativeQuery = true)
    		    ScanActivity countForFileName(String scanTypeDetail,String scanDetail);
    
    
    @Query(value="SELECT * FROM scan_activity WHERE is_deleted='false' AND scan_type_id=2 AND (current_status='SCAN' AND type='False Positive')" 
   , nativeQuery = true)
    List<ScanActivity> duplicateFileChek();
    @Query(value="SELECT * FROM scan_activity WHERE is_deleted='false' AND scan_type_id=1 AND (current_status='SCAN' AND type='False Positive')" 
    		   , nativeQuery = true)
    		    List<ScanActivity> duplicateFileChekIP();

}
