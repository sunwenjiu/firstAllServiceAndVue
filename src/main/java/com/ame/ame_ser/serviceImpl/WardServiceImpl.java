package com.ame.ame_ser.serviceImpl;

import com.ame.ame_ser.controllerEx.ParameterInvalidException;
import com.ame.ame_ser.dao.WardRepository;
import com.ame.ame_ser.model.Ward;
import com.ame.ame_ser.service.WardService;
import com.ame.ame_ser.serviceEx.TableFieldContentDuplicateException;
import com.ame.ame_ser.utils.PrimaryKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 病区业务层实现类
 */
@Service
public class WardServiceImpl implements WardService {
    private static Logger logger = LoggerFactory.getLogger(WardServiceImpl.class);

    @Autowired
    private WardRepository wardRepository;

    @Override
    public void createWard(Ward ward) throws TableFieldContentDuplicateException {

        Ward result = wardRepository.getByName(ward.getName());

            logger.info("结果{}",result);
        if (result == null) {

            String id = PrimaryKeyUtil.getPrimaryId();
            ward.setWardId(id);
            Date newTime = new Date();
            ward.setCreateTime(newTime);
            ward.setUpdateTime(newTime);

            //保存持久层
            wardRepository.saveAndFlush(ward);


        }else {
            throw new TableFieldContentDuplicateException("病区已经存在了，不能新建");
        }

    }
}
