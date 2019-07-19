package com.ame.ser.service;

import com.ame.ser.model.Dept;
import com.ame.ser.model.Ward;
import com.ame.ser.serviceEx.TableFieldContentDuplicateException;
import com.ame.ser.service.impl.DeptServiceImpl;
import com.ame.ser.service.impl.WardServiceImpl;
import com.ame.ser.vo.PageVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WardServiceTests {
    private static Logger logger =  LoggerFactory.getLogger(WardServiceTests.class);
	@Autowired
	private WardServiceImpl wardService;

	@Test
	public void createWardTest() {
		Ward ward = new Ward();
		ward.setName("大病区");
		ward.setWardDescribe("大病区介绍");
      try {

		  wardService.createWard(ward);
      }catch (TableFieldContentDuplicateException e){
        logger.info("异常{}",e.getMessage());
      }catch (Exception e){
      	logger.info(e.getMessage());
	  }

	}
//	@Test
//	public void findAllPage() {
//		PageVO<List<Dept>> allPage = deptService.findAllPage(1, 5);
//		logger.info("分页信息{}",allPage);
//
//	}
//	@Test
//	public void updataTest() {
//
//		String s = "8bff43c7b4aa47828c955aadc";//60599c5
//		String name = "手术7-12部";
//		String d = "saveAndFlush修改23";
//		try {
//
//		deptService.update(s, name, d);
//		}catch (Exception e){
//			logger.info(e.getMessage());
//		}
//
//	}
//	@Test
//	public void delByIdTest() {
//		String  id="8bff43c7b4aa47828c955aadc";
//		try {
//
//			deptService.delDeptById(id);
//		}catch (Exception e){
//			logger.info("删除没有的ID{}",e.getMessage());
//			logger.info(e.getClass().toString());
//		}
//
//	}
//	@Test
//	public void a() {
//
//	}

}
