package com.shop.service;

import com.shop.pojo.Employee;

public interface EmployeeService {
 
	/**
	 * 根据员工账号查找yuangng 
	 * @param name
	 * @return
	 */
	Employee findEmployeeByName(String name);
	
	
	/**
	 * 根据managrId 查找出某个员工的上司
	 * @param managerId
	 * @return
	 */
	Employee findEmployeeManager(long id);
	
}
