package com.ame.ser.controller;

import com.ame.ser.config.ShiroRealm;
import com.ame.ser.dto.LoginDTO;
import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.User;
import com.ame.ser.model.UserInfo;
import com.ame.ser.service.UserInfoService;
import com.ame.ser.service.UserService;
import com.ame.ser.utils.PrimaryKeyUtil;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.LoginVO;
import com.ame.ser.vo.ResultVO;
import com.ame.ser.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Description: 登录、退出、未登录
 * Date: 2018-05-04
 * Time: 0:50
 *
 * @author: ycbx
 */
@RestController
public class LoginController {

    /**
     * The constant logger.
     */
    static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    /**
     * The User service.
     */
    @Autowired
    private UserService userService;

    /**
     * The User info service.
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录方法
     *
     * @param login the login
     * @return the result vo
     */
    @PostMapping("/login")
    public ResultVO ajaxLogin(@RequestBody LoginDTO login) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(login.getUserName(), login.getUserPassword());
        ResultVO resultVO = new ResultVO();
        try {
            subject.login(token);

            LoginVO loginVO = new LoginVO();
            loginVO.setToken("" + subject.getSession().getId());
            User user = userService.findByName(login.getUserName());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            loginVO.setUser(userVO);
            System.out.println("Token：" + subject.getSession().getId());

            // 记录登录时间及登录次数
            UserInfo userInfo = userInfoService.findLoginTime(user.getUserId());
            if (userInfo == null) {
                UserInfo obj = new UserInfo();
                obj.setInfoId(PrimaryKeyUtil.getPrimaryId());
                obj.setLoginTime(new Date());
                obj.setLoginNumber(1);
                obj.setUserId(user.getUserId());
                userInfoService.save(obj);
                userVO.setLoginNumber(obj.getLoginNumber());
            } else {
                userVO.setLoginTime(userInfo.getLoginTime());
                userInfo.setLoginTime(new Date());
                userInfo.setLoginNumber(userInfo.getLoginNumber() + 1);
                userVO.setLoginNumber(userInfo.getLoginNumber());
                userInfo.setUserId(user.getUserId());
                userInfoService.save(userInfo);
            }

            resultVO.setCode(ResultEnum.SUCCESS.getCode());
            resultVO.setMsg("登录成功!");
            resultVO.setData(loginVO);

            logger.info("有用户登录：" + login.getUserName() + "，密码：" + login.getUserPassword() + "，状态：登录成功，时间：" + new Date());
        } catch (IncorrectCredentialsException e) {
            resultVO.setCode(ResultEnum.USER_PASSWORD_ERROR.getCode());
            resultVO.setMsg(ResultEnum.USER_PASSWORD_ERROR.getMessage());
            logger.info("有用户登录：" + login.getUserName() + "，密码：" + login.getUserPassword() + "，状态：密码错误，时间：" + new Date());
        } catch (LockedAccountException e) {
            resultVO.setCode(ResultEnum.IS_LOCK.getCode());
            resultVO.setMsg(ResultEnum.IS_LOCK.getMessage());
            logger.info("有用户登录：" + login.getUserName() + "，密码：" + login.getUserPassword() + "，状态：用户已被冻结，时间：" + new Date());
        } catch (AuthenticationException e) {
            resultVO.setCode(ResultEnum.USER_PASSWORD_ERROR.getCode());
            resultVO.setMsg(ResultEnum.USER_PASSWORD_ERROR.getMessage());
            logger.info("有用户登录：" + login.getUserName() + "，密码：" + login.getUserPassword() + "，状态：无此用户，时间：" + new Date());
        } catch (Exception e) {
            logger.info("未知登录错误，用户：" + subject.getPrincipal() + "，错误信息：" + e.getMessage() + "，时间：" + new Date());
            e.printStackTrace();
        }
        return resultVO;
    }

    /**
     * 退出方法
     *
     * @return the result vo
     */
    @GetMapping("/logout")
    public ResultVO logout() {

        Subject subject = SecurityUtils.getSubject();

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setMsg("亲爱的用户" + subject.getPrincipal() + "：您已成功退出，欢迎再次光临！");
        logger.info("有用户退出: " + subject.getPrincipal() + "，状态：退出成功，时间：" + new Date());

        subject.logout();

        return resultVO;
    }


    /**
     * 未登录时返回的状态信息，前后端不分离时应重定向到登录界面，此处只返回未登录状态信息由前端控制跳转页面
     *
     * @return the result vo
     */
    @GetMapping("/unauth")
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultVO unauthorizedHandler() {
        Subject subject = SecurityUtils.getSubject();
        System.out.println("Token----->" + subject.getSession().getId());
        logger.info("有未登录访问，时间：" + new Date());
        return ResultVOUtil.error(ResultEnum.UN_AUTH.getCode(), ResultEnum.UN_AUTH.getMessage());
    }

}
