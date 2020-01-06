package com.ame.ser.controller;import com.ame.ser.service.ex.ParameterInvalidException;import com.ame.ser.model.EventLevel;import com.ame.ser.service.EventLevelService;import com.ame.ser.vo.ResultVO;import lombok.extern.slf4j.Slf4j;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.domain.Page;import org.springframework.web.bind.annotation.*;import java.util.List;/** * 事件等级控制器 * @author SWJ */@Slf4j@RestController@RequestMapping("eventLevel")public class EventLevelController extends BaseController{    @Autowired    private EventLevelService eventLevelService;    /**     * 创建事件等级     * @param eventLevel     * @return     */    @RequestMapping("create")    public ResultVO<Void> createEventLevel(@RequestBody EventLevel eventLevel){        if (eventLevel.getName()==null || eventLevel.getName().isEmpty()) {            log.error("创建事件等级时，传入的名字为空");            throw new ParameterInvalidException("传入的参数是非法的，参考为:名字是空的");        }        eventLevelService.createEventLevel(eventLevel);        return new ResultVO<>(SUCCESS);    }    /**     * 分页展示事件等级     * @param pageNo     * @param pageSize     * @return     */    @RequestMapping("show")    public ResultVO<Page<EventLevel>> showAllEventLevelOnPage(int pageNo, int pageSize){        Page<EventLevel> PageEventLevel =  eventLevelService.findAllPage(pageNo, pageSize);        return new ResultVO<>(SUCCESS,PageEventLevel);    }    /**     * 全部显示事件等级     * @return     */    @RequestMapping("showAll")    public ResultVO<List<EventLevel>> showAllDept(){        List<EventLevel> PageDept =  eventLevelService.findAll();        return new ResultVO<>(SUCCESS,PageDept);    }    /**     * 通过ID单个删除事件等级（或被迭代）     * @param id     * @return     */    @RequestMapping("/del/{id}")    public ResultVO<Void> del(@PathVariable("id") String id ){        eventLevelService.delEventLevelById(id);        return new ResultVO<>(SUCCESS);    }    /**     * 通过id  多个删除事件等级     * @param ids     * @return     */    @RequestMapping("delAll")    public ResultVO<Void> deleteByEventLevelIdIn(String ids){        List<String> LString = regDelAllData(ids);        eventLevelService.deleteByEventLevelIdIn(LString);        return new ResultVO<>(SUCCESS);    }    /**     * 修改事件等级     * @param eventLevel     * @return     */    @RequestMapping("update")    public ResultVO<Void> update(@RequestBody EventLevel eventLevel){        if (eventLevel.getName()==null) {            log.error("修改事件等级时，传入的名字为空");            throw new ParameterInvalidException("传入的参数有误");        }        eventLevelService.update(eventLevel.getEventLevelId() , eventLevel.getName(), eventLevel.getEventLevelDescribe());        return new ResultVO<>(SUCCESS);    }}