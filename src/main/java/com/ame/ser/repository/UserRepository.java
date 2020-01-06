package com.ame.ser.repository;


import com.ame.ser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Count by user name long.
     *
     * @param userName the user name
     * @return the long
     */
    long countByUserName(String userName);

    /**
     * Delete by user id in.
     *
     * @param ids the ids
     */
    void deleteByUserIdIn(String[] ids);

    /**
     * Find by user name user.
     *
     * @param userName the user name
     * @return the user
     */
    User findByUserName(String userName);

    /**
     * Find by user name and user password user.
     *
     * @param userName     the user name
     * @param userPassword the user password
     * @return user user
     */
    User findByUserNameAndUserPassword(String userName, Long userPassword);

    @Modifying
    @Query(value="delete from User e where e.id in (:ids) ")
    int deleteByUserIdIn(@Param("ids")List<String> ids);

    /**
     * find user whose userName like String S
     *
     * @param s
     * @return: java.util.List<com.ame.ser.model.User>
     * @createTime 2019/8/22 11:23
     */
    List<User> findByUserNameLike(String s);
}
