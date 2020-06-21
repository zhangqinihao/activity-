package com.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.pojo.Employee;
import com.shop.service.EmployeeService;
import com.shop.utils.Constants;
@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	
	// 登录功能实现
	@RequestMapping(value="/login")
	public String login(String username,
			String password,
			HttpSession session,
			Model model)
	{
		
		// 根据用户名去员工表中查询员工信息
		Employee employee = this.employeeService.findEmployeeByName(username);
		if(employee!=null)
		{
			// 再使用查询到的员工信息中的pwd 和用户传入的pwd进行比较
			if(employee.getPassword().equals(password))
			{
				// 此处说明用户名和密码一致，将用户信息保存在session域中
				session.setAttribute(Constants.GLOBLE_USER_SESSION, employee);
				// 跳转到index  首页
				return "index";
			}
			else
			{
				model.addAttribute("errorMsg", "账号或密码错误");
				return "login";
			}
			
		}
		else
		{
			model.addAttribute("errorMsg", "账号或密码错误");
			return "login";	
		}
	}
	
	@RequestMapping(value="/logout")
	// 注销功能
	public String logout(HttpSession session)
	{
		// 清空session
		session.invalidate();
		
		return "redirect:login.jsp";
		
	}
	
	
}
