package com.cn.project.AddWork;

import java.util.Calendar;
import java.util.Date;

import com.cn.project.AddWork.form.DefaultInfo;
import com.cn.project.AddWork.form.GerneryWork;

/**
 * Hello world!
 *
 */
public class App {

	public static Date getDate(Integer month, Integer day, Integer hour, Integer minute) {
		Calendar startTime = Calendar.getInstance();
		if (month != null)
			startTime.set(Calendar.MONTH, (month - 1));
		if (day != null)
			startTime.set(Calendar.DAY_OF_MONTH, day);
		if (hour != null)
			startTime.set(Calendar.HOUR_OF_DAY, hour);
		if (minute != null)
			startTime.set(Calendar.MINUTE, minute);
		return startTime.getTime();
	}

	public static void main(String[] args) {
//		Date startDate = getDate(4,28,18,30);
//		Date endDate = getDate(4,28,20,30);

		TempInfo tem = new TempInfo();

		DefaultInfo ww = new GerneryWork();
//		 ww.setStartDate(startDate);
//		ww.setEndDate(endDate);
//		tem.run("C:\\Users\\uigsw\\Desktop\\测试数据", ww);
		 tem.run("E:\\addWorkLog", ww);
	}
}
