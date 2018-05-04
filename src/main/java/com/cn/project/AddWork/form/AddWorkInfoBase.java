package com.cn.project.AddWork.form;

import java.util.Date;

public class AddWorkInfoBase {
	protected Date writeYearDate;
	protected String writeYear;
	protected String name;
	protected String co;
	protected String work;
	protected int type;
	protected String resion;
	protected Date startDate;
	protected String start;
	protected Date endDate;
	protected String end;
	protected int day;
	protected int hour;
	private String msg = "备注:1、时间格式：24小时制；2、加班核定好之后转送人事部门备案。";

	public Date getWriteYearDate() {
		return writeYearDate;
	}

	public void setWriteYearDate(Date writeYearDate) {
		this.writeYearDate = writeYearDate;
	}

	public String getWriteYear() {
		return writeYear;
	}

	public void setWriteYear(String writeYear) {
		this.writeYear = writeYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getResion() {
		return resion;
	}

	public void setResion(String resion) {
		this.resion = resion;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
