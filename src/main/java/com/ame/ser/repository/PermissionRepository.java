package com.ame.ser.repository;

import com.ame.ser.model.Permission;
import com.ame.ser.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-13
 * Time: 14:59
 *
 * @author: ycbx
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

    /**
     * Delete by id in.
     *
     * @param split the split
     */
    void deleteByPerIdIn(String[] split);

    /**
     * Find by resource list.
     *
     * @param resource the resource
     * @return list
     */
    List<Permission> findByResource(Resource resource);

    /**
     * Count by code long.
     *
     * @param code the code
     * @return long
     */
    long countByPerCode(String code);

    /**
     * Delete by resource in.
     *
     * @param resources the resources
     */
    @Modifying
    @Query("DELETE FROM Permission WHERE resource IN (:resources)")
    void deleteByResourceIn(@Param("resources") List<Resource> resources);
}
