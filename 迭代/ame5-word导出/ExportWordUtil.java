package com.ame.ser.utils;



import cn.afterturn.easypoi.word.WordExportUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Author LSQ
 * @date 2019/7/24 14:00
 */
@Slf4j
public class ExportWordUtil {


    /**
     * 导出word 工具类
     * @param tempPath  word模板地址
     * @param tempDir   生成临时文件地址
     * @param fileName  文件名
     * @param params    参数Map集合
     * @param request
     * @param response
     */
    public static void exportWord(String tempPath, String tempDir, String fileName, Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        if(tempPath.isEmpty() || tempDir.isEmpty() || fileName.isEmpty() || params.isEmpty()){
            log.info("导出word模板参数错误");
            return;
        }
        if(!tempDir.endsWith("/")){
            tempDir = tempDir + File.separator;
        }
        File dir = new File(tempDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
           String userAgent = request.getHeader("user-agent").toLowerCase();
           if(userAgent.contains("msie") || userAgent.contains("like gecko")){
               fileName = URLEncoder.encode(fileName,"UTF-8");
           }else {
               fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
           }
           XWPFDocument doc = WordExportUtil.exportWord07(tempPath,params);
           String newTempPath = tempDir + fileName;
            FileOutputStream fos = new FileOutputStream(newTempPath);
            doc.write(fos);
            // 下载后不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
        }catch (Exception e){
           log.error("导出word模板异常，异常为：{}",e);
        }finally {
            // 删除临时文件夹
            delFileWord(tempDir,fileName);
        }

    }

    public static void delFileWord(String filePath, String fileName){
        File file =new File(filePath+fileName);
        File file1 =new File(filePath);
        file.delete();
        file1.delete();
    }

}
