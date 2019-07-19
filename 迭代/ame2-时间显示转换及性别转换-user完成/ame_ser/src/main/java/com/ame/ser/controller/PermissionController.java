package com.ame.ser.controller;

import com.ame.ser.dto.PermissionChangeDTO;
import com.ame.ser.model.Permission;
import com.ame.ser.service.PermissionService;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Date: 2018-05-13
 * Time: 23:12
 *
 * @author: ycbx
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    /**
     * The Permission service.
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * 获取当前用户的权限列表.
     *
     * @return the result vo
     */
    @GetMapping("/findByCurrentUser")
    public ResultVO istCodeByUser() {
        return permissionService.listCodeByUser();
    }


    /**
     * 保存或编辑
     *
     * @param permission the permission
     * @return the base response
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }


    /**
     * 删除
     *
     * @param ids the ids
     * @return the base response
     */
    @PostMapping("/delete/{ids}")
    public ResultVO delete(@PathVariable("ids") String ids) {
        return permissionService.delete(ids);
    }

    /**
     * 通过菜单的ID查找权限
     *
     * @param resourceId the resource id
     * @return the list response
     */
    @GetMapping("/resource/{resourceId}")
    public ResultVO listByResource(@PathVariable("resourceId") String resourceId) {
        return permissionService.listByResource(resourceId);
    }

    /**
     * 前端给角色分配权限时，选择菜单后加载对应权限
     *
     * @param roleId the role id
     * @param resId  the resource id
     * @return the result vo
     */
    @GetMapping("/list/role/resource/{roleId}/{resId}")
    public ResultVO listByRoleAndResource(@PathVariable("roleId") String roleId,
                                                        @PathVariable("resId") String resId) {
        return permissionService.listByRoleAndResource(roleId, resId);
    }

    /**
     * 给角色分配权限
     *
     * @param request the request
     * @return the result vo
     */
    @PostMapping("/change/role")
    public ResultVO changeByRole(@RequestBody PermissionChangeDTO request) {
        return permissionService.changeByRole(request);
    }
}
