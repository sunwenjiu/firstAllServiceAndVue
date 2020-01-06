package com.ame.ser.controller;

import com.ame.ser.service.ex.ParameterInvalidException;
import com.ame.ser.model.Dept;
import com.ame.ser.service.DeptService;
import com.ame.ser.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门或科室控制器
 * @author SWJ
 */
@Slf4j
@RestController
@RequestMapping("dept")
public class DeptController  extends BaseController{

    @Autowired
    private DeptService deptService;

    /**
     * 创建科室
     * @param dept
     * @throws
     */
    @RequestMapping("create")
    public ResultVO<Void> createDept(@RequestBody Dept dept){
       // logger.info("科室创建时传入的参数{}",dept);
        if (dept.getName()==null || dept.getName().isEmpty()) {
            //部门名字是空的，不能创建
            log.error("创建部门时，传入的参数是非法的，参考为:部门名字是空的");
            throw new ParameterInvalidException("传入的参数是非法的，参考为:部门名字是空的");
        }
        //部门名字不是空的，可以创建
        deptService.createDept(dept);
        return new ResultVO<>(SUCCESS);
    }

    /**
     * 分页查询部门数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("show")
    public ResultVO<Page<Dept>> showAllDeptOnPage(int pageNo, int pageSize){
        Page<Dept> PageDept =  deptService.findAllPage(pageNo, pageSize);
        return new ResultVO<>(SUCCESS,PageDept);
    }

    /**
     * 查询全部的部门数据（填报事件页面用到）
     * @return
     */
    @RequestMapping("showAll")
    public ResultVO<List<Dept>> showAllDept(){
        List<Dept> PageDept =  deptService.findAll();
        return new ResultVO<>(SUCCESS,PageDept);
    }


    /**
     * 单个删除部门数据
     * @param id
     * @return
     */
    @RequestMapping("/del/{id}")
    public ResultVO<Void> del(@PathVariable("id") String id ){
        deptService.delDeptById(id);
        return new ResultVO<>(SUCCESS);
    }

    /**
     * 通过ids批量删除
     * @param ids
     * @return
     */
    @RequestMapping("delAll")
    public ResultVO<Void> deleteByDeptIdIn(  String ids){
        List<String> LString = regDelAllData(ids);
        deptService.deleteByDeptIdIn(LString);
        return new ResultVO<>(SUCCESS);
    }


    /**
     * 通过id更新部门名字和描述
     * @param dept
     * @return
     */
    @RequestMapping("update")
    public ResultVO<Void> update(@RequestBody Dept dept){
        if (dept.getName()==null) {
            log.error("修改部门时，传入的参数是非法的，参考为:部门名字是空的");
            throw new ParameterInvalidException("传入的参数有误");
        }
        deptService.update(dept.getDeptId() , dept.getName(), dept.getDeptDescribe());
        return new ResultVO<>(SUCCESS);
    }


}

