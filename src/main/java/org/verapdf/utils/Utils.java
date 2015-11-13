package org.verapdf.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static <T> List<T> getList(T value) {
		List<T> list = new ArrayList<>();
		list.add(value);
		return list;
	}
}