package com.ame.ser.service.impl;

import com.ame.ser.dto.PermissionChangeDTO;
import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.*;
import com.ame.ser.repository.PermissionRepository;
import com.ame.ser.repository.RolePermissionRepository;
import com.ame.ser.repository.UserRepository;
import com.ame.ser.repository.UserRoleRepository;
import com.ame.ser.service.PermissionService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:23
 *
 * @author: ycbx
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    /**
     * The Permission repository.
     */
    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * The User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The User role repository.
     */
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * The Role permission repository.
     */
    @Autowired
    private RolePermissionRepository rolePermissionRepository;


    /**
     * Find one permission.
     *
     * @param perId the per id
     * @return the permission
     */
    @Override
    public Permission findOne(String perId) {
        return null;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<Permission> findAll() {
        return null;
    }

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<Permission> findAll(Pageable pageable) {
        return null;
    }


    /**
     * 保存或编辑
     *
     * @param permission the permission
     * @return the result vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO save(Permission permission) {

        if (permission.getPerId() == null) {
            long count = permissionRepository.countByPerCode(permission.getPerCode());
            if (count > 0) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "权限编码已存在");
            }
            permissionRepository.save(permission);
            return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "权限新增成功");
        }
        permissionRepository.save(permission);
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "权限修改成功");
    }

    /**
     * 删除
     *
     * @param ids the ids
     * @return the result vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO delete(String ids) {
        String[] idArr = StringUtils.split(ids, ",");
        for (String id : idArr) {
            Permission permission = permissionRepository.getOne(id);
            rolePermissionRepository.deleteByPermission(permission);
            permissionRepository.delete(permission);
        }
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "删除成功！");
    }

    /**
     * 通过菜单的ID查找权限
     *
     * @param resourceId the resource id
     * @return result vo
     */
    @Override
    public ResultVO listByResource(String resourceId) {
        Resource resource = new Resource();
        resource.setResId(resourceId);
        return ResultVOUtil.success(permissionRepository.findByResource(resource));
    }


    /**
     * 获取当前用户的权限列表.
     *
     * @return the result vo
     */
    @Override
    public ResultVO listCodeByUser() {

        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        String userName = (String) subject.getPrincipal();
        User user = userRepository.findByUserName(userName);

        // 查询权限
        List<Permission> list = null;
        // 如果是admin用户，返回所有权限
        if (StringUtils.equals(user.getUserName(), "admin")) {
            list = permissionRepository.findAll();
        } else {
            // 查询当前用户的所有角色
            List<Role> roles = userRoleRepository.findUserRolesByUser(user);
            list = rolePermissionRepository.findRolePermissionsByRole(roles);
        }
        Set<Permission> permissions = new HashSet<>(list);
        List<String> result = new ArrayList<>();
        for (Permission permission : permissions) {
            result.add(permission.getPerCode());
        }

        return ResultVOUtil.success(result);
    }

    /**
     * 前端给角色分配权限时，选择菜单后加载对应权限
     *
     * @param roleId the role id
     * @param resId  the res id
     * @return the result vo
     */
    @Override
    public ResultVO listByRoleAndResource(String roleId, String resId) {
        Role role = new Role();
        role.setRoleId(roleId);
        Resource resource = new Resource();
        resource.setResId(resId);

        List<Permission> rolePermissions = rolePermissionRepository
                .findRolePermissionsByRole(Collections.singletonList(role));
        List<Permission> resourcePermissions = permissionRepository.findByResource(resource);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Permission permission : resourcePermissions) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", permission.getPerId());
            map.put("code", permission.getPerCode());
            map.put("name", permission.getPerName());
            if (rolePermissions.contains(permission)) {
                map.put("status", true);
            } else {
                map.put("status", false);
            }
            result.add(map);
        }
        return ResultVOUtil.success(result);
    }

    /**
     * 给角色分配权限
     *
     * @param request the request
     * @return the result vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO changeByRole(PermissionChangeDTO request) {
        if (request.getStatus()) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(request.getPermission());
            rolePermission.setRole(request.getRole());
            rolePermissionRepository.save(rolePermission);
            return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "添加权限成功");
        }
        rolePermissionRepository.deleteByRoleAndPermission(request.getRole(), request.getPermission());
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "删除权限成功");
    }
}
