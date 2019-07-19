package com.ame.ser.service;

import com.ame.ser.dto.EditPasswordDTO;
import com.ame.ser.enums.SexEnum;
import com.ame.ser.model.User;
import com.ame.ser.serviceEx.TableFieldContentDuplicateException;
import com.ame.ser.vo.PageVO;
import com.ame.ser.vo.ResultVO;
import com.ame.ser.vo.UserVO;
import org.springframework.data.domain.Page;

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


    /**
     * 创建用户
     * @param user
     * @throws TableFieldContentDuplicateException
     *
     */
    void createUser(User user) throws TableFieldContentDuplicateException;

    // ========================================  SWJ开始 ==================================================

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVO<List<UserVO> > findAllPage(int pageNo, int pageSize);

    /**
     * 通过id单个删除用户（多个删除可实现单个删除，但因前段已设计好，所以暂时保留）
     * @param userId
     */
    void delUserById(String userId);

    /**
     * 通过id 多个或单个删除用户
     * @param ids
     */
    void deleteByUserIdIn(List<String > ids);


    void update(User user);
}
