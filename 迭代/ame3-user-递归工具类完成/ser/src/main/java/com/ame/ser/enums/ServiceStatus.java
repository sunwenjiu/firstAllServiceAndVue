package com.ame.ser.enums;

/**
 * Description 服务状态
 * Date: 2018-5-21
 * Time: 10:50
 *
 * @author ycbx
 */
public enum ServiceStatus {

    /**
     * Un ahthority service status.
     */
    UN_AHTHORITY(302), /**
     * Un login service status.
     */
    UN_LOGIN(400), /**
     * Illegal token service status.
     */
    ILLEGAL_TOKEN(401);

    /**
     * The Code.
     */
    public int code;

    /**
     * Instantiates a new Service status.
     *
     * @param code the code
     */
    ServiceStatus(int code) {
        this.code = code;
    }
}
