package com.ame.ser.service;
import com.ame.ser.model.Ward;
import com.ame.ser.service.ex.TableFieldContentDuplicateException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WardService {
    /**
     * 创建病区
     * @param ward
     * @throws TableFieldContentDuplicateException
     */
    void createWard(Ward ward) throws TableFieldContentDuplicateException;

    /**
     * 分页显示
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Ward> findAllPage(int pageNo, int pageSize);

    /**
     * 显示全部
     * @return
     */
    List<Ward> findAll();

    /**
     * 通过id删除（或被迭代）
     * @param wardId
     */
    void delWardById(String wardId);

    /**
     * 多选删除
     * @param ids
     */
    void deleteByWardIdIn(List<String > ids);

    /**
     * 修改数据
     * @param wardId
     * @param name
     * @param wardDescribe
     */
    void update(String wardId,String name,String wardDescribe);
}
