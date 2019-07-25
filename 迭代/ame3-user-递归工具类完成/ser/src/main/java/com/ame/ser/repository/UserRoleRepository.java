package com.ame.ser.repository;

import com.ame.ser.model.Role;
import com.ame.ser.model.User;
import com.ame.ser.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:49
 *
 * @author: ycbx
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    /**
     * 获取关联的用户角色对象
     *
     * @param user :用户
     * @return 用户角色对象列表 list
     */
    List<UserRole> findByUser(User user);


    /**
     * Find by role list.
     *
     * @param role the role
     * @return the list
     */
    List<UserRole> findByRole(Role role);

    /**
     * 根据当前用户查询对应的角色列表
     *
     * @param user the user
     * @return list list
     */
    @Query("SELECT ur.role FROM UserRole ur WHERE ur.user = :user")
    List<Role> findUserRolesByUser(@Param("user") User user);

    /**
     * Find by role list.
     *
     * @param roles the roles
     * @return list list
     */
    @Query("FROM UserRole ur WHERE ur.role IN (:roles)")
    List<UserRole> findByRole(@Param("roles") List<Role> roles);

    /**
     * 通过用户删除
     *
     * @param user the user
     */
    void deleteByUser(User user);

    /**
     * 通过角色删除
     *
     * @param role the user
     */
    void deleteByRole(Role role);
}
