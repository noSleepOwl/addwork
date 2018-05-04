package com.cn.project.AddWork.form;

import java.util.Calendar;

/**
 * 默认的平常的工作时间晚上加班
 * 
 * @author uigsw
 *
 */
public class GerneryWork extends DefaultInfo {
	public GerneryWork() {
		this.resion = "晚上加班";
		this.type = 1;
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.HOUR_OF_DAY, 18);
		ca.set(Calendar.MINUTE, 30);
		// 加班的时间从六点三十开始
		this.startDate = ca.getTime();
	}
}
