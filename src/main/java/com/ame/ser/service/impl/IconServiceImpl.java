package com.ame.ser.service.impl;

import com.ame.ser.enums.ResultEnum;
import com.ame.ser.model.Icon;
import com.ame.ser.repository.IconRepository;
import com.ame.ser.service.IconService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Date: 2018-06-21
 * Time: 9:30
 *
 * @author: ycbx
 */
@Service
public class IconServiceImpl implements IconService {

    @Autowired
    private IconRepository iconRepository;

    /**
     * Find one icon.
     *
     * @return the icon
     */
    @Override
    public ResultVO findOne(String id) {
        Icon icon = iconRepository.getOne(id);
        if (icon == null) {
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        }
        return ResultVOUtil.success(icon);
    }

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<Icon> findAll(Pageable pageable) {
        return iconRepository.findAll(pageable);
    }
}
