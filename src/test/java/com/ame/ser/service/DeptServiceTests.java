package com.ame.ser.service;

import com.ame.ser.model.Dept;
import com.ame.ser.service.ex.TableFieldContentDuplicateException;
import com.ame.ser.service.impl.DeptServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptServiceTests {
    private static Logger logger =  LoggerFactory.getLogger(DeptServiceTests.class);
	@Autowired
	private  DeptServiceImpl deptService;

	@Test
	public void createDeptTest() {
		Dept dept= new Dept();
		//dept.setName("手术三部");
		//dept.setDeptDescribe("救死扶伤的，工作很累的");
      try {

          deptService.createDept(dept);
      }catch (TableFieldContentDuplicateException e){
        logger.info(e.getMessage());
      }catch (Exception e){
      	logger.info(e.getMessage());
	  }

	}
	@Test
	public void findAllPage() {
		 Page<Dept> allPage = deptService.findAllPage(1, 5);
		logger.info("分页信息{}",allPage);

	}
	@Test
	public void updataTest() {

		String s = "8bff43c7b4aa47828c955aadc";//60599c5
		String name = "手术7-12部";
		String d = "saveAndFlush修改23";
		try {

		deptService.update(s, name, d);
		}catch (Exception e){
			logger.info(e.getMessage());
		}

	}
	@Test
	public void delByIdTest() {
		String  id="8bff43c7b4aa47828c955aadc";
		try {

			deptService.delDeptById(id);
		}catch (Exception e){
			logger.info("删除没有的ID{}",e.getMessage());
			logger.info(e.getClass().toString());
		}

	}
	@Test
	public void a() {

	}

}
