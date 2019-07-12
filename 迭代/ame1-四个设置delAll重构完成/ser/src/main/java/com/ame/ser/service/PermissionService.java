package com.ame.ser.service;

import com.ame.ser.dto.PermissionChangeDTO;
import com.ame.ser.model.Permission;
import com.ame.ser.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:04
 *
 * @author: ycbx
 */
public interface PermissionService {
    /**
     * Find one role.
     *
     * @param perId the per id
     * @return the role
     */
    Permission findOne(String perId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Permission> findAll();

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<Permission> findAll(Pageable pageable);

    /**
     * Save role.
     *
     * @param permission the permission
     * @return the role
     */
    ResultVO save(Permission permission);

    /**
     * Delete.
     *
     * @param ids the ids
     * @return the result vo
     */
    ResultVO delete(String ids);

    /**
     * List by resource result vo.
     *
     * @param resourceId the resource id
     * @return result vo
     */
    ResultVO listByResource(String resourceId);

    /**
     * List code by user result vo.
     *
     * @return the result vo
     */
    ResultVO listCodeByUser();

    /**
     * List by role and resource result vo.
     *
     * @param roleId the role id
     * @param resId  the res id
     * @return the result vo
     */
    ResultVO listByRoleAndResource(String roleId, String resId);

    /**
     * Change by role result vo.
     *
     * @param request the request
     * @return the result vo
     */
    ResultVO changeByRole(PermissionChangeDTO request);
}
