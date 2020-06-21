package com.shop.utils;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shop.pojo.Employee;
import com.shop.service.EmployeeService;

public class TaskAssigneeHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		/*
		 * // 调用业务类查询当前登录人的上级部门？ 调用EmployeeServiceImpl // 通过程序获取spring容器 //
		 * 当前登录人在HttpSession HttpServletRequest
		 *
		 * 
		 */

		// 调用业务类查询当前登录人的上级部门？ 调用EmployeeServiceImpl
		// 通过程序获取spring容器
		// 当前登录人在HttpSession HttpServletRequest
		// spring容器
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Employee employee = (Employee) request.getSession().getAttribute(Constants.GLOBLE_USER_SESSION);

		Employee manager = employeeService.findEmployeeManager(employee.getManagerId());

		delegateTask.setAssignee(manager.getName());

		System.out.println("监听器打印了没有啊。。。。。。。");

	}

}
