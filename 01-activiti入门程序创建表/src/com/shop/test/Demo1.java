 package com.shop.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

public class Demo1 {

/**
 * 初始化activiti 数据表的方式：3种！
 * 
 * 1）jdbc方式初始化   -》 熟悉 API
 * 
 * 2)spring 方式  -》 明白第三种方式原理
 * 
 * 
 * 3）使用activiti自带对象初始化 （简单方便，开发中常用！） ->掌握
 * 
 * @author newuser
 *
 */
	@Test
	public void fun1()
	{
	  
		

		// 创建  流程引擎管理者
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.
		createStandaloneProcessEngineConfiguration();
		
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti_demo");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("1234");
		
		
		
		/**
		  public static final String DB_SCHEMA_UPDATE_FALSE = "false";  每次都要删除，再创建
		  
		
		  public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop"; 没有表就抛异常

		
		  public static final String DB_SCHEMA_UPDATE_TRUE = "true";  如果没有表，就创建表，如果有，就直接使用！
		  
		  */
		// 设置建表策略
		processEngineConfiguration.setDatabaseSchemaUpdate("true");
		
		
		// 通过 流程引擎管理者 构建出 ProcessEngine 
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		
		System.out.println(processEngine);
		
		
		
		
	}
	
	
	@Test
	public void fun2()
	{
	  
		

		// 创建  流程引擎管理者  加载springxml文件
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		
		
		System.out.println(processEngine);
		
		
		
	}
	

	@Test
	public void fun3()
	{
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    System.out.println(processEngine);
		
	}
	
	
}
