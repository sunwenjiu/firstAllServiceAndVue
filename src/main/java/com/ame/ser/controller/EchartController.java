package com.ame.ser.controller;

import com.ame.ser.enums.ResultEnum;
import com.ame.ser.service.EventService;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ChartEventTimeVO;
import com.ame.ser.vo.ChartEventTypeVO;
import com.ame.ser.vo.ChartTitleVO;
import com.ame.ser.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author LSQ
 * @date 2019/9/1 14:25
 */
@RestController
@RequestMapping("echart")
public class EchartController {


    @Autowired
    private EventService eventService;

    /**
     * 事件等级统计
     * @return
     */
    @RequestMapping("/getEchartByEventLevel")
    public ResultVO getEchartByEventLevel(){
        Map<String,Object> result = eventService.getEchartByEventLevel();
        if(result != null && result.size() > 0){
            return ResultVOUtil.success(result);
        }
        return ResultVOUtil.error(ResultEnum.DATA_ERROR.getCode(),ResultEnum.DATA_ERROR.getMessage());
    }

    /**
     * 事件类型统计
     * @return
     */
    @RequestMapping("/getEchartByEventType")
    public ResultVO getEchartByEventType(){
        List<ChartEventTypeVO> result = eventService.getEchartByEventType();
        if(result != null && result.size() > 0){
            return ResultVOUtil.success(result);
        }
        return ResultVOUtil.error(ResultEnum.DATA_ERROR.getCode(),ResultEnum.DATA_ERROR.getMessage());
    }

    /**
     * 不同月份事件等级统计
     * @return
     */
    @RequestMapping("/getEchartByEventTime")
    public ResultVO getEchartByEventTime(){
        List<ChartEventTimeVO> result = eventService.getEchartByEventTime();
        if(result != null && result.size() > 0){
            return ResultVOUtil.success(result);
        }
        return ResultVOUtil.error(ResultEnum.DATA_ERROR.getCode(),ResultEnum.DATA_ERROR.getMessage());
    }

    /**
     * 获取首页标题栏相关统计
     * @return
     */
    @RequestMapping("/getEchartByTitle")
    public ResultVO getEchartByTitle(){
        List<ChartTitleVO> result = eventService.getEchartByTitle();
        if(result != null && result.size() > 0){
            return ResultVOUtil.success(result);
        }
        return ResultVOUtil.error(ResultEnum.DATA_ERROR.getCode(),ResultEnum.DATA_ERROR.getMessage());
    }

    /**
     * 获取当前用户不同事件状态的事件数量
     * @param userId
     * @return
     */
    @RequestMapping("/getByEventCountInUser")
    public ResultVO getByEventCountInUser(@RequestParam("userId") String userId){
        Map<String,Object> result = eventService.getByEventCountInUser(userId);
        if(result != null && result.size() > 0){
            return ResultVOUtil.success(result);
        }
        return ResultVOUtil.error(ResultEnum.DATA_ERROR.getCode(),ResultEnum.DATA_ERROR.getMessage());
    }

}
