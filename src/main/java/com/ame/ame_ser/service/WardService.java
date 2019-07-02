package com.ame.ame_ser.service;

import com.ame.ame_ser.model.Ward;
import com.ame.ame_ser.serviceEx.TableFieldContentDuplicateException;

/**
 *病区的业务层接口
 * @author SWJ
 */
public interface WardService {
    void createWard(Ward ward) throws TableFieldContentDuplicateException;

}
