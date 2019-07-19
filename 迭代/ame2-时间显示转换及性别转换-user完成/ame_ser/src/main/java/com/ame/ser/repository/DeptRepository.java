package com.ame.ser.repository;import com.ame.ser.model.Dept;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Modifying;import org.springframework.data.jpa.repository.Query;import org.springframework.data.repository.query.Param;import org.springframework.transaction.annotation.Transactional;import java.util.List;/** * 科室/部门的持久层 * @author SWJ */public interface DeptRepository extends JpaRepository<Dept,String> {    Dept getByName(String name);    @Modifying    @Query(value="delete from Dept e where e.id in (:ids) ")    int deleteByDeptIdIn(@Param("ids")List<String> ids);}