package com.ame.ame_ser.controller;

import com.ame.ame_ser.controllerEx.ParameterInvalidException;
import com.ame.ame_ser.dao.DeptRepository;
import com.ame.ame_ser.model.Dept;
import com.ame.ame_ser.service.DeptService;
import com.ame.ame_ser.vo.PageVO;
import com.ame.ame_ser.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门或科室控制器
 * @author SWJ
 */
@RestController
@RequestMapping("dept")
public class DeptController  extends BaseController{

    @Autowired
    private DeptService deptService;

    @RequestMapping("create")
    public ResultVo<Void> createDept(@RequestBody Dept dept){
        logger.info("科室创建时传入的参数{}",dept);
        if (dept.getName()==null || dept.getName().isEmpty()) {
            //部门名字是空的，不能创建
            throw new ParameterInvalidException("传入的参数是非法的，参考为:部门名字是空的");
        }
        //部门名字不是空的，可以创建
        deptService.createDept(dept);
        return  new ResultVo<>(SUCCESS);
    }

    @RequestMapping("show")
    public ResultVo<PageVO<List<Dept>>> showAllDeptOnPage(int pageNo, int pageSize){

        PageVO<List<Dept>> listDept = deptService.findAllPage(pageNo, pageSize);
        logger.info("分页结果：{}",listDept);

        return new ResultVo<>(SUCCESS, listDept);
    }

    @RequestMapping("/del/{id}")
    public ResultVo<Void> del(@PathVariable("id") String id ){
        deptService.delDeptById(id);

        return new ResultVo<>(SUCCESS);
    }

    @RequestMapping("update")
    public ResultVo<Void> update(@RequestBody Dept dept){
        if (dept.getName()==null) {
            throw new ParameterInvalidException("传入的参数有误");
        }

        deptService.update(dept.getDeptId() , dept.getName(), dept.getDeptDescribe());
        return  new ResultVo<>(SUCCESS );
    }


}
