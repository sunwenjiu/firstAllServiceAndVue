package com.ame.ame_ser.service;

import com.ame.ame_ser.model.Dept;
import com.ame.ame_ser.serviceEx.TableFieldContentDuplicateException;
import com.ame.ame_ser.vo.PageVO;

import java.util.List;

/**
 * 科室/部门的业务层接口
 * @author SWJ
 */
public interface DeptService {
    void createDept(Dept dept) throws TableFieldContentDuplicateException;
    PageVO<List<Dept>> findAllPage(int pageNo, int pageSize);
    void delDeptById(String deptId);
    void update(String deptId,String name,String deptDescribe);
}
