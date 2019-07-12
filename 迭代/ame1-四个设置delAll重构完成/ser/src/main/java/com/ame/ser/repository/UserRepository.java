package com.ame.ser.repository;


import com.ame.ser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
