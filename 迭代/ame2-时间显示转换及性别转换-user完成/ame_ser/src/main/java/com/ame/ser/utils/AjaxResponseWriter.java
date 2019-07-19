package com.ame.ser.utils;

import com.ame.ser.enums.ServiceStatus;
import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/**
 * Description: 当前端通过ajax请求时，直接将返回结果写回前端
 * Date: 2018-05-03
 * Time: 8:42
 *
 * @author: ycbx
 */
public class AjaxResponseWriter {

    /**
     * 写回数据到前端,响应cors，带状态
     *
     * @param request    the request
     * @param response   the response
     * @param statusCode the status code
     * @param status     {@link }
     * @param message    返回的描述信息
     * @throws IOException the io exception
     */
    public static void write(HttpServletRequest request, HttpServletResponse response, int statusCode, ServiceStatus status, String message) throws IOException {
        writeCorsHeader(request, response, statusCode);
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("service_status", status.code);
        map.put("description", message);
        String result = JsonUtil.objectToJson(map);
        PrintWriter out = response.getWriter();
        try {
            out.print(result);
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 想要cors的响应头,带状态消息
     *
     * @param request    the request
     * @param response   the response
     * @param statusCode the status code
     * @param message    the message
     */
    public static void writeCorsResponse(HttpServletRequest request, HttpServletResponse response, int statusCode, String message) {
        writeCorsHeader(request, response, statusCode);
    }

    /**
     * 想要cors的响应头
     *
     * @param request    the request
     * @param response   the response
     * @param statusCode the status code
     */
    private static void writeCorsHeader(HttpServletRequest request, HttpServletResponse response, int statusCode) {
        String contentType = "application/json";
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, sid, Authorization");
        response.setHeader("Access-Control-Max-Age", "1800");
        if (statusCode != 0) {
            response.setStatus(statusCode);
        }
    }

}
