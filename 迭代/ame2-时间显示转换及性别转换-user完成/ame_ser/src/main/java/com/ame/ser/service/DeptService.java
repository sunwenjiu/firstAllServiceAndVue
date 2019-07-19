package com.ame.ser.service;

import com.ame.ser.model.Dept;
import com.ame.ser.serviceEx.TableFieldContentDuplicateException;
import com.ame.ser.vo.PageVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 科室/部门的业务层接口
 * @author SWJ
 */
public interface DeptService {
    void createDept(Dept dept) throws TableFieldContentDuplicateException;
    Page<Dept> findAllPage(int pageNo, int pageSize);
    void delDeptById(String deptId);
    void deleteByDeptIdIn(List<String > ids);
    void update(String deptId,String name,String deptDescribe);
}
