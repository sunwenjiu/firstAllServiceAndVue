package com.ame.ser.service.impl;

import com.ame.ser.dto.EditPasswordDTO;
import com.ame.ser.enums.ResultEnum;
import com.ame.ser.enums.UserStatusEnum;
import com.ame.ser.model.*;
import com.ame.ser.repository.RolePermissionRepository;
import com.ame.ser.repository.RoleRepository;
import com.ame.ser.repository.UserRepository;
import com.ame.ser.repository.UserRoleRepository;
import com.ame.ser.service.UserService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import com.ame.ser.vo.RoleVO;
import com.ame.ser.vo.UserRoleVO;
import com.ame.ser.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:09
 *
 * @author: ycbx
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * The User repository.
     */
    @Resource
    private UserRepository userRepository;

    /**
     * The Role repository.
     */
    @Resource
    private RoleRepository roleRepository;

    /**
     * The User role repository.
     */
    @Resource
    private UserRoleRepository userRoleRepository;

    /**
     * The Role permission repository.
     */
    @Resource
    private RolePermissionRepository rolePermissionRepository;


    /**
     * Find one user.
     *
     * @param userId the user id
     * @return the user
     */
    @Override
    public User findOne(String userId) {
        return userRepository.getOne(userId);
    }

    /**
     * Find by user name result vo.
     *
     * @param name the name
     * @return the result vo
     */
    @Override
    public ResultVO findByUserName(String name) {
        User user = userRepository.findByUserName(name);
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        }
        if (user.getUserStatus() == UserStatusEnum.IS_LOCK) {
            return ResultVOUtil.error(ResultEnum.IS_LOCK.getCode(), ResultEnum.IS_LOCK.getMessage());
        }

        UserRoleVO userRoleVO = new UserRoleVO();
        BeanUtils.copyProperties(user, userRoleVO);

        //返回角色
        List<UserRole> userRoleList = userRoleRepository.findByUser(user);
        List<RoleVO> roleVOList = new ArrayList<>();
        for (UserRole obj : userRoleList) {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(obj.getRole(), roleVO);

            //返回权限
            Role role = new Role();
            BeanUtils.copyProperties(roleVO, role);
            List<RolePermission> rolePermissionList = rolePermissionRepository.findByRole(role);
            List<Permission> permissionList = new ArrayList<>();
            for (RolePermission rolePermission : rolePermissionList) {
                permissionList.add(rolePermission.getPermission());
            }
            roleVO.setPermissionList(permissionList);

            roleVOList.add(roleVO);
        }
        userRoleVO.setRoleList(roleVOList);

        return ResultVOUtil.success(userRoleVO);
    }


    /**
     * Find by name user.
     *
     * @param name the name
     * @return the user
     */
    @Override
    public User findByName(String name) {
        return userRepository.findByUserName(name);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<UserVO> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }

        return userVOList;
    }

    /**
     * Find all result vo.
     *
     * @param pno  the pno
     * @param size the size
     * @return the result vo
     */
    @Override
    public ResultVO findAll(Integer pno, Integer size) {
        PageRequest pageable = new PageRequest(pno, size);
        Page<User> userPage = userRepository.findAll(pageable);
        if (userPage.getTotalElements() <= 0) {
            return ResultVOUtil.error(ResultEnum.DATA_ERROR.getCode(), ResultEnum.DATA_ERROR.getMessage());
        }
        List<UserRoleVO> userRoleVOList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        //返回角色
        for (User user : userPage.getContent()) {
            List<UserRole> userRoleList = userRoleRepository.findByUser(user);
            List<Role> roleList = new ArrayList<>();
            UserRoleVO userRoleVO = new UserRoleVO();
            BeanUtils.copyProperties(user, userRoleVO);

            for (UserRole userRole : userRoleList) {
                Role role = userRole.getRole();
                roleList.add(role);

                userRoleVO.setRoleList(roleList);
            }
            userRoleVOList.add(userRoleVO);

        }
        //分页信息
        map.put("totalPages", userPage.getTotalPages());
        map.put("totalElements", userPage.getTotalElements());
        map.put("last", userPage.isLast());
        map.put("size", userPage.getSize());
        map.put("number", userPage.getNumber());
        map.put("sort", userPage.getSort());
        map.put("first", userPage.isFirst());
        map.put("numberOfElements", userPage.getNumberOfElements());
        map.put("content", userRoleVOList);

        return ResultVOUtil.success(map);
    }

    /**
     * Save
     *
     * @param user the user
     * @return the result vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO save(User user) {
        // 如果传过来的id为空。那证明就是新增操作，需要获得ID，生成密文密码再更新，否则就是修改操作
        if (user.getUserId() == null) {
            long count = userRepository.countByUserName(user.getUserName());
            if (count > 0) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户名已存在");
            }
            Md5Hash md5Hash = new Md5Hash(new Md5Hash(user.getUserPassword(), user.getUserName()));
            user.setUserPassword(String.valueOf(md5Hash));
            userRepository.save(user);
            return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "用户添加成功");
        }
        User user1 = userRepository.getOne(user.getUserId());
        if (!StringUtils.equals(user1.getUserName(), user.getUserName())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "不能修改登录名");
        }
        user.setUserPassword(user1.getUserPassword());
        userRepository.save(user);
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "用户修改成功");
    }

    /**
     * Delete
     *
     * @param userId the ids
     * @return the result vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO delete(String userId) {
        User user = userRepository.getOne(userId);
        List<UserRole> userRoleList = userRoleRepository.findByUser(user);
        if (userRoleList.size() > 0) {
            userRoleRepository.deleteByUser(user);
        }
        userRepository.deleteById(userId);
        User obj = userRepository.getOne(userId);
        if (obj == null) {
            return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "删除用户成功");
        } else {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除用户失败");
        }
    }

    /**
     * 给用户分配权限
     *
     * @param userId    the user id
     * @param direction the direction
     * @param roleIds   the role ids
     * @return the result vo
     */
    @Override
    public ResultVO userAuthorized(String userId, String direction, String roleIds) {
        User user = new User();
        user.setUserId(userId);
        // 如果是右移，就是添加
        if (StringUtils.equals("right", direction)) {
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId : roleIds.split(",")) {
                Role role = new Role();
                role.setRoleId(roleId);
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(role);
                userRoles.add(userRole);
            }
            userRoleRepository.saveAll(userRoles);
        } else {
            // 否则就是先移除
            List<Role> roles = new ArrayList<>();
            for (String roleId : roleIds.split(",")) {
                Role role = new Role();
                role.setRoleId(roleId);
                roles.add(role);
            }
            List<UserRole> userRoles = userRoleRepository.findByRole(roles);
            userRoleRepository.deleteAll(userRoles);
        }
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "用户角色分配成功");
    }

    /**
     * 修改密码
     *
     * @param editPasswordDTO the edit password dto
     * @return the result vo
     */
    @Override
    public ResultVO editPassword(EditPasswordDTO editPasswordDTO) {
        User user = userRepository.getOne(editPasswordDTO.getUserId());
        Md5Hash md5Hash = new Md5Hash(new Md5Hash(editPasswordDTO.getOldPass(), editPasswordDTO.getUserName()));
        if (!StringUtils.equals(String.valueOf(md5Hash), user.getUserPassword())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "原密码错误，请重试！");
        }
        Md5Hash pwd = new Md5Hash(new Md5Hash(editPasswordDTO.getNewPass(), editPasswordDTO.getUserName()));
        user.setUserPassword(String.valueOf(pwd));
        User obj = userRepository.save(user);
        if (obj == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "密码修改失败，请重试！");
        } else {
            return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "密码修改成功！");
        }
    }

    /**
     * 验证密码，用于锁定界面解锁
     *
     * @param user the user
     * @return the result vo
     */
    @Override
    public ResultVO validatePass(User user) {
        User obj = userRepository.getOne(user.getUserId());
        Md5Hash md5Hash = new Md5Hash(new Md5Hash(user.getUserPassword(), user.getUserName()));
        if (!StringUtils.equals(String.valueOf(md5Hash), obj.getUserPassword())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "密码错误，请重试！");
        }
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "解锁成功！");
    }

    /**
     * 检查当前用户是否已过期
     *
     * @return the result vo
     */
    @Override
    public ResultVO currentUser() {

        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        String userName = (String) subject.getPrincipal();
        if (userName == null) {
            return ResultVOUtil.error(ResultEnum.OVER_TIME.getCode(), ResultEnum.OVER_TIME.getMessage());
        }
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
    }
}
