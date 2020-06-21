package com.shop.utils;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


/**
 * 
 * 
 * 一旦流程启动，会将任务往前推进！
 * 
 * 会自动触发该监听器 
 *  
 *     在该监听器中有一个代理对象会帮助我们设置 待办人！
 *     
 *     
 * 
 * 
 * @author newuser
 *
 */
public class TaskAssigneeHandler implements TaskListener{

	@Override
	public void notify(DelegateTask delegate) {
		
		
		// 很多很多逻辑
		
		
		// 分配待办人
		delegate.setAssignee("二丫");
		
		
		
		
	}
}
