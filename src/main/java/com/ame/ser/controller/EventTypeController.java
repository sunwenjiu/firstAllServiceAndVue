package com.ame.ser.controller;import com.ame.ser.service.ex.ParameterInvalidException;import com.ame.ser.model.EventType;import com.ame.ser.service.EventTypeService;import com.ame.ser.utils.TreeNodeForVueUtile;import com.ame.ser.vo.ResultVO;import lombok.extern.slf4j.Slf4j;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.domain.Page;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;import java.util.List;/** * 事件类型控制器 * @author SWJ */@Slf4j@RestController@RequestMapping("eventType")public class EventTypeController extends BaseController {    @Autowired    private EventTypeService eventTypeService;    /**     * 创建事件类型     * @param eventType     * @return     */    @RequestMapping("create")    public ResultVO<Void> createEventType(@RequestBody EventType eventType) {       // System.out.println("请求的数据：" + eventType);        if (eventType.getName() == null || eventType.getParentId() == null) {            log.error("创建事件类型时，传入的名字或父类ID为空");            throw new ParameterInvalidException("传入的参数是非法的，参考为:名字是空的");        }        eventTypeService.createEventType(eventType);        return new ResultVO<>(SUCCESS);    }    /**     * 展示事件类型     * @return     */    @RequestMapping("show")    public ResultVO<List<TreeNodeForVueUtile>> showAllEventTypeOnPage() {        List<TreeNodeForVueUtile> resule = eventTypeService.showTreeData();        return new ResultVO<>(SUCCESS, resule);    }    /*@RequestMapping("show")    public ResultVO<Page<EventType>> showAllEventTypeOnPage(int pageNo, int pageSize){        Page<EventType> PageEventType =  eventTypeService.findAllPage(pageNo, pageSize);        return new ResultVO<>(SUCCESS,PageEventType);    }*/   /* @RequestMapping("/del/{id}")    public ResultVO<Void> del(@PathVariable("id") String id) {        eventTypeService.delEventTypeById(id);        return new ResultVO<>(SUCCESS);    }*/    /**     * 删除事件类型（关联删除子级）     * @param id     * @return     */    @RequestMapping("delAll")    public ResultVO<Void> deleteByEventTypeIdIn(String id) {        if (id == null || id.length() < 32) {            log.error("删除事件类型时，传入的ID为空，或ID不正确，ID是：{}",id);            throw new ParameterInvalidException("传入的参数有误");        }        eventTypeService.deleteByEventTypeIdIn(id);        return new ResultVO<>(SUCCESS);    }    /**     * 修改事件类型     * @param eventType     * @return     */    @RequestMapping("update")    public ResultVO<Void> update(@RequestBody EventType eventType) {        if (eventType.getName() == null) {            log.error("修改事件类型时，传入的名字为空");            throw new ParameterInvalidException("传入的参数有误");        }        eventTypeService.update(eventType.getEventTypeId(), eventType.getName(), eventType.getEventTypeDescribe());        return new ResultVO<>(SUCCESS);    }}