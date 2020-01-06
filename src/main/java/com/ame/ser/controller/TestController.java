package com.ame.ser.controller;

import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.ame.ser.config.WebSocket;
import com.ame.ser.dto.MessageDTO;
import com.ame.ser.model.Event;
import com.ame.ser.model.EventType;
import com.ame.ser.repository.EventRepository;
import com.ame.ser.service.impl.TestServiceImpi;
import com.ame.ser.utils.ExportWordUtil;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.utils.TreeNodeForVueUtile;
import com.ame.ser.vo.EventExcelVO;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author LSQ
 * weibSocket Controller 测试
 * @date 2019/7/10 9:39
 */
@RestController
public class TestController {

    @Autowired
    WebSocket webSocket;

    @Autowired
    TestServiceImpi testServiceImpi;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private EventRepository eventRepository;


    // ----------------webSocket 测试----------------
    @GetMapping("/sendAllWebSocket")
    public ResultVO test() {
        String text = "你们好！这是websocket群体发送3333！";
        webSocket.sendAllMessage(text);
        return ResultVOUtil.success();
    }

    @GetMapping("/sendOneWebSocket/{clientId}")
    public ResultVO sendOneWebSocket(@PathVariable("clientId") String clientId) {
        String text = clientId + " 你好！ 这是websocket单人发送！";
        webSocket.sendOneMessage(clientId, text);
        return ResultVOUtil.success();
    }

    // ----------------redis 缓存测试----------------

    @RequestMapping("/redisSelect")
    public ResultVO redisSelect() {
        String result = stringRedisTemplate.opsForValue().get("lsq");
        if (result == null) {
            stringRedisTemplate.opsForValue().set("lsq", "lsq");
            return ResultVOUtil.success("缓存中无数据");
        }
        return ResultVOUtil.success(result);
    }

    //---- 导出word 模板测试--------

    @RequestMapping("/exportWord")
    public void exportWord(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "药品不良事件（单项）报告");
        params.put("name", "患者A");
        params.put("age", "22");
        params.put("patId", "244350");
        params.put("sex", "男");
        params.put("type", "药品不良事件");
        params.put("level", "1级事件");
        params.put("addr", "病区护士站");
        params.put("writeName", "匿名");
        params.put("handleName", "琦玉老师");
        params.put("closeName", "卡卡西老师");
        params.put("play", "临床表现临床表现临床表现临床表现");
        params.put("reason1", "原因1原因1原因1原因1原因1");
        params.put("reason2", "原因2原因2原因2原因2原因2");
        params.put("reason3", "原因3原因3原因3原因3原因3");
        params.put("reason4", "原因4原因4原因4原因4原因4");
        params.put("reason5", "原因5原因5原因5原因5原因5");
        params.put("reason6", "原因6原因6原因6原因6原因6");
        params.put("evaluate", "这是评价评价评价评价评价");
        params.put("proposal", "这是改进措施改进措施改进措施");
        params.put("writeTime", "李四");
        ExportWordUtil.exportWord("word/event.docx", "E:/del", "aaa.docx", params, request, response);
    }

    /**
     * 测试树 swj
     * @return
     */
    @RequestMapping("tree")
    public ResultVO<List<TreeNodeForVueUtile>> getTree(){

        EventType e1=new EventType();
        e1.setParentId("0");
        e1.setName("e1");
        e1.setEventTypeId("111");

        EventType e2=new EventType();
        e2.setParentId("0");
        e2.setName("e2");
        e2.setEventTypeId("222");

        EventType e3=new EventType();
        e3.setParentId("111");
        e3.setName("e3");
        e3.setEventTypeId("333");

        EventType e4=new EventType();
        e4.setParentId("222");
        e4.setName("e4");
        e4.setEventTypeId("444");

        EventType e5=new EventType();
        e5.setParentId("444");
        e5.setName("e5");
        e5.setEventTypeId("555");

        EventType e6=new EventType();
        e6.setParentId("444");
        e6.setName("e6");
        e6.setEventTypeId("666");

        List<EventType> list =new ArrayList();
        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
        list.add(e5);
        list.add(e6);

        //以上是模拟数据，
        //以下是转换数据
        List< TreeNodeForVueUtile> tree = new ArrayList<>();
        for (EventType e:list //原来的数据进行转换
                ) {
            String title =e.getName();
            String id = e.getEventTypeId();
            String parentId =e.getParentId();
            tree.add(new TreeNodeForVueUtile(title, id, parentId,e.getEventTypeDescribe()));
        }
        List<TreeNodeForVueUtile> result =  TreeNodeForVueUtile.getTreeResult(tree);     //递归得到的结果

        return new ResultVO<>(200,result);
    }

    @RequestMapping("getEvent")
    public ResultVO<Event> getE(){
        Event e =eventRepository.findById("20bed65e92f24a34a621a57ffe9ff476").get();
        return  new ResultVO<>(0, e);
    }

    @RequestMapping("T/message/init")
    public ResultVO getMessage(){
        Date now =new Date();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E a");
        String nows =sdf.format(now);

        MessageDTO messageDTO =new MessageDTO();
        messageDTO.setMsgId("1111111");
        messageDTO.setTitle("标题在这里1");
        messageDTO.setCreateTime( nows);

        MessageDTO messageDTO2 =new MessageDTO();
        messageDTO2.setMsgId("2222hh2222");
        messageDTO2.setTitle("标题在这里2");
        messageDTO2.setCreateTime( nows);

        MessageDTO messageDTO3 =new MessageDTO();
        messageDTO3.setMsgId("3333333");
        messageDTO3.setTitle("标题在这里3");
        messageDTO3.setCreateTime( nows);

        MessageDTO messageDTO4 =new MessageDTO();
        messageDTO4.setMsgId("4444444444");
        messageDTO4.setTitle("标题在这里4");
        messageDTO4.setCreateTime( nows);

        List<MessageDTO> list =new ArrayList<>();
        list.add(messageDTO);
        list.add(messageDTO2);
        list.add(messageDTO4);
        List<MessageDTO> list2 =new ArrayList<>();
        list2.add(messageDTO3);

        Map<String,List> listMap = new HashMap<>();
        listMap.put("unread",list );
        listMap.put("readed", list2);



        return new ResultVO(0, listMap);
    }

    @RequestMapping("T/message/content")
    public ResultVO getMessageContentById(String id){
        MessageDTO messageDTO =new MessageDTO();
        System.out.println("获取内容id==================="+id);

        String content ="这里的话被传过去了";

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setData(content);


        return resultVO;
    }

    @RequestMapping("T/message/has_read")
    public ResultVO messageHasRead(@RequestBody MessageDTO messageDTO){

        System.out.println("标记已读：========"+messageDTO.getMsgId() );
        return  new ResultVO(0);
    }
    @RequestMapping("T/message/count")
    public ResultVO messageUnreadCount(){

        System.out.println("未读统计：========" );
        int count =10;
        return  new ResultVO(0,count);
    }

    //---- 导出excel 模板测试--------
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response){
        Date date1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = df.format(date1);

        EventExcelVO excelVO = new EventExcelVO();
        excelVO.setEventId("1111");
        excelVO.setPatientName("AAA");
        excelVO.setPatientNumber("34444");
        excelVO.setPatientBirthday("2078766");
        EventExcelVO excelVO1 = new EventExcelVO();
        excelVO1.setEventId("3455");
        excelVO1.setPatientName("bbb");
        excelVO1.setPatientNumber("28987");
        excelVO1.setPatientBirthday("21435465");
        List<EventExcelVO> list = new ArrayList<>();
        list.add(excelVO);
        list.add(excelVO1);
        TemplateExportParams params = new TemplateExportParams("excel/eventExcel.xls", true);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("date", date);//导出一般都要日期
        data.put("list", list);//导出list集合

        ExportWordUtil.exportExcel(response,params,data,"测试Excel");
    }


}
