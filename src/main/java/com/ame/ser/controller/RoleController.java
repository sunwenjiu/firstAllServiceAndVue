package com.ame.ser.controller;

import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.Role;
import com.ame.ser.service.RoleService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 角色
 * Date: 2018-05-05
 * Time: 21:29
 *
 * @author: ycbx
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    /**
     * The Role service.
     */
    @Autowired
    private RoleService roleService;

    /**
     * 分页查找所有角色
     *
     * @param pno  the pno
     * @param size the size
     * @return the result vo
     */
    @GetMapping("/findAll")
    public ResultVO pageList(@RequestParam(value = "pno", defaultValue = "0") Integer pno,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest request = new PageRequest(pno, size);
        Page<Role> rolePage = roleService.findAll(request);

        if (rolePage.getTotalElements() > 0) {
            return ResultVOUtil.success(rolePage);
        }
        return ResultVOUtil.error(ResultEnum.DATA_ERROR.getCode(), ResultEnum.DATA_ERROR.getMessage());
    }


    /**
     * 查询所有角色，不分页
     *
     * @return the result vo
     */
    @GetMapping("/list")
    public ResultVO listAll() {
        return roleService.listAll();
    }


    /**
     * 根据用户ID查找角色（只返回ID字串）
     *
     * @param userId the user id
     * @return the list response
     */
    @GetMapping("/user/{userId}")
    public ResultVO listByUser(@PathVariable("userId") String userId) {
        return roleService.listByUser(userId);
    }


    /**
     * 保存或编辑
     *
     * @param role the role
     * @return the base response
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody Role role) {
        return roleService.save(role);
    }


    /**
     * 删除
     *
     * @param roleId the ids
     * @return the base response
     */
    @GetMapping("/delete")
    public ResultVO delete(@RequestParam("roleId") String roleId) {
        return roleService.delete(roleId);
    }


    /**
     *  用户分配角色
     * @param userId 用户id
     * @param roleId 角色id
     * @param direction right 添加  -  left 移除
     * @return
     */
    @RequestMapping("/userRoleSaveOrDelete")
    public ResultVO userRoleSaveOrDelete(@RequestParam(value = "userId",required = false) String userId,
                                         @RequestParam(value = "roleId",required = false) String roleId,
                                         @RequestParam(value = "direction",required = false) String direction){
        if(userId.isEmpty() || roleId.isEmpty() || direction.isEmpty()){
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }
        return roleService.userRoleSaveOrDelete(userId,roleId,direction);
    }


}
