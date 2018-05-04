package com.cn.project.AddWork.form;

import java.util.Date;

public class DefaultInfo extends AddWorkInfoBase {
	public DefaultInfo() {
		this.writeYearDate = new Date();
		this.name = "史恭文";
		this.co = "研发";
		this.work = "java";
		this.type = 1;
		this.resion = "晚上加班";
		// 结束的时间就是运行的时间
		this.endDate = new Date();
	}
}
