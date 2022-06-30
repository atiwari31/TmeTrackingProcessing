
package com.timetracking.repository;

import java.io.Serializable;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timetracking.bean.Image;
@Repository
public interface ImageRepositroy extends JpaRepository<Image, Long>{


}
