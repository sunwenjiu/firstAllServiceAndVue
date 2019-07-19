package com.ame.ser.service.impl;import com.ame.ser.controllerEx.ParameterInvalidException;import com.ame.ser.repository.DeptRepository;import com.ame.ser.model.Dept;import com.ame.ser.service.DeptService;import com.ame.ser.serviceEx.TableFieldContentDuplicateException;import com.ame.ser.utils.PrimaryKeyUtil;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.dao.EmptyResultDataAccessException;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;import org.springframework.data.domain.Sort;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.util.Date;import java.util.List;import java.util.Optional;/** * 科室/部门的业务层实现类 * * @author SWJ */@Servicepublic class DeptServiceImpl implements DeptService {    private static Logger logger = LoggerFactory.getLogger(DeptServiceImpl.class);    @Autowired    private DeptRepository deptRepository;    @Override    public void createDept(Dept dept) throws TableFieldContentDuplicateException {        Dept result = deptRepository.getByName(dept.getName());        logger.info("新创建部门是否存在：{}", result);        if (result == null) {            //部门不存在，可以创建            //封装uuid,            String id = PrimaryKeyUtil.getPrimaryId();            logger.info(id);            dept.setDeptId(id);            //封装创建时间和修改时间            Date newTime = new Date();            dept.setCreateTime(newTime);            dept.setUpdateTime(newTime);            //保存持久层            deptRepository.save(dept);        } else {            //部门存在了，不能创建            throw new TableFieldContentDuplicateException("部门存在了，不能创建.");        }    }    @Override    public void delDeptById(String deptId) {        try {            deptRepository.deleteById(deptId);        }catch (EmptyResultDataAccessException e){            throw new ParameterInvalidException("传入的参数有误，数据不存在");        }    }    @Transactional    @Override    public void deleteByDeptIdIn(List<String > ids) {        try {            deptRepository.deleteByDeptIdIn(ids);        }catch (Exception e){            throw new ParameterInvalidException("传入的参数有误");        }    }    @Override    public Page<Dept> findAllPage(int pageNo, int pageSize) {        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);        Page<Dept> depts = deptRepository.findAll(pageable);        return depts;    }    @Override    public void update(String deptId, String name, String deptDescribe) {        Optional<Dept> byId = deptRepository.findById(deptId);        if (!byId.isPresent()) {           throw new ParameterInvalidException("传入的参数有误");        }        Dept dept =  byId.get();        if (name.equals(dept.getName())) {            dept.setDeptDescribe(deptDescribe);            dept.setUpdateTime(new Date());            deptRepository.saveAndFlush(dept);            return;        }        Dept result = deptRepository.getByName(name);        if (result !=null) {            //用户名重复了            throw new TableFieldContentDuplicateException("部门存在了，不能创建.");        }        dept.setName(name);        dept.setDeptDescribe(deptDescribe);        dept.setUpdateTime(new Date());        deptRepository.saveAndFlush(dept);    }}