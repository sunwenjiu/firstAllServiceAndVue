package com.ame.ser.controller;

import com.ame.ser.config.WebSocket;
import com.ame.ser.utils.ResultVOUtil;
import com.ame.ser.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LSQ
 * weibSocket 推送测试
 * @date 2019/7/10 9:39
 */
@RestController
public class TestController {

    @Autowired
    WebSocket webSocket;

    @GetMapping("/sendAllWebSocket")
    public ResultVO test() {
        String text="你们好！这是websocket群体发送！";
        webSocket.sendAllMessage(text);
        return ResultVOUtil.success();
    }

    @GetMapping("/sendOneWebSocket/{clientId}")
    public ResultVO sendOneWebSocket(@PathVariable("clientId") String clientId) {
        String text = clientId + " 你好！ 这是websocket单人发送！";
        webSocket.sendOneMessage(clientId, text);
        return ResultVOUtil.success();
    }
}
