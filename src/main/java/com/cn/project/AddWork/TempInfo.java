package com.cn.project.AddWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cn.project.AddWork.form.AddWorkInfoBase;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class TempInfo {
	static {
		CopyClass.getCellstyleMap();
	}

	/**
	 * 模板地址
	 */
	public static final InputStream path = TempInfo.class.getClassLoader().getResourceAsStream("addWorkTemp.xlsx");
	public static final String EXCEL_SUFFIX = ".xlsx";
	public static final String CURRENT_WROKBOOK_NAME = String.format("%d年_统计表格%s", Calendar.getInstance().getWeekYear(),
			EXCEL_SUFFIX);
	public static final String CURRENT_SHEET_NAME = String.format("%d月",
			Calendar.getInstance().get(Calendar.MONTH) + 1);
	public static final String logName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + EXCEL_SUFFIX;
	private XSSFWorkbook result;
	private XSSFWorkbook exisitBook;
	private String outDir;
	private File outFile;
	private AddWorkInfoBase param;
	private Map<String, AddWorkInfoBase> paramMap = new HashMap<>();
	private boolean outFileIsNew = false;
	private SimpleDateFormat formatWrite = new SimpleDateFormat("yyyy年MM月dd日");
	private SimpleDateFormat startEnd = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
	private static SimpleDateFormat logTime = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

	public static void log(Object obj) {
		System.out.println(String.format("%-28s%s", logTime.format(new Date()), obj));
	}

	/**
	 * 输出当前的时间节点
	 * 
	 * @param hour
	 * @param param
	 */
	private void printTimeLog(int hour, AddWorkInfoBase param) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(param.getStartDate());
		System.out.print("\t");
		for (int i = 0; i <= hour; i++) {
			String end = i == hour ?"":"------->";
			System.out.printf("|%s|%s", logTime.format(ca.getTime()),end);
			ca.add(Calendar.HOUR, 1);
		}
		System.out.println();
	}

	private boolean isNotNull(String tar) {
		return tar != null && tar != ""; 
	}

	public void run(String outDir, AddWorkInfoBase param) {
		this.outDir = outDir;
		this.param = param;
		parseParamToMap();
		try {
			dirPars();
			if (!error()) {
				return;
			}
			createWorkBook();
			getExisitWorkBook();
			writeResultToExisit();
			singleList();
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean error() {
		if (param == null) {
			log("表单信息未填写");
			return false;
		}
		if (param.getHour() < 0) {
			log("加班开始时间,和结束时间设置不正确加班时间不可能为" + param.getHour() + "小时");
			return false;
		}
		return true;
	}

	/**
	 * 单个表格生成
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void singleList() throws FileNotFoundException, IOException {
		if (isNotNull(outDir)) {
			File file = Paths.get(outDir).resolve("log").toFile();
			if (!file.exists()) {
				file.mkdirs();
			}
			file = file.toPath().resolve(logName).toFile();
			result.write(new FileOutputStream(file));
			log("新加班单生成:" + file.getAbsolutePath());
		}
	}

	private boolean parseParamToMap() {
		if (param != null) {

			try {
				param.setStart(startEnd.format(param.getStartDate()));
				log("加班开始时间:" + param.getStart());
				param.setEnd(startEnd.format(param.getEndDate()));
				log("加班结束时间:" + param.getEnd());
				param.setWriteYear(formatWrite.format(param.getWriteYearDate()));
				log("填表时间:" + param.getWriteYear());

				// 小时
				int hour = (int) ((param.getEndDate().getTime() - param.getStartDate().getTime()) / (1000 * 60 * 60));
				// 天
				int res = hour / 8; // 八小时一天大于八小时就加一
				log(String.format("总计:%d天%d小时", res, hour));

				param.setDay(res);
				param.setHour(hour);
				paramMap.put("info", param);
				
				printTimeLog(hour, param);
			} catch (Exception e) {
				log("表单属性有错误" + e.getMessage());
			}
			return true;
		} else {
			return false;
		}

	}

	private void createWorkBook() throws ParsePropertyException, InvalidFormatException, IOException {
		XLSTransformer xlsTransformer = new XLSTransformer();
		result = new XSSFWorkbook(path);
		xlsTransformer.transformWorkbook(result, paramMap);
		log("新的加班单,生成完毕");
	}

	private File dirPars() throws InvalidFormatException, IOException {
		if (isNotNull(outDir)) {
			File file = new File(outDir);
			boolean exisits = file.exists();
			if (!exisits && file.isDirectory()) {
				file.mkdirs();
			}
			file = file.toPath().resolve(CURRENT_WROKBOOK_NAME).toFile();
			if (!file.exists()) {
				log(CURRENT_WROKBOOK_NAME + "创建完成!!新的一年GoodLuck");
				file.createNewFile();
				outFileIsNew = true;
			}
			outFile = file;
		}
		return null;
	}

	private void getExisitWorkBook() throws InvalidFormatException, IOException {
		if (outFile != null && !outFileIsNew) {
			this.exisitBook = new XSSFWorkbook(new FileInputStream(outFile));
			log(CURRENT_WROKBOOK_NAME + "表格读取成功!!");
		} else {
			log(CURRENT_WROKBOOK_NAME + "Excel实体类创建完成");
			this.exisitBook = new XSSFWorkbook(); 
		}
	}

	private void writeResultToExisit() throws FileNotFoundException, IOException {
		XSSFSheet sheet = exisitBook.getSheet(CURRENT_SHEET_NAME);
		if (sheet == null) {
			sheet = exisitBook.createSheet(CURRENT_SHEET_NAME);
			log(CURRENT_SHEET_NAME + "工作簿已创建!!新的一个月GoodLuck");
		}
		XSSFSheet resSheet = result.getSheet("s1");
		int resFrist = resSheet.getFirstRowNum();
		int resLast = resSheet.getLastRowNum();
		
		int last = sheet.getLastRowNum() == 0 ? sheet.getLastRowNum() : sheet.getLastRowNum() + 2;
		
		POIUtils.mergeSheetAllRegion(resSheet, sheet, last);
		for (int i = last, j = resFrist; j < resLast; i++, j++) {
			XSSFRow row = sheet.createRow(i);
			XSSFRow resRow = resSheet.getRow(j);
			POIUtils.copyRow(exisitBook, resRow, row);
		}
		log("新的加班单,汇总到" + CURRENT_WROKBOOK_NAME + "的" + CURRENT_SHEET_NAME + "工作簿内成功");
		exisitBook.write(new FileOutputStream(outFile));
		log("汇总表格输出成功!!");
	}

}
