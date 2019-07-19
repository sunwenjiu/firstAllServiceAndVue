package com.ame.ser.controller;

import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.UserInfo;
import com.ame.ser.service.UserInfoService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Date: 2018-05-26
 * Time: 9:51
 *
 * @author: ycbx
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    /**
     * The User info service.
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查找用户通知
     *
     * @param userId the name
     * @return the result VO
     */
    @GetMapping("/findNotify")
    public ResultVO findNotify(@RequestParam("userId") String userId) {
        return userInfoService.findNotify(userId);
    }

    /**
     * 查找用户便签
     *
     * @param userId the name
     * @return the result VO
     */
    @GetMapping("/findNote")
    public ResultVO findNote(@RequestParam("userId") String userId) {
        return userInfoService.findNote(userId);
    }

    /**
     * 添加或编辑
     *
     * @param userInfo the user
     * @return the base response
     */
    @PostMapping("/save")
    public ResultVO save(@RequestBody UserInfo userInfo) {
        UserInfo obj = userInfoService.save(userInfo);
        if (obj == null) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "保存失败");
        }
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "保存成功");
    }

    /**
     * 删除
     *
     * @param infoId the info id
     * @return the result vo
     */
    @GetMapping("/delete")
    public ResultVO delete(@RequestParam("infoId") String infoId) {
        return userInfoService.delete(infoId);
    }

    /**
     * 统计未读通知数量
     *
     * @param userId the info id
     * @return the result vo
     */
    @GetMapping("/noReadMesCount")
    public ResultVO messageCount(@RequestParam("userId") String userId) {
        return userInfoService.noReadMesCount(userId);
    }

}
