package com.shop.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.LeavebillMapper;
import com.shop.pojo.Leavebill;
import com.shop.utils.Constants;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;

	@Autowired
	private LeavebillMapper leaveBillMapper;

	public void saveNewDeploy(InputStream in, String filename) {

		try {
			// 将普通的file流转换成 ZipInputStream流
			ZipInputStream zipInputStream = new ZipInputStream(in);

			repositoryService.createDeployment().name(filename).addZipInputStream(zipInputStream).deploy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Task> findTaskListByName(String name) {

		List<Task> list = taskService.createTaskQuery().taskAssignee(name).orderByTaskCreateTime().desc().list();
		return list;
	}

	public void saveStartProcess(String username) {
		String key = Constants.Leave_KEY;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", username); // ${userId}
		this.runtimeService.startProcessInstanceByKey(key, map);
	}

	public void saveStartProcess2(Long leaveId, String username) {
		// 请假业务和 流程信息进行关联 BUSSINSS_KEY
		String key = Constants.Leave_KEY;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", username);

		// 定义规则
		String BUSSINSS_KEY = key + "." + leaveId;
		map.put("objId", BUSSINSS_KEY);

		this.runtimeService.startProcessInstanceByKey(key, BUSSINSS_KEY, map);
	}

	public Leavebill findLeaveBillByTask(String taskId) {

		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();

		ProcessInstance instance = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();

		String business_Key = instance.getBusinessKey();

		String id = "";

		if (business_Key != null) {
			id = business_Key.split("\\.")[1];
		}

		Leavebill leave = this.leaveBillMapper.selectByPrimaryKey(Long.parseLong(id));
		return leave;
	}

	public List<Comment> findCommentListByTask(String taskId) {
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();

		List<Comment> list = this.taskService.getProcessInstanceComments(task.getProcessInstanceId());
		return list;
	}

	public void saveTaskSubmit(long id, String taskId, String comment, String username) {
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		// 使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用： String userId =
		 * Authentication.getAuthenticatedUserId(); CommentEntity comment = new
		 * CommentEntity(); comment.setUserId(userId);
		 * 所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，
		 * 不过不添加审核人，该字段为null
		 * 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 */
		// 加当前任务的审核人
		Authentication.setAuthenticatedUserId(username);
		// 添加批注
		taskService.addComment(taskId, processInstanceId, comment);
		taskService.complete(taskId);

		// 获取流程实例
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		
		if (pi == null) { // 流程结束
			Leavebill leave = leaveBillMapper.selectByPrimaryKey(id);
			// 设置业务的状态：审批结束 (2)
			leave.setState(2);
			leaveBillMapper.updateByPrimaryKey(leave);
		}
	}

}
