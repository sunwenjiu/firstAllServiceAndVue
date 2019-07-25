package com.ame.ser.controller;

import com.ame.ser.service.ex.ParameterInvalidException;
import com.ame.ser.dto.EditPasswordDTO;
import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.User;
import com.ame.ser.service.UserService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.PageVO;
import com.ame.ser.vo.ResultVO;
import com.ame.ser.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 用户
 * Date: 2018-03-21
 * Time: 14:18
 *
 * @author: ycbx
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    /**
     * The Users service.
     */
    @Autowired
    private UserService userService;


    /**
     * 通过用户名查找一个用户
     *
     * @param name the name
     * @return the result VO
     */
    @RequiresPermissions("user:view")
    @GetMapping("/findUser")
    public User findUser(@RequestParam("name") String name) {
        return userService.findByName(name);
    }

    /**
     * 分页查找所有用户，不带权限信息
     *
     * @param pno  the pno
     * @param size the size
     * @return the result vo
     */
    @RequiresPermissions("user:view")
    @GetMapping("/findAll")
    public ResultVO pageList(@RequestParam(value = "pno", defaultValue = "0") Integer pno,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {

        return userService.findAll(pno, size);
    }


    /**
     * 添加或编辑
     *
     * @param user the user
     * @return the base response
     */
    @RequiresPermissions("user:add")
    @PostMapping("/save")
    public ResultVO save(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 用户自己修改资料
     *
     * @param user the user
     * @return the base response
     */
    @PostMapping("/edit")
    public ResultVO edit(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        if (!user.getUserName().equals(subject.getPrincipal())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "无权限");
        }
        return userService.save(user);
    }

    /**
     * 用户查看自己的资料
     *
     * @param name the name
     * @return the result VO
     */
    @GetMapping("/findMe")
    public ResultVO findMe(@RequestParam("name") String name) {
        Subject subject = SecurityUtils.getSubject();
        if (!name.equals(subject.getPrincipal())) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "无权限");
        }
        return ResultVOUtil.success(userService.findByName(name));
    }


    /**
     * 删除
     *
     * @param userId the ids
     * @return the base response
     */
    @RequiresPermissions("user:delete")
    @GetMapping("/delete")
    public ResultVO remove(@RequestParam("userId") String userId) {
        return userService.delete(userId);
    }

    /**
     * 给用户分配权限
     *
     * @param userId    the user id
     * @param direction the direction
     * @param roleIds   the role ids
     * @return the base response
     */
    @RequiresPermissions("user:update")
    @PostMapping("/authorized/user/{userId}/role/{roleIds}/{direction}")
    public ResultVO userAuth(@PathVariable("userId") String userId, @PathVariable("direction") String direction,
                                 @PathVariable("roleIds") String roleIds) {
        return userService.userAuthorized(userId, direction, roleIds);
    }

    /**
     * 修改密码
     *
     * @param editPasswordDTO the user
     * @return the base response
     */
    @PostMapping("/editPass")
    public ResultVO editPass(@RequestBody EditPasswordDTO editPasswordDTO) {
        return userService.editPassword(editPasswordDTO);
    }

    /**
     * 验证密码，用于锁定界面解锁
     *
     * @param user the user
     * @return the base response
     */
    @PostMapping("/validatePass")
    public ResultVO validatePass(@RequestBody User user) {
        return userService.validatePass(user);
    }

    /**
     * 检查当前用户是否已过期
     *
     * @return the model response
     */
    @GetMapping("/current")
    public ResultVO currentUser() {
        return userService.currentUser();
    }



    // =================================================================================================================
    /*SWJ 开始*/

    @RequestMapping("create")
    public ResultVO<Void> createUser(@RequestBody User user){
        boolean sexIsRight =user.getUserSex().toString() == "MAN"? true:user.getUserSex().toString() == "WOMAN"?true:false;

        System.out.println(user.toString());
        if (user.getUserName() == null || !sexIsRight){
            throw new ParameterInvalidException("传入的参数是非法的");
        }
        userService.createUser(user);

        return new ResultVO<>(SUCCESS);
    }

    @RequestMapping("show")
    public ResultVO<PageVO<List<UserVO> >> showAllUserOnPage(int pageNo, int pageSize){
        PageVO<List<UserVO> > pageVO= userService.findAllPage(pageNo, pageSize);

        return new ResultVO<>(SUCCESS, pageVO);
    }

    /**
     * 通过id单个删除用户（多个删除可实现单个删除，但因前段已设计好，所以暂时保留）
     *
     */
    @RequestMapping("/del/{id}")
    public ResultVO<Void> del(@PathVariable("id") String id ){
        userService.delUserById(id);

        return new ResultVO<>(SUCCESS);
    }

    /**
     * 通过id 多个或单个删除用户
     *
     */
    @RequestMapping("delAll")
    public ResultVO<Void> deleteByUserIdIn(  String ids){
        List<String> LString = regDelAllData(ids);
        userService.deleteByUserIdIn(LString);

        return new ResultVO<>(SUCCESS);
    }

    @RequestMapping("update")
    public ResultVO<Void> update(@RequestBody User user){
        //判断数据
        // 性别判断是不是MAN WOMAN
        boolean sexIsRight =user.getUserSex().toString() == "MAN"? true:user.getUserSex().toString() == "WOMAN"?true:false;

        if (user.getUserId().length() != 32  || !sexIsRight){
            throw new ParameterInvalidException("传入的参数有误");
        }

        userService.update(user);
        return new ResultVO<>(SUCCESS);
    }

}
