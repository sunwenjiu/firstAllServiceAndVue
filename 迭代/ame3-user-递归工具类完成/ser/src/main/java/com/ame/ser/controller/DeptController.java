package com.ame.ser.controller;

import com.ame.ser.service.ex.ParameterInvalidException;
import com.ame.ser.model.Dept;
import com.ame.ser.service.DeptService;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public ResultVO<Void> createDept(@RequestBody Dept dept){
        logger.info("科室创建时传入的参数{}",dept);
        if (dept.getName()==null || dept.getName().isEmpty()) {
            //部门名字是空的，不能创建
            throw new ParameterInvalidException("传入的参数是非法的，参考为:部门名字是空的");
        }
        //部门名字不是空的，可以创建
        deptService.createDept(dept);
        return new ResultVO<>(SUCCESS);
    }

    @RequestMapping("show")
    public ResultVO<Page<Dept>> showAllDeptOnPage(int pageNo, int pageSize){
        Page<Dept> PageDept =  deptService.findAllPage(pageNo, pageSize);

        return new ResultVO<>(SUCCESS,PageDept);
    }

    @RequestMapping("/del/{id}")
    public ResultVO<Void> del(@PathVariable("id") String id ){
        deptService.delDeptById(id);

        return new ResultVO<>(SUCCESS);
    }
    @RequestMapping("delAll")
    public ResultVO<Void> deleteByDeptIdIn(  String ids){


        List<String> LString = regDelAllData(ids);
        deptService.deleteByDeptIdIn(LString);

       /* String[] arr;
            try {
                //判断传入的参数，有任何异常都确定为参数有误
                ids= ids.replaceAll("\"","" );
                String[] idsArray =ids.split(":")[1].split(",");
                 arr=new String[idsArray.length-1];
                System.arraycopy(idsArray, 0, arr, 0, arr.length);
            }catch (Exception e){
                throw new ParameterInvalidException("传入参数有误");
            }

        List<String> LString = new ArrayList<>();
        for(String str : arr){
            LString.add(new String(str));
        }
            deptService.deleteByDeptIdIn(LString);
*/
        return new ResultVO<>(SUCCESS);
    }



    @RequestMapping("update")
    public ResultVO<Void> update(@RequestBody Dept dept){
        if (dept.getName()==null) {
            throw new ParameterInvalidException("传入的参数有误");
        }

        deptService.update(dept.getDeptId() , dept.getName(), dept.getDeptDescribe());
        return new ResultVO<>(SUCCESS);
    }


}

