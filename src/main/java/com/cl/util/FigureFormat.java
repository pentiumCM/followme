package com.cl.util;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * 两位数除法保留小数位数
 * @author single
 *
 */
public class FigureFormat {
	public static double doubleFormat(double a, double b) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		// 保留两位小数
		numberFormat.setMaximumIntegerDigits(2);
		numberFormat.setRoundingMode(RoundingMode.UP);
		// 保留两位小数返回double类型数据
		if (a == b) {
			return 100.00;
		} else {
			return Double.parseDouble(numberFormat.format(a / b * 100));
		}
	}
}
