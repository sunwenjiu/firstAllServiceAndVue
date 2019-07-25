package com.ame.ser.repository;import com.ame.ser.model.EventType;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Modifying;import org.springframework.data.jpa.repository.Query;import org.springframework.data.repository.query.Param;import org.springframework.transaction.annotation.Transactional;import java.util.List;/** * 事件类型持久层 * @author SWJ */public interface EventTypeRepository extends JpaRepository<EventType,String> {    EventType getByName(String name);    @Modifying    @Transactional    @Query(value="delete from EventType e where e.id in (:ids) ")    int deleteByEventTypeIdIn(@Param("ids")List<String> ids);}