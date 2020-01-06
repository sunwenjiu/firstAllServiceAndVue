package com.ame.ser.service.impl;

import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author LSQ
 * @date 2019/7/12 10:18
 */
@Service
public class TestServiceImpi {



    public ResultVO testSelect(){
        return ResultVOUtil.success();
    }

}
