package com.shop.test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


/**
 *
 */
public class Demo1 {


	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	
	/// 1.发布流程（部署流程） 前提：准备好流程图
	
	// 从 zip压缩包中 部署流程
	@Test
	public void fun1()
	{
        /*
         * 1.act_re_procdef（流程定义表）
         * 
         * 2.act_re_deployment（部署对象表）
         * 3)act_ge_bytearray（资源文件表）
         * */
		
		//InputStream in = new FileInputStream(name)
		
		InputStream in =  this.getClass()
				.getClassLoader()
				.getResourceAsStream("digram/HelloWord.zip");
		
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine
		.getRepositoryService()
	
		.createDeployment()
		.name("zip部署测试")
		.addZipInputStream(zipInputStream)
		.deploy();
		
		
		System.out.println("部署的id:"+deployment.getId());
		System.out.println("部署名称:"+deployment.getName());
	}

	
	// 2。启动流程  -》让流程开始推荐  （activiti必然会操作某些表）
	/*
	 * act_hi_procinst;    -- 流程实例历史表
	 * act_ru_execution;  --  流程实例的执行对象表
	 * act_ru_task;  -- 当前活动（节点）任务表 ,默认  act_ru_task 和 act_ru_execution 是一对一
	 * **/
			@Test
			public void fun2()
			{
			  String processDefinitionKey="helloword";
			ProcessInstance processInstance = processEngine
					.getRuntimeService()
			  .startProcessInstanceByKey(processDefinitionKey);
			
			System.out.println("流程定义id:"+processInstance.getProcessDefinitionId());
			
			System.out.println("流程实例id:"+processInstance.getProcessInstanceId());
			
			
			
			}
			
			
			//3 查看个人任务列表
			/*
			 * 
			 * 
				注意：当所有的流程走完了后，那么在
				
				act_ru_execution;  --  流程实例的执行对象表
				
				
				act_ru_task;    -- 正在运行任务（节点）
				
				以上两种表中看不到任何信息了，都将成为历史数据，所以需要查看另外一张表！
				
				
				act-hi-taskinst 历史任务记录表！
			 * 
			 * 
			 * */
			@Test
			public void fun3()
			{
			    
				//String assignee ="小明";
				
				//String assignee ="小强";

				String assignee ="小丽";
				
				List<Task> list = processEngine
				.getTaskService()
				.createTaskQuery()
				.taskAssignee(assignee)
				.list();
			
				if(list!=null && list.size()>0)
				{
				  for (Task task : list) {
					 
					  System.out.println("流程定义id:"+task.getProcessDefinitionId());
					  System.out.println("任务id:"+task.getId());
					  System.out.println("待办人:"+task.getAssignee());
					  System.out.println("任务创建时间:"+task.getCreateTime());
					  System.out.println("任务名称:"+task.getName());
				}	
				}
			
			}
			
			//4.完成个人任务
			@Test
			public void fun4()
			{
			  String taskId="1202";
			  
			 //String taskId="702";
			  
			 // String taskId="302";
			processEngine
			  .getTaskService()
			  .complete(taskId);
			
			System.out.println("任务已经完成");
			}
			
		
}
