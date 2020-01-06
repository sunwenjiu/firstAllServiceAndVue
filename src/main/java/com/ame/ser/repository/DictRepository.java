package com.ame.ser.repository;import com.ame.ser.model.Dict;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Modifying;import org.springframework.data.jpa.repository.Query;import org.springframework.data.repository.query.Param;import java.util.List;/** * 字典类持久层 * @author SWJ */public interface DictRepository extends JpaRepository<Dict,String> {    /**     * 通过Name字段查找     * @param name     * @return     */    Dict getByName(String name);    /**     * 多选删除     * @param ids     * @return     */    @Modifying    @Query(value="delete from Dict e where e.id in (:ids) ")    int deleteByDictIdIn(@Param("ids")List<String> ids);}