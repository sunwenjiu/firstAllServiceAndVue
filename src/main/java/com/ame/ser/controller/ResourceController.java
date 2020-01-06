package com.ame.ser.controller;

import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.Resource;
import com.ame.ser.service.ResourceService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import com.ame.ser.vo.TreeVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 菜单
 * Date: 2018-05-05
 * Time: 21:52
 *
 * @author: ycbx
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    /**
     * The Resource service.
     */
    @Autowired
    private ResourceService resourceService;


    /**
     * 查找所有资源（菜单）
     *
     * @return the result vo
     */
//    @RequiresPermissions("user:view")
    @GetMapping("/findAll")
    public ResultVO pageList() {
        List<Resource> resourcePage = resourceService.findAll();

        if (resourcePage != null) {
            return ResultVOUtil.success(resourcePage);
        }
        return ResultVOUtil.error(ResultEnum.DATA_ERROR.getCode(), ResultEnum.DATA_ERROR.getMessage());
    }


    /**
     * 通过当前用户查找资源.
     *
     * @return the result vo
     */
    @GetMapping("/findByCurrentUser")
    public ResultVO findByCurrentUser() {

        return resourceService.finByCurrentUser();
    }

    /**
     * 查找单个
     *
     * @param id the id
     * @return the model response
     */
    @GetMapping("/findOne/{id}")
    public ResultVO findOne(@PathVariable("id") String id) {
        Resource resource = resourceService.findOne(id);
        if (resource == null) {
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        }
        return ResultVOUtil.success(resource);
    }


    /**
     * 保存及修改
     *
     * @param resource the resource
     * @return the base response
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody Resource resource) {
        return resourceService.save(resource);
    }


    /**
     * 删除
     *
     * @param ids the ids
     * @return the base response
     */
    @PostMapping("/delete/{ids}")
    public ResultVO remove(@PathVariable("ids") String ids) {
        return resourceService.delete(ids);
    }


    /**
     * 查询根节点(父ID为空的)
     *
     * @return the list response
     */
    @GetMapping("/findRoot")
    public ResultVO listRoot() {
        return resourceService.listRoot();
    }


    /**
     * 查询该ID下的所有节点
     *
     * @param parentId the parent id
     * @return the resource tree response
     */
    @GetMapping("/tree/{parentId}")
    public ResultVO listByTree(@PathVariable("parentId") String parentId) {
        return resourceService.findTree(parentId);
    }


    /**
     * 获取资源树
     * @return
     */
    @RequestMapping("/getResourceTree")
    public ResultVO getResourceTree(){
        List<TreeVO> resultList = resourceService.findSourceTree();
        if(resultList != null && resultList.size() > 0){
            return ResultVOUtil.success(resultList);
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMessage());
    }

}
