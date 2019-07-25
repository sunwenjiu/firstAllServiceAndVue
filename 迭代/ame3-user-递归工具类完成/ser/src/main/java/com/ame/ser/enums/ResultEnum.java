package com.ame.ser.enums;

import lombok.Getter;

/**
 * Description: ResultEnum
 * Date: 2018-03-21
 * Time: 14:18
 *
 * @author: ycbx
 */
@Getter
public enum ResultEnum {

    /**
     * Success result enum.
     */
    SUCCESS(0, "成功"),
    /**
     * Error result enum.
     */
    ERROR(1, "失败"),
    /**
     * Param error result enum.
     */
    PARAM_ERROR(2, "参数不正确"),
    /**
     * Form error result enum.
     */
    FORM_ERROR(3, "表单提交失败"),
    /**
     * Data error result enum.
     */
    DATA_ERROR(4, "无数据，请添加或联系管理员"),
    /**
     * Search keyword null result enum.
     */
    SEARCH_KEYWORD_NULL(5, "请输入检索关键字"),
    /**
     * Search data null result enum.
     */
    SEARCH_DATA_NULL(6, "没有检索到相关内容，请更换关键字！"),
    /**
     * Date format error result enum.
     */
    DATE_FORMAT_ERROR(7, "格式错误，请检查！"),
    /**
     * Img not empty result enum.
     */
    IMG_NOT_EMPTY(10, "文件不能为空"),
    /**
     * Img format error result enum.
     */
    IMG_FORMAT_ERROR(11, "图片格式错误"),
    /**
     * Article not empty result enum.
     */
    ARTICLE_NOT_EMPTY(12, "删除失败！请先删除下级数据"),
    /**
     * Validate fail result enum.
     */
    UN_AUTH(401, "未授权，请先登录！"),
    /**
     * No auth result enum.
     */
    NO_AUTH(403, "您无权访问此页面，请联系管理员！"),
    /**
     * No authority result enum.
     */
    IS_LOCK(10001, "登录失败！该用户已被冻结，请联系管理员！"),
    /**
     * User password error result enum.
     */
    USER_PASSWORD_ERROR(10003, "用户名或密码错误！"),
    /**
     * Token error result enum.
     */
    TOKEN_ERROR(10004, "Token错误！"),
    /**
     * Over time result enum.
     */
    OVER_TIME(10005, "登录超时，请重新登录！"),
    /**
     * Exception result enum.
     */
    EXCEPTION(-1, "未知系统错误"),;

    /**
     * The Code.
     */
    private Integer code;

    /**
     * The Message.
     */
    private String message;

    /**
     * Instantiates a new Result enum.
     *
     * @param code    the code
     * @param message the message
     */
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
