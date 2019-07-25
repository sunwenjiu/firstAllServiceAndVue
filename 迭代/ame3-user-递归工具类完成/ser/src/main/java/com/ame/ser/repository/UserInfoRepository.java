package com.ame.ser.repository;

import com.ame.ser.enums.NotifyStatusEnum;
import com.ame.ser.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 10:12
 *
 * @author: ycbx
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    /**
     * 通过UserId查找数据
     *
     * @param userId the user id
     * @return the user info
     */
    List<UserInfo> findByUserId(String userId);

    /**
     * 通过UserId查找通知
     *
     * @param userId the user id
     * @return the user info
     */
    List<UserInfo> findByUserIdAndNotifyIsNotNull(String userId);

    /**
     * 通过UserId查找便签
     *
     * @param userId the user id
     * @return the list
     */
    List<UserInfo> findByUserIdAndNoteIsNotNull(String userId);

    /**
     * 通过UserId查找登录时间
     *
     * @param userId the user id
     * @return the user info
     */
    UserInfo findByUserIdAndLoginTimeIsNotNull(String userId);


    /**
     * 通过用户名删除数据
     *
     * @param userId the user id
     */
    void deleteByUserId(String userId);

    /**
     * 按用户统计未读通知数量
     *
     * @param userId the user id
     * @param status the status
     * @return the long
     */
    Integer countByUserIdAndNotifyStatus(String userId, NotifyStatusEnum status);
}
