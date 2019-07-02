package com.ame.ame_ser.dao;

import com.ame.ame_ser.model.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 科室/部门的持久层
 * @author SWJ
 */
public interface DeptRepository extends JpaRepository<Dept,String> {

    Dept getByName(String name);
}
