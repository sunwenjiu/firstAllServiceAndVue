package com.ame.ser.repository;

import com.ame.ser.model.EventTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Bob.C.J
 * @Description
 * @createTime 2019-08-07 14:15
 */
@Repository
public interface EventTaskRepository extends JpaRepository<EventTask,String> {

    EventTask saveAndFlush(EventTask eventTask);

    EventTask save(EventTask eventTask);

    Optional<EventTask> findByEventTypeId(String eventTypeId);



    EventTask findByEventTaskId(String eventTaskId);

    void deleteByEventTaskId(String eventTaskId);
}
