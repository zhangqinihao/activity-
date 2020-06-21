package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.LeavebillMapper;
import com.shop.pojo.Leavebill;

@Service
public class LeaveBillServiceImpl implements LeaveBillService {
	@Autowired
	private LeavebillMapper leaveBillMapper;

	public void saveLeaveBill(Leavebill leave) {

		if (leave.getId() != null) {
			this.leaveBillMapper.updateByPrimaryKey(leave);
		} else {
			this.leaveBillMapper.insert(leave);
		}

	}

}
