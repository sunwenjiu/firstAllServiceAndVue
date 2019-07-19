package com.ame.ser.repository;

import com.ame.ser.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:56
 *
 * @author: ycbx
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * Delete by role id in.
     *
     * @param ids the ids
     */
    void deleteByRoleIdIn(String[] ids);

    /**
     * Count by role code long.
     *
     * @param code the code
     * @return long long
     */
    long countByRoleCode(String code);

    /**
     * Count by role name long.
     *
     * @param name the name
     * @return long long
     */
    long countByRoleName(String name);

    /**
     * Find by role id role.
     *
     * @param roleId the role id
     * @return the role
     */
    Role findByRoleId(Long roleId);


}
