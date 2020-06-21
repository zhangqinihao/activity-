package com.shop.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
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

	// 3 查看个人任务列表
	@Test
	public void fun3() {

		String assignee ="小明";

		// String assignee ="小强";

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

	// 4.完成个人任务
	@Test
	public void fun4() {
		// String taskId="104";

		String taskId = "1804";

		// String taskId="302";
		processEngine.getTaskService().complete(taskId);

		System.out.println("任务已经完成");
	}

	// 查询流程定义列表
	/*
	 * act_re_procdef
	 */
	@Test
	public void fun5() {
		List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionName().desc().list();

		if (list != null && list.size() > 0) {
			for (ProcessDefinition processDefinition : list) {
				System.out.println("流程定义id:" + processDefinition.getId());
				System.out.println("部署id:" + processDefinition.getDeploymentId());
				System.out.println("流程定义的key:" + processDefinition.getKey());
				System.out.println("流程定义的版本:" + processDefinition.getVersion());

				System.out.println("流程定义 bpmn:" + processDefinition.getResourceName());
				System.out.println("流程定义的png:" + processDefinition.getDiagramResourceName());

			}
		}
	}

	// 删除部署信息
	@Test
	public void fun6() {
		String deploymentId = "1401";

		
		// 1.普通删除 （如果当前流程正在运行去删除的话，直接抛出异常！）
		
		  processEngine.getRepositoryService() .deleteDeployment(deploymentId );
		  
		  System.out.println("删除完成");
		 

		
	}

	// 删除部署信息
	@Test
	public void fun7() {
		String deploymentId = "1601";

		
		// 2.强制删除（级联删除 ）即使当前流程正在运行也可以直接干掉！！

		processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
		System.out.println("删除完成");
	}
	
	// 获取流程定义png图片和保存
	/*
	 * act_ge_bytearray
	 * */
		@Test
		public void fun8() throws IOException {

			String resourceName = ""; // 部署流程中 图片名称

			String deploymentId = "1701";
			List<String> list = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);

			if (list != null && list.size() > 0) {
				for (String name : list) {

					// 判断找到的一定是 png
					if (name.indexOf(".png") > -1) {
						resourceName = name;
					}
				}
			}
			
			System.out.println(resourceName);
			
			
			// 将数据库查询到的  png 图片  保存到本地磁盘
			
			InputStream inputStream = processEngine
			.getRepositoryService()
			.getResourceAsStream(deploymentId, resourceName);
			
			
			
			// 定义好存储磁盘路径
			File file = new File("D:\\"+resourceName);
			
			
			
			
			FileUtils.copyInputStreamToFile(inputStream, file);
			
			System.out.println("保存成功");
			
		}
	

}
