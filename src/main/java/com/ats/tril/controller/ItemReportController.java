package com.ats.tril.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.tril.common.DateConvertor;
import com.ats.tril.model.report.MonthwiseAvgItemRate;
import com.ats.tril.repository.report.MonthwiseAvgItemRateRepo;

@RestController
public class ItemReportController {
	
	
	@Autowired MonthwiseAvgItemRateRepo getMonthwiseAvgItemRateRepo;
	
	
	@RequestMapping(value = { "/getMonthwiseAvgItemRate" }, method = RequestMethod.POST)
	public @ResponseBody List<MonthwiseAvgItemRate> getIssueHeaderList(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			@RequestParam("prevFromDate") String prevFromDate,
			@RequestParam("prevToDate") String prevToDate) {

		List<MonthwiseAvgItemRate> monthAvgItemRateList = new ArrayList<MonthwiseAvgItemRate>();

		try {

			monthAvgItemRateList = getMonthwiseAvgItemRateRepo.getMonthWiseAvgItemRate(DateConvertor.convertToYMD(fromDate),DateConvertor.convertToYMD(toDate),
					DateConvertor.convertToYMD(prevFromDate),DateConvertor.convertToYMD(prevToDate));
			 for(int i=0;i<monthAvgItemRateList.size();i++) {
				 if(monthAvgItemRateList.get(i).getItemId()==6) {
					 
					 System.err.println("monthAvgItemRateList.get(i) 6 " +i + "to " +monthAvgItemRateList.get(i).toString());
				 }
				 
if(monthAvgItemRateList.get(i).getItemId()==5) {
					 
					 System.err.println("monthAvgItemRateList.get(i) 5 sugar" +i + "to " +monthAvgItemRateList.get(i).toString());
				 }
			 }
			
		} catch (Exception e) {

			e.printStackTrace();
			 
		}
		return monthAvgItemRateList;

	}
	

}
