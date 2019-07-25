package com.ame.ser.vo;

import lombok.Data;

import java.io.Serializable;


/**
* Description: http请求返回的最外层对象
* Date: 2017-10-25
* Time: 17:59
*
* @param <T> the type parameter
* @author: ycbx
*/
@Data
public class ResultVO<T> implements Serializable {

   /**
    * 使用了序列化插件生成uid  快捷键设置为Ctrl+Shift+Q
    * The constant serialVersionUID.
    */
   private static final long serialVersionUID = 8848731630266574133L;
   /**
    * 错误码
    * The Code.
    */
   private Integer code;
   /**
    * 提示信息
    * The Msg.
    * = "";值为null返回""
    */
   private String msg;
   /**
    * 具体对象
    * The Data.
    */
   private T data;

   public ResultVO() {
   }

   /*以下构造请勿删除，孙文九用异常捕捉错误提示用到*/
   public ResultVO(Integer code) {
      this.code = code;
   }

   public ResultVO(Integer code, String msg) {

      this.code = code;
      this.msg = msg;
   }

   public ResultVO(Integer code, T data) {
      this.code = code;
      this.data = data;
   }

}
