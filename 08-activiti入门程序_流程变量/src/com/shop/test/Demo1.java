package com.shop.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 *
 */
public class Demo1 {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/// 1.发布流程（部署流程） 前提：准备好流程图
	@Test
	public void fun1() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name("helloworld入门程序")
				.addClasspathResource("digram/HelloWord.bpmn").addClasspathResource("digram/HelloWord.png").deploy();

		System.out.println("部署流程id:" + deployment.getId());
		System.out.println("部署流程的name:" + deployment.getName());
	}

	// 2。启动流程 -》让流程开始推荐 （activiti必然会操作某些表）

	@Test
	public void fun2() {
		String processDefinitionKey = "helloword";
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey);

		System.out.println("流程定义id:" + processInstance.getProcessDefinitionId());

		System.out.println("流程实例id:" + processInstance.getProcessInstanceId());

	}
	
	
	/*
	 * 1）可以在推进流程的时候，传值到另外一个任务中！

       2）根据设置的不同流程变量的值，控制我们流程到底怎么走！
	 * */
	
	// 设置流程变量
	@Test
	public void setVariable() {

		String taskId = "1902";
		TaskService taskService = processEngine.getTaskService();
		taskService.setVariable(taskId, "金额", 1000);
		taskService.setVariable(taskId, "日期", new Date());

		System.out.println("设置流程变量完成");

	}
	// 4.完成个人任务
		@Test
		public void fun4() {
			// String taskId="104";

			String taskId = "1902";

			// String taskId="302";
			processEngine.getTaskService().complete(taskId);

			System.out.println("任务已经完成");
		}
	// 获取流程变量
	/*
	 * act_hi_varinst
	 * */
	@Test
	public void getVariable() {
		String taskId = "2102";
		Integer variable = (Integer) processEngine.getTaskService().getVariable(taskId, "金额");

		System.out.println(variable);

	}

	// 3 查看个人任务列表
	@Test
	public void fun3() {

		//String assignee ="小明";

		 String assignee ="小强";

		//String assignee = "小丽";

		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();

		if (list != null && list.size() > 0) {
			for (Task task : list) {

				System.out.println("流程定义id:" + task.getProcessDefinitionId());
				System.out.println("任务id:" + task.getId());
				System.out.println("待办人:" + task.getAssignee());
				System.out.println("任务创建时间:" + task.getCreateTime());
				System.out.println("任务名称:" + task.getName());
			}
		}

	}

	

	
		
		
		

}
