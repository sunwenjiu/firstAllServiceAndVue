package com.ame.ser.service;
import com.ame.ser.model.Ward;
import com.ame.ser.service.ex.TableFieldContentDuplicateException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WardService {
    void createWard(Ward ward) throws TableFieldContentDuplicateException;
    Page<Ward> findAllPage(int pageNo, int pageSize);
    void delWardById(String wardId);
    void deleteByWardIdIn(List<String > ids);
    void update(String wardId,String name,String wardDescribe);
}
