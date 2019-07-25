package com.ame.ser.service;

import com.ame.ser.model.Resource;
import com.ame.ser.vo.ResultVO;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:18
 *
 * @author: ycbx
 */
public interface ResourceService {

    /**
     * Find one role.
     *
     * @param resId the res id
     * @return the role
     */
    Resource findOne(String resId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Resource> findAll();

    /**
     * Save role.
     *
     * @param resource the resource
     * @return the role
     */
    ResultVO save(Resource resource);

    /**
     * Delete.
     *
     * @param ids the ids
     * @return the result vo
     */
    ResultVO delete(String ids);

    /**
     * Fin by current user result vo.
     *
     * @return the result vo
     */
    ResultVO finByCurrentUser();

    /**
     * List root list response.
     *
     * @return list response
     */
    ResultVO listRoot();

    /**
     * Find tree result vo.
     *
     * @param parentId the parent id
     * @return the result vo
     */
    ResultVO findTree(String parentId);
}
