package com.cn.project.AddWork;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class CopyClass {
	public static List<Map<String, Method>> map;

	/**
	 * 先初始化他要不是错的...
	 */
	public static void getCellstyleMap() {
		Method[] formMe = XSSFCellStyle.class.getMethods();
		Map<String, List<Method>> map = Stream.of(formMe).collect(Collectors.groupingBy(o -> {
			if (o.getName().startsWith("get")) {
				return "get";
			} else if (o.getName().startsWith("set")) {
				return "set";
			} else {
				return "other";
			}
		}));
		CopyClass.map = findGetSet(map);
	}

	public static <T> void copy(T from, T to) {
		
		List<Map<String, Method>> list = CopyClass.map;
		for (Map<String, Method> map2 : list) {
			try {
				Object obj = map2.get("get").invoke(from);
				map2.get("set").invoke(to, obj); 
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<Map<String, Method>> findGetSet(Map<String, List<Method>> map) {
		List<Method> get = map.get("get");
		List<Method> set = map.get("set");
		List<Map<String, Method>> list = new ArrayList<>();
		for (Method method : set) {
			String name = method.getName();
			name = name.substring(3, name.length());
			if (method.getParameterTypes().length != 1)
				break;
			for (Method getMe : get) {
				String gName = getMe.getName();
				gName = gName.substring(3, gName.length());
				if (gName.equals(name) && getMe.getReturnType() == method.getParameterTypes()[0]) {
					Map<String, Method> met = new HashMap<>();
					met.put("get", getMe);
					met.put("set", method);
					list.add(met);
				}
			}
		}
		return list;
	}
}
