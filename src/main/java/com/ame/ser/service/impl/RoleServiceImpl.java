package com.ame.ser.service.impl;

import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.Role;
import com.ame.ser.model.RolePermission;
import com.ame.ser.model.User;
import com.ame.ser.model.UserRole;
import com.ame.ser.repository.RolePermissionRepository;
import com.ame.ser.repository.RoleRepository;
import com.ame.ser.repository.UserRepository;
import com.ame.ser.repository.UserRoleRepository;
import com.ame.ser.service.RoleService;
import com.ame.ser.utils.PrimaryKeyUtil;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:15
 *
 * @author: ycbx
 */
@Service
public class RoleServiceImpl implements RoleService {

    /**
     * The Role repository.
     */
    @Autowired
    private RoleRepository roleRepository;

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

    @Autowired
    private UserRepository userRepository;

    /**
     * Find one role.
     *
     * @param roleId the role id
     * @return the role
     */
    @Override
    public Role findOne(String roleId) {
        return roleRepository.getOne(roleId);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    /**
     * Save role.
     *
     * @param role the role
     * @return the role
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO save(Role role) {
        if (role.getRoleId() == null || role.getRoleId().length() == 0) {
            long count = roleRepository.countByRoleCode(role.getRoleCode());
            if (count > 0) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "角色编码已存在");
            }
            count = roleRepository.countByRoleName(role.getRoleName());
            if (count > 0) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "角色名称已存在");
            }
            role.setRoleId(PrimaryKeyUtil.getPrimaryId());
            roleRepository.save(role);
            return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "角色新增成功");
        }
        roleRepository.save(role);
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "角色修改成功");
    }

    /**
     * Delete.
     *
     * @param roleId the role id
     * @return the result vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO delete(String roleId) {
        Role role = roleRepository.getOne(roleId);
        userRoleRepository.deleteByRole(role);
        rolePermissionRepository.deleteByRole(role);
        roleRepository.delete(role);
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "删除角色成功");
    }

    /**
     * 查询所有角色，不分页（给用户分配角色时用）
     *
     * @return the result vo
     */
    @Override
    public ResultVO listAll() {
        List<Role> roles = roleRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Role role : roles) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", role.getRoleId());
            map.put("label", MessageFormat.format("{0}[{1}]", role.getRoleName(), role.getRoleCode()));
            result.add(map);
        }
        return ResultVOUtil.success(result);
    }

    /**
     * List by user list response.
     *
     * @param userId the user id
     * @return the list response
     */
    @Override
    public ResultVO listByUser(String userId) {
        User user = new User();
        user.setUserId(userId);
        List<Role> roles = userRoleRepository.findUserRolesByUser(user);
        List<String> result = new ArrayList<>();
        for (Role role : roles) {
            result.add(role.getRoleId());
        }
        return ResultVOUtil.success(result);
    }

    /**
     * 用户分配角色
     * @param userId  用户id
     * @param roleId  角色id
     * @param direction right 添加  -  left 移除
     * @Author LSQ
     * @return
     */
    @Override
    public ResultVO userRoleSaveOrDelete(String userId, String roleId, String direction) {
        // 判断角色Id是否有多个
        Boolean roleIdFlag = roleId.contains(",");
        if(!direction.isEmpty() && "right".equals(direction)){
            // 添加
            List<UserRole> userRoleList = new ArrayList<UserRole>();
            if(roleIdFlag){
                // 1.批量添加
                String[] roleArr = roleId.split(",");
                for(String roIdItem : roleArr){
                    UserRole userRole = new UserRole();
                    userRole.setUserRoleId(PrimaryKeyUtil.getPrimaryId());
                    User user = new User();
                    user.setUserId(userId);
                    Role role = new Role();
                    role.setRoleId(roIdItem);
                    userRole.setUser(user);
                    userRole.setRole(role);
                    userRoleList.add(userRole);
                }
            }else {
                // 2. 单个添加
                UserRole userRole = new UserRole();
                userRole.setUserRoleId(PrimaryKeyUtil.getPrimaryId());
                User user = new User();
                user.setUserId(userId);
                Role role = new Role();
                role.setRoleId(roleId);
                userRole.setUser(user);
                userRole.setRole(role);
                userRoleList.add(userRole);
            }
            List<UserRole> result =  userRoleRepository.saveAll(userRoleList);
            if(result != null){
                return ResultVOUtil.success();
            }
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMessage());
        }else if(!direction.isEmpty() && "left".equals(direction)){
            // 移除
            String[] roleArr = new String[10];
            if(roleIdFlag){
                roleArr = roleId.split(",");
            }else {
                roleArr = new String[]{roleId};
            }
            int resultFlag = userRoleRepository.removeUserRole(userId,roleArr);
            if (resultFlag > 0){
                return ResultVOUtil.success();
            }
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMessage());
        }
        return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
    }
}
