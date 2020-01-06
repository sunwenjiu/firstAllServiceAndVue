package com.ame.ser.service;

import com.ame.ser.model.UserInfo;
import com.ame.ser.vo.ResultVO;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:03
 *
 * @author: ycbx
 */
public interface UserInfoService {


    /**
     * Find one user info.
     *
     * @param infoId the info id
     * @return the user info
     */
    UserInfo findOne(String infoId);

    /**
     * Find by user id result vo.
     *
     * @param userId the user id
     * @return the result vo
     */
    UserInfo findLoginTime(String userId);

    /**
     * Find by user id result vo.
     *
     * @param userId the user id
     * @return the result vo
     */
    ResultVO findNotify(String userId);


    /**
     * Find note result vo.
     *
     * @param userId the user id
     * @return the result vo
     */
    ResultVO findNote(String userId);

    /**
     * Save result vo.
     *
     * @param userInfo the user info
     * @return the result vo
     */
    UserInfo save(UserInfo userInfo);

    /**
     * Delete result vo.
     *
     * @param infoId the info id
     * @return the result vo
     */
    ResultVO delete(String infoId);

    /**
     * Delete by user id result vo.
     *
     * @param userId the user id
     * @return the result vo
     */
    ResultVO deleteByUserId(String userId);

    /**
     * Message count result vo.
     *
     * @param userId the user id
     * @return the result vo
     */
    ResultVO noReadMesCount(String userId);


}
