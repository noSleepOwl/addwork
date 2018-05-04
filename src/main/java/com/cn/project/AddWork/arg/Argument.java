package com.cn.project.AddWork.arg;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import com.cn.project.AddWork.TempInfo;
import com.cn.project.AddWork.form.GerneryWork;
import com.cn.project.AddWork.form.WeekDayWork;

public class Argument {
	private static final String DEF_COMM = "DEFAULT";
	private TempInfo tem = new TempInfo();

	private String[] args;

	private String mainCommand;

	public Argument(String[] arg) throws ParseException {
		this.args = arg;
		mainCommand = args[0];
		init();
		runTemp();
	}

	private void runTemp() {

	}
	private void init() throws ParseException {
		mainCommand();
	}
	private void mainCommand() throws ParseException {
		if (Objects.nonNull(mainCommand)) {
			mainCommand = mainCommand.toUpperCase();
			switch (mainCommand) {
			case DEF_COMM:
				runDefault();
				break;
			default:
				break;
			}
		}
	}

	private void runDefault() throws ParseException {
		int we = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (we > 5) {
			WeekDayWork wee = new WeekDayWork();
			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
			wee.setStartDate(sdf.parse(args[2] + " " + args[3]));
			tem.run(args[1], wee);
		} else {
			tem.run(args[1], new GerneryWork());
		}
	}
}
