package com.ame.ser.repository;

import com.ame.ser.model.EventTask;
import com.ame.ser.model.EventTaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bob.C.J
 * @Description
 * @createTime 2019-08-07 14:15
 */
@Repository
public interface EventTaskUserRepository extends JpaRepository<EventTaskUser,String> {
    
    List<EventTaskUser> findByUserId(String userId);

    List<EventTaskUser> findByEventTaskId(String eventTaskId);

    void deleteByEventTaskId(String eventTaskId);

    List<EventTaskUser> findByEventTaskIdAndUserType(String eventTaskId, Integer userType);
}
