package com.dcardprocessing.service;

import com.dcardprocessing.bean.UserScanActivity;
import com.dcardprocessing.generic.GenericService;

public interface UserScanActivityService extends GenericService<UserScanActivity>{

	int findMaxID();

}
