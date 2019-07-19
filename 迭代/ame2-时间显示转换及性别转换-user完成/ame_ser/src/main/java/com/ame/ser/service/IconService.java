package com.ame.ser.service;

import com.ame.ser.model.Icon;
import com.ame.ser.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Description:
 * Date: 2018-06-21
 * Time: 9:27
 *
 * @author: ycbx
 */
public interface IconService {
    /**
     * Find one icon.
     *
     * @param id the id
     * @return the icon
     */
    ResultVO findOne(String id);

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<Icon> findAll(Pageable pageable);
}
