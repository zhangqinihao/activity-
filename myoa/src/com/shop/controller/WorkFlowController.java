package com.shop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shop.pojo.Employee;
import com.shop.pojo.Leavebill;
import com.shop.service.LeaveBillService;
import com.shop.service.WorkFlowService;
import com.shop.utils.Constants;

@Controller
public class WorkFlowController {

	@Autowired
	private WorkFlowService workFlowService;

	@Autowired
	private LeaveBillService leaveService;

	@RequestMapping(value = "/deployProcess")
	public String deployProcess(MultipartFile fileName, String processName) {
		try {
			this.workFlowService.saveNewDeploy(fileName.getInputStream(), processName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "add_process";
	}

	@RequestMapping(value = "/taskList")
	public ModelAndView getTaskList(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		String name = ((Employee) session.getAttribute(Constants.GLOBLE_USER_SESSION)).getName();

		List<Task> taskList = this.workFlowService.findTaskListByName(name);
		mv.addObject("taskList", taskList);
		mv.setViewName("workflow_task");
		return mv;
	}

	@RequestMapping(value = "/saveStartLeave")
	public String saveStartLeave(Leavebill bill, HttpSession session) {
		bill.setLeavedate(new Date());
		bill.setState(0);
		Employee em = (Employee) session.getAttribute(Constants.GLOBLE_USER_SESSION);
		bill.setUserId(em.getId());
		this.leaveService.saveLeaveBill(bill);
		// 启动流程
		this.workFlowService.saveStartProcess(em.getName());
		return "redirect:/taskList";

	}
	
	
	/*@RequestMapping(value = "/saveStartLeave")
	public String saveStartLeave(Leavebill bill,
			HttpSession session) {
		bill.setLeavedate(new Date());
		bill.setState(0);
		Employee em = (Employee) session.getAttribute(Constants.GLOBLE_USER_SESSION);
		bill.setUserId(em.getId());
		this.leaveService.saveLeaveBill(bill);
		// 启动流程
		this.workFlowService.saveStartProcess2(bill.getId(),em.getName());
		return "redirect:/taskList";

	}*/
	
	@RequestMapping(value="/viewTaskForm")
	public ModelAndView viewTaskForm(String taskId)
	{
		// 根据taskId 查询出请假单的业务数据  LeaveBill对象
		
		Leavebill leavebill = this.workFlowService
		.findLeaveBillByTask(taskId);
		
		// 根据taskId 查询出List<Comment>
		List<Comment> list = this.workFlowService.findCommentListByTask(taskId);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("bill", leavebill);
		mv.addObject("commentList", list);
		mv.addObject("taskId", taskId);
		
		mv.setViewName("approve_leave");
		
		return mv;
	}

	@RequestMapping("/submitTask")
	public String submitTask(long id,String taskId,String comment,HttpSession session) {
		String username = ((Employee)session.getAttribute(Constants.GLOBLE_USER_SESSION)).getName();
		// 流程要往前推进  complete();
		// 加批注
		this.workFlowService.saveTaskSubmit(id,taskId,comment,username);
		return "redirect:/taskList";
		
	}
	
	
}
