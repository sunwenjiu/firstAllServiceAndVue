package com.ame.ser.controller;

import com.ame.ser.dto.EventTaskDTO;
import com.ame.ser.dto.EventTaskUserDTO;
import com.ame.ser.model.EventTask;
import com.ame.ser.model.EventTaskUser;
import com.ame.ser.model.EventType;
import com.ame.ser.model.User;
import com.ame.ser.service.SetEventTaskService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Bob.C.J
 * @Description
 * @createTime 2019-08-07 15:35
 */
@RestController
@RequestMapping("eventTask")
public class SetEventTaskController extends BaseController {

    @Autowired
    SetEventTaskService setEventTaskService;

    @RequestMapping("getEventTask")
    public ResultVO<List<EventTaskDTO>> getEventTask() {
        List<EventTaskDTO> eventTask = setEventTaskService.getEventTask();
        return ResultVOUtil.success(eventTask);
    }

    @RequestMapping("getUser")
    public ResultVO<List<User>> getUser(String userName){
        return ResultVOUtil.success(setEventTaskService.findUserByNameLike(userName));
    }

    @RequestMapping("addETU")
    public ResultVO addEventTaskUser(@RequestBody EventTaskUser eventTaskUser) {
        setEventTaskService.addEventTaskUser(eventTaskUser);
        return ResultVOUtil.success();
    }

    @RequestMapping("deleteTask")
    public ResultVO deleteEventTask(@RequestBody EventTaskDTO eventTask) {
        setEventTaskService.deleteEventTask(eventTask.getEventTaskId());
        return ResultVOUtil.success();
    }

    @RequestMapping("deleteTaskUser")
    public ResultVO deleteEventTaskUser(@RequestBody EventTaskUser eventTaskUser) {
        System.out.println(ToStringBuilder.reflectionToString(eventTaskUser,ToStringStyle.JSON_STYLE));
        setEventTaskService.deleteEventTaskUser(eventTaskUser);
        return ResultVOUtil.success();
    }

}
