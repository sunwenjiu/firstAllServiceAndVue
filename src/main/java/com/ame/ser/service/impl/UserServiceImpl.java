package com.ame.ser.service.impl;

import com.ame.ser.service.ex.ParameterInvalidException;
import com.ame.ser.dto.EditPasswordDTO;
import com.ame.ser.enums.ResultEnum;
import com.ame.ser.enums.UserStatusEnum;
import com.ame.ser.model.*;
import com.ame.ser.repository.RolePermissionRepository;
import com.ame.ser.repository.RoleRepository;
import com.ame.ser.repository.UserRepository;
import com.ame.ser.repository.UserRoleRepository;
import com.ame.ser.service.UserService;
import com.ame.ser.service.ex.TableFieldContentDuplicateException;
import com.ame.ser.utils.Md5Util;
import com.ame.ser.utils.PrimaryKeyUtil;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
            List<String> roleList = new ArrayList<>();
            UserRoleVO userRoleVO = new UserRoleVO();
            BeanUtils.copyProperties(user, userRoleVO);

            for (UserRole userRole : userRoleList) {
                Role role = userRole.getRole();
                roleList.add(role.getRoleName());

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
        if (user.getUserId() == null || user.getUserId().length() == 0) {
            long count = userRepository.countByUserName(user.getUserName());
            if (count > 0) {
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "用户名已存在");
            }
            if(user.getUserPassword().isEmpty()){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "密码不能为空");
            }
            String md5Pwd = Md5Util.pwdByMd5(user.getUserPassword(),user.getUserName(),2);
            user.setUserPassword(md5Pwd);
            user.setUserId(PrimaryKeyUtil.getPrimaryId());
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
        if(user != null){
            userRoleRepository.deleteByUser(user);
            userRepository.deleteById(userId);
            return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "删除用户成功");
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除用户失败");
//        List<UserRole> userRoleList = userRoleRepository.findByUser(user);
//        if (userRoleList.size() > 0) {
//            userRoleRepository.deleteByUser(user);
//        }
//        userRepository.deleteById(userId);
//        User obj = userRepository.getOne(userId);
//        if (obj == null) {
//            return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "删除用户成功");
//        } else {
//            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "删除用户失败");
//        }
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


// =================================================================================================================
    /*SWJ 开始*/
    /**
     * 创建用户 SWJ
     * @param user
     * @throws TableFieldContentDuplicateException
     */
    @Override
    public void createUser(User user) throws TableFieldContentDuplicateException {
        User result =userRepository.findByUserName(user.getUserName());
        if (result == null){
            //用户不存在，可以创建,user包含用户名，性别，电话，
            String id = PrimaryKeyUtil.getPrimaryId();
            user.setUserId(id);
            //封装角色
            user.setUserRealName("申报人");
            //封装默认密码
            String defaultPassword ="666666";
            defaultPassword=Md5Util.pwdByMd5(defaultPassword, user.getUserName(), 2);
            user.setUserPassword(defaultPassword);

            //封装状态,默认为已开启
            user.setUserStatus(UserStatusEnum.NO_LOCK);



            //封装头像
            user.setUserPhoto("");

            // 封装创建时间和修改时间
            Date newTime = new Date();
            user.setCreateTime(newTime);
            user.setUpdateTime(newTime);

            userRepository.saveAndFlush(user);

        }else {
            //用户已经存在
            throw new TableFieldContentDuplicateException("用户已经存在，请更换用户名");
        }

    }
    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageVO<List<UserVO> > findAllPage(int pageNo, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> users = userRepository.findAll(pageable);
        Long totalElements = users.getTotalElements();
        int totalPages =  users.getTotalPages();
        //数据转换，去除密码等
        List<UserVO> listUserVO = new ArrayList<>();
        List<User> listUser=users.getContent();
        for (User u:listUser) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(u,userVO );
            listUserVO.add(userVO);
        }


        PageVO<List<UserVO> > listPageVO = new PageVO<>(listUserVO, totalElements, totalPages);

        return listPageVO;
    }


    /**
     * 通过id单个删除用户（多个删除可实现单个删除，但因前段已设计好，所以暂时保留）
     * @param userId
     */
    @Override
    public void delUserById(String userId) {
        try {
            userRepository.deleteById(userId);
        }catch (EmptyResultDataAccessException e){
            throw new ParameterInvalidException("传入的参数有误，数据不存在");
        }
    }

    /**
     * 通过id 多个或单个删除用户
     * @param ids
     */
    @Transactional
    @Override
    public void deleteByUserIdIn(List<String > ids) {
        try {
            userRepository.deleteByUserIdIn(ids);
        }catch (Exception e){
            throw new ParameterInvalidException("传入的参数有误");
        }
    }

    /**
     * 修改用户数据
     * @param user
     */
    @Override
    public void update(User user) {
        Optional<User> byId = userRepository.findById(user.getUserId());
        if (!byId.isPresent()) {
            throw new ParameterInvalidException("传入的参数有误,没有查询到用户");
        }
        User userResult = byId.get();

        userResult.setUserSex(user.getUserSex());
        userResult.setUserTel(user.getUserTel());
        userRepository.saveAndFlush(userResult);


    }
}
