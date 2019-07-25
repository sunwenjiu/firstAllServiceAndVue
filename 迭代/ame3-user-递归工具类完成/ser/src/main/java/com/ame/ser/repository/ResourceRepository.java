package com.ame.ser.repository;

import com.ame.ser.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 12:05
 *
 * @author: ycbx
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, String> {
    /**
     * Count by res code long.
     *
     * @param resCode the res code
     * @return long long
     */
    long countByResCode(String resCode);

    /**
     * Count by name long.
     *
     * @param name the name
     * @return long long
     */
    long countByResName(String name);

    /**
     * Count by url long.
     *
     * @param url the url
     * @return long long
     */
    long countByResUrl(String url);

    /**
     * Delete by id in.
     *
     * @param ids the ids
     */
    void deleteByResIdIn(Long[] ids);

    /**
     * Delete by parent.
     *
     * @param resources the resources
     */
    void deleteByParent(List<Resource> resources);

    /**
     * Find by parent id is null list.
     *
     * @return list list
     */
    @Query("FROM Resource WHERE parent.id = null")
    List<Resource> findByParentIsNull();

    /**
     * Delete by parent id in.
     *
     * @param parentIds the parent ids
     */
    @Modifying
    @Query("DELETE FROM Resource WHERE parent.id IN (:parentIds)")
    void deleteByParentIn(@Param("parentIds") List<String> parentIds);

    /**
     * Count by parent long.
     *
     * @param resource the resource
     * @return long long
     */
    long countByParent(Resource resource);
}
