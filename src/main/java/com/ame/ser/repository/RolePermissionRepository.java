package com.ame.ser.repository;

import com.ame.ser.model.Permission;
import com.ame.ser.model.Role;
import com.ame.ser.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 12:02
 *
 * @author: ycbx
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    /**
     * Find by role list.
     *
     * @param role the role
     * @return the list
     */
    List<RolePermission> findByRole(Role role);

    /**
     * Find by permission list.
     *
     * @param permission the permission
     * @return the list
     */
    List<RolePermission> findByPermission(Permission permission);

    /**
     * Delete by role.
     *
     * @param role the role
     */
    void deleteByRole(Role role);

    /**
     * Delete by permission.
     *
     * @param permission the permission
     */
    void deleteByPermission(Permission permission);

    /**
     * Find role permissions by role list.
     *
     * @param roles the roles
     * @return the list
     */
    @Query("SELECT rp.permission FROM RolePermission rp WHERE rp.role IN (:roles)")
    List<Permission> findRolePermissionsByRole(@Param("roles") List<Role> roles);

    /**
     * Delete by role and permission.
     *
     * @param role       the role
     * @param permission the permission
     */
    void deleteByRoleAndPermission(Role role, Permission permission);
}
