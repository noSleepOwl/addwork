package com.cn.project.AddWork.form;

import java.util.Calendar;

/**
 * 休息日加班
 * 
 * @author uigsw
 *
 */
public class WeekDayWork extends DefaultInfo {
	public WeekDayWork() {
		type = 2;
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.HOUR_OF_DAY, 9);
		ca.set(Calendar.MINUTE, 30);
		startDate = ca.getTime();
		resion = "周末加班";
	}
}
