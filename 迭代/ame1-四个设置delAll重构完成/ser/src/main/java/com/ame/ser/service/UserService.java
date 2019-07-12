package com.ame.ser.service;

import com.ame.ser.dto.EditPasswordDTO;
import com.ame.ser.model.User;
import com.ame.ser.vo.ResultVO;
import com.ame.ser.vo.UserVO;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:03
 *
 * @author: ycbx
 */
public interface UserService {

    /**
     * Find one user.
     *
     * @param userId the user id
     * @return the user
     */
    User findOne(String userId);

    /**
     * Find by user name user.
     *
     * @param name the name
     * @return the user
     */
    ResultVO findByUserName(String name);


    /**
     * Find by name user.
     *
     * @param name the name
     * @return the user
     */
    User findByName(String name);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<UserVO> findAll();

    /**
     * Find all page.
     *
     * @param pno  the pno
     * @param size the size
     * @return the page
     */
    ResultVO findAll(Integer pno, Integer size);

    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     */
    ResultVO save(User user);

    /**
     * Delete.
     *
     * @param userId the users id
     * @return the result vo
     */
    ResultVO delete(String userId);

    /**
     * User authorized result vo.
     *
     * @param userId    the user id
     * @param direction the direction
     * @param roleIds   the role ids
     * @return the result vo
     */
    ResultVO userAuthorized(String userId, String direction, String roleIds);

    /**
     * Edit password result vo.
     *
     * @param editPasswordDTO the edit password dto
     * @return the result vo
     */
    ResultVO editPassword(EditPasswordDTO editPasswordDTO);

    /**
     * Validate pass result vo.
     *
     * @param user the user
     * @return the result vo
     */
    ResultVO validatePass(User user);
    /**
     * Current user result vo.
     *
     * @return the result vo
     */
    ResultVO currentUser();


}
