package com.ame.ser.service.impl;

import com.ame.ser.enums.NotifyStatusEnum;
import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.UserInfo;
import com.ame.ser.repository.UserInfoRepository;
import com.ame.ser.service.UserInfoService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-26
 * Time: 0:26
 *
 * @author: ycbx
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * Find one user info.
     *
     * @param infoId the info id
     * @return the user info
     */
    @Override
    public UserInfo findOne(String infoId) {
        return userInfoRepository.getOne(infoId);
    }

    /**
     * 查找登录时间
     * 登录时有调用，不能改成ResultVO
     *
     * @param userId the user id
     * @return the result vo
     */
    @Override
    public UserInfo findLoginTime(String userId) {
        return userInfoRepository.findByUserIdAndLoginTimeIsNotNull(userId);
    }

    /**
     * 通过UserId查找通知
     *
     * @param userId the user id
     * @return the result vo
     */
    @Override
    public ResultVO findNotify(String userId) {
        List<UserInfo> userInfoList = userInfoRepository.findByUserIdAndNotifyIsNotNull(userId);
        if (userInfoList.size() < 1) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "无通知");
        }
        return ResultVOUtil.success(userInfoList);
    }

    /**
     * 通过UserId查找便签
     *
     * @param userId the user id
     * @return the result vo
     */
    @Override
    public ResultVO findNote(String userId) {
        List<UserInfo> userInfoList = userInfoRepository.findByUserIdAndNoteIsNotNull(userId);
        if (userInfoList.size() < 1) {
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(), "无便签");
        }
        return ResultVOUtil.success(userInfoList);
    }

    /**
     * 保存
     * 登录时有调用，不能改成ResultVO
     *
     * @param userInfo the user info
     * @return the result vo
     */
    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    /**
     * 通过ID删除
     *
     * @param infoId the info id
     * @return the result vo
     */
    @Override
    public ResultVO delete(String infoId) {
        userInfoRepository.deleteById(infoId);
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "删除成功");
    }

    /**
     * 通过用户ID删除
     *
     * @param userId the user id
     * @return the result vo
     */
    @Override
    public ResultVO deleteByUserId(String userId) {
        userInfoRepository.deleteByUserId(userId);
        return ResultVOUtil.success(ResultEnum.SUCCESS.getCode(), "删除成功");
    }

    /**
     * 按用户统计未读通知数量
     *
     * @param userId the user id
     * @return the result vo
     */
    @Override
    public ResultVO noReadMesCount(String userId) {
        Integer count = userInfoRepository.countByUserIdAndNotifyStatus(userId, NotifyStatusEnum.NO_READ);
        return ResultVOUtil.success(count);
    }
}
