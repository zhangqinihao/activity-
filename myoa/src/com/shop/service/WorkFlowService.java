package com.shop.service;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.shop.pojo.Leavebill;

public interface WorkFlowService {
	// 部署流程
	void saveNewDeploy(InputStream in, String filename);

	// 根据姓名查询某个员工下的所有待办事务
	public List<Task> findTaskListByName(String name);
	// 保存并启动流程实例
	 public void saveStartProcess(String username);

	//void saveStartProcess2(Long leavId, String name);

	Leavebill findLeaveBillByTask(String taskId);
	
	List<Comment> findCommentListByTask(String taskId);

	void saveTaskSubmit(long id, String taskId, String comment, String username);
}
