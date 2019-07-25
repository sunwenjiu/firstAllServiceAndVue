package com.ame.ser.controller;import com.ame.ser.service.ex.ParameterInvalidException;import com.ame.ser.model.EventType;import com.ame.ser.service.EventTypeService;import com.ame.ser.vo.ResultVO;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.domain.Page;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;import java.util.List;@RestController@RequestMapping("eventType")public class EventTypeController extends BaseController{    @Autowired    private EventTypeService eventTypeService;    @RequestMapping("create")    public ResultVO<Void> createEventType(@RequestBody EventType eventType){        if (eventType.getName()==null || eventType.getName().isEmpty()) {            throw new ParameterInvalidException("传入的参数是非法的，参考为:名字是空的");        }        eventTypeService.createEventType(eventType);        return new ResultVO<>(SUCCESS);    }    @RequestMapping("show")    public ResultVO<Page<EventType>> showAllEventTypeOnPage(int pageNo, int pageSize){        Page<EventType> PageEventType =  eventTypeService.findAllPage(pageNo, pageSize);        return new ResultVO<>(SUCCESS,PageEventType);    }    @RequestMapping("/del/{id}")    public ResultVO<Void> del(@PathVariable("id") String id ){        eventTypeService.delEventTypeById(id);        return new ResultVO<>(SUCCESS);    }    @RequestMapping("delAll")    public ResultVO<Void> deleteByEventTypeIdIn(  String ids){        List<String> LString = regDelAllData(ids);        eventTypeService.deleteByEventTypeIdIn(LString);        return new ResultVO<>(SUCCESS);    }    @RequestMapping("update")    public ResultVO<Void> update(@RequestBody EventType eventType){        if (eventType.getName()==null) {            throw new ParameterInvalidException("传入的参数有误");        }        eventTypeService.update(eventType.getEventTypeId() , eventType.getName(), eventType.getEventTypeDescribe());        return new ResultVO<>(SUCCESS);    }}