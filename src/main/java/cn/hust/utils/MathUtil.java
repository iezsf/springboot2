package cn.hust.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

//对浮点型数据进行计算
public class MathUtil {
	
	//除法运算
	public static String div(String str1, String str2) {
		if(str1 != null && str2 != null && 
				!str1.equals("") && !str2.equals("") && !str2.equals("0")) {
			BigDecimal b1 = new BigDecimal(str1);
			BigDecimal b2 = new BigDecimal(str2);
			BigDecimal b3 = b1.divide(b2, 4, RoundingMode.HALF_UP);
			return b3.toString();
		}
		return "0";
	}
	
	//加法运算
	public static String add(String str1, String str2) {
		if(str1 != null && str2 != null && 
				!str1.equals("") && !str2.equals("") && !str2.equals("0")) {
			BigDecimal b1 = new BigDecimal(str1);
			BigDecimal b2 = new BigDecimal(str2);
			BigDecimal b3 = b1.add(b2);
			return b3.toString();
		}
		return "0";
	}
	
	//减法运算
	public static String sub(String str1, String str2) {
		if(str1 != null && str2 != null && 
				!str1.equals("") && !str2.equals("") && !str2.equals("0")) {
			BigDecimal b1 = new BigDecimal(str1);
			BigDecimal b2 = new BigDecimal(str2);
			BigDecimal b3 = b1.subtract(b2);
			return b3.toString();
		}
		return "0";
	}
	
	//乘法运算
	public static String mul(String str1 , String str2) {
		if(str1 != null && str2 != null 
				&& !str1.equals("") && !str2.equals("") && !str2.equals("0")) {
			BigDecimal b1 = new BigDecimal(str1);
			BigDecimal b2 = new BigDecimal(str2);
			BigDecimal b3 = b1.multiply(b2);
			return b3.toString();
		}
		return "0";
	}
}
