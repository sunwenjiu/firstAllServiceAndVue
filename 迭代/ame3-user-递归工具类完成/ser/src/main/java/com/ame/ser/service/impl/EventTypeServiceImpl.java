package com.ame.ser.service.impl;import com.ame.ser.model.EventType;import com.ame.ser.repository.EventTypeRepository;import com.ame.ser.service.EventTypeService;import org.springframework.stereotype.Service;import com.ame.ser.service.ex.ParameterInvalidException;import com.ame.ser.service.ex.TableFieldContentDuplicateException;import com.ame.ser.utils.PrimaryKeyUtil;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.dao.EmptyResultDataAccessException;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;import org.springframework.data.domain.Sort;import java.util.Date;import java.util.List;import java.util.Optional;/** * 事件类型业务层实现类 * @author SWJ */@Servicepublic class EventTypeServiceImpl implements EventTypeService {    private static Logger logger = LoggerFactory.getLogger(EventTypeServiceImpl.class);    @Autowired    private EventTypeRepository eventTypeRepository;    @Override    public void createEventType(EventType eventType) throws TableFieldContentDuplicateException {        EventType result = eventTypeRepository.getByName(eventType.getName());        // logger.info("结果{}",result);        if (result == null) {            String id = PrimaryKeyUtil.getPrimaryId();            eventType.setEventTypeId(id);            Date newTime = new Date();            eventType.setCreateTime(newTime);            eventType.setUpdateTime(newTime);            //保存持久层            eventTypeRepository.saveAndFlush(eventType);        }else {            throw new TableFieldContentDuplicateException("事件类型已经存在了，不能新建");        }    }    @Override    public Page<EventType> findAllPage(int pageNo, int pageSize) {        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);        Page<EventType> eventTypes = eventTypeRepository.findAll(pageable);        return eventTypes;    }    @Override    public void delEventTypeById(String eventTypeId) {        try {            eventTypeRepository.deleteById(eventTypeId);        }catch (EmptyResultDataAccessException e){            throw new ParameterInvalidException("传入的参数有误，数据不存在");        }    }    @Override    public void deleteByEventTypeIdIn(List<String > ids) {        try {            eventTypeRepository.deleteByEventTypeIdIn(ids);        }catch (Exception e){            throw new ParameterInvalidException("传入的参数有误");        }    }    @Override    public void update(String eventTypeId, String name, String eventTypeDescribe) {        Optional<EventType> byId = eventTypeRepository.findById(eventTypeId);        if (!byId.isPresent()) {            throw new ParameterInvalidException("传入的参数有误");        }        EventType eventType =  byId.get();        if (name.equals(eventType.getName())) {            eventType.setEventTypeDescribe(eventTypeDescribe);            eventType.setUpdateTime(new Date());            eventTypeRepository.saveAndFlush(eventType);            return;        }        EventType result = eventTypeRepository.getByName(name);        if (result !=null) {            throw new TableFieldContentDuplicateException("事件类型存在了，不能创建.");        }        eventType.setName(name);        eventType.setEventTypeDescribe(eventTypeDescribe);        eventType.setUpdateTime(new Date());        eventTypeRepository.saveAndFlush(eventType);    }}