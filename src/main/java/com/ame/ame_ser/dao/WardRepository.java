package com.ame.ame_ser.dao;

import com.ame.ame_ser.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 病区持久层
 * @author SWJ
 */
public interface WardRepository extends JpaRepository<Ward,String>  {

    Ward getByName(String name);

}
