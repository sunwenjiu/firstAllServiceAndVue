package com.ame.ame_ser.dao;

import com.ame.ame_ser.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author LSQ
 * @date 2019/6/25 14:42
 */
@Repository
public interface UsersDao extends JpaRepository<Users,String> {


      Users findByName(String userName);

}
