package com.ame.ser.config;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.ame.ser.enums.ResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 全局异常处理
 * Date: 2018-05-04
 * Time: 11:01
 *
 * @author: ycbx
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    /**
     * The constant logger.
     */
    static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        Subject subject = SecurityUtils.getSubject();
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<>();
        if (ex instanceof UnauthenticatedException) {
            attributes.put("code", ResultEnum.TOKEN_ERROR.getCode());
            attributes.put("msg", ResultEnum.TOKEN_ERROR.getMessage());
            logger.info("Token错误，用户：" + subject.getPrincipal() + "，错误信息：" + ResultEnum.TOKEN_ERROR.getMessage() + "，时间：" + new Date());
        } else if (ex instanceof UnauthorizedException) {
            attributes.put("code", ResultEnum.NO_AUTH.getCode());
            attributes.put("msg", ResultEnum.NO_AUTH.getMessage());
            logger.info("未授权访问，用户：" + subject.getPrincipal() + "，访问路径：" + o.toString() + "，状态：无权限访问，时间：" + new Date());
        } else {
            attributes.put("code", ResultEnum.EXCEPTION.getCode());
            attributes.put("msg", ex.getCause());
            logger.info("未知全局错误，用户：" + subject.getPrincipal() + "，错误信息：" + ex.getCause() + "，状态：" + httpServletResponse.getStatus() + "，时间：" + new Date());
        }

        view.setAttributesMap(attributes);
        mv.setView(view);
        return mv;
    }
}
