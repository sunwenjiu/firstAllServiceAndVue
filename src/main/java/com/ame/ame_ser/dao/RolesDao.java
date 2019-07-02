package com.ame.ame_ser.dao;

import com.ame.ame_ser.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author LSQ
 * @date 2019/6/25 14:46
 */
@Repository
public interface RolesDao extends JpaRepository<Roles,String> {


}
