package com.ame.ser.service;

import com.ame.ser.model.Dept;
import com.ame.ser.service.ex.TableFieldContentDuplicateException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 科室/部门的业务层接口
 * @author SWJ
 */
public interface DeptService {
    /**
     * 创建科室
     * @param dept
     * @throws TableFieldContentDuplicateException
     */
    void createDept(Dept dept) throws TableFieldContentDuplicateException;

    /**
     * 分页查询部门数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Dept> findAllPage(int pageNo, int pageSize);

    /**
     * 查询全部的部门数据（填报事件页面用到）
     * @return
     */
    List<Dept> findAll();

    /**
     * 单个删除部门数据
     * @param deptId
     */
    void delDeptById(String deptId);

    /**
     * 通过id批量删除部门数据
     * @param ids
     */
    void deleteByDeptIdIn(List<String > ids);

    /**
     * 通过id更新部门名字和描述
     * @param deptId
     * @param name
     * @param deptDescribe
     */
    void update(String deptId,String name,String deptDescribe);
}
