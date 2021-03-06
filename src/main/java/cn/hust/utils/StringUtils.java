package cn.hust.utils;

import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 为空
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str);
	}

	/**
	 * 不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	/**
	 * 转大写
	 * @param instr
	 * @return
	 */
    public static String toUpperCase(String instr) {
        return instr == null ? instr : instr.toUpperCase();
    }
    
    /**
     * 转小写
     * @param instr
     * @return
     */
    public static String toLowerCase(String instr) {
        return instr == null ? instr : instr.toLowerCase();
    }
    

	/**
	 * 首字母大写 ,其余不变
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirst(String str) {
		if (str == null)
			return null;
		if (str.length() == 0)
			return str;
		String pre = String.valueOf(str.charAt(0));
		return str.replaceFirst(pre, pre.toUpperCase());
	}
	
	/**
	 * 首字母小写 ,其余不变
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFirst(String str) {
		if (str == null)
			return null;
		if (str.length() == 0)
			return str;
		String pre = String.valueOf(str.charAt(0));
		return str.replaceFirst(pre, pre.toLowerCase());
	}
	
	/**
	 * 不会抛NullPointerException 的trim() 
	 * 传入null会返回null
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}
	
	/**
	 * 过滤 ;当instr==null时返回长度为0的""; 
	 * 与 nvl(...)系的区别在于只处理null ,不处理长度为0的"";
	 * @param instr
	 * @return
	 */
	public static String nvl(String instr) {
		return nvl(instr, "");
	}

	/**

	 * 过滤 ,把null和长度为0的""当成同一种情况处理; <br>

	 * 当instr==null||"".equals(instr)时返回defaultValue ;其它情况返回 instr

	 * 

	 * @param instr

	 * @param defaultValue

	 * @return

	 */
	public static String nvl(String instr, String defaultValue) {
		return instr == null || "".equals(instr) ? defaultValue : instr;
	}
	
	/**
	 * 比较 str1 和 str2 如果都是 null 或者 str1.equals(str2) 返回 true 表示一样 ;
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 != null && str1.equals(str2))
			return true;
		return false;
	}

	public static String apadLeft(double a, int b, int len) {
		return apadLeft(String.valueOf(a), String.valueOf(b), len);
	}

	public static String apadRight(double a, int b, int len) {
		return apadRight(String.valueOf(a), String.valueOf(b), len);
	}

	public static String apadLeft(String str, String str2, int len) {
		if (str == null || str.length() == len || str2 == null)
			return str;
		if (str.length() > len)
			return str.substring(str.length() - len, len);
		return apadpro(str, str2, len, true);
	}

	public static String apadRight(String str, String str2, int len) {
		if (str == null || str.length() == len || str2 == null)
			return str;
		if (str.length() > len)
			return str.substring(0, len);
		return apadpro(str, str2, len, false);
	}

	private static String apadpro(String a, String b, int len, boolean appendleft) {
		int f = len - a.length();
		for (int i = 0; i < f; i++) {
			a = appendleft == true ? b + a : a + b;
		}
		return a;
	}



	/**

	 * 清除字符串中所有的空格 ,传入null返回null

	 * 

	 * @author wangp

	 * @since 2009.02.06

	 * @param str

	 * @return

	 */
	public static String clear(String str) {
		return clear(str, " ");
	}

	/**

	 * 清除str中出现的所有str2字符序列 直到结果中再也找不出str2为止 str2 == null时 返回str

	 * 

	 * @author wangp

	 * @since 2009.02.06

	 * @param str

	 *            原始字符串

	 * @param str2

	 *            清除的目标

	 * @return

	 */
	public static String clear(String str, String str2) {
		if (str == null)
			return str;
		if (str2 == null)
			return str;
		String reg = "(" + str2 + ")+";
		Pattern p = Pattern.compile(reg);
		while (p.matcher(str).find()) {
			str = str.replaceAll(reg, "");
		}
		return str;
	}

	/**

	 * 如果str的长度超过了c则取c-sub.length长度,然后拼上sub结尾

	 * 

	 * @author wangp

	 * @since 2009.06.11

	 * @param str

	 * @param c

	 * @param sub

	 * @return

	 */
	public static String suojin(String str, int c, String sub) {
		if (isBlank(str))
			return str;
		if (str.length() <= c)
			return str;
		sub = nvl(sub);
		c = c - sub.length();
		c = c > str.length() ? 0 : c;
		str = str.substring(0, c);
		return str + sub;
	}

	/**

	 * 如果str的长度超过了length,取前length位然后拼上...

	 * 

	 * @author yimian

	 * @since 2009.06.11

	 * @param str

	 * @param length

	 * @return

	 */
	public static String suojin(String str, int length) {
		return suojin(str, length, "…");
	}

	public static String replaceOnce(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String replace(String text, String searchString, String replacement, int max) {
		if (isBlank(text) || isBlank(searchString) || replacement == null || max == 0)
			return text;
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1)
			return text;
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = increase >= 0 ? increase : 0;
		increase *= max >= 0 ? max <= 64 ? max : 64 : 16;
		StringBuffer buf = new StringBuffer(text.length() + increase);
		do {
			if (end == -1)
				break;
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0)
				break;
			end = text.indexOf(searchString, start);
		} while (true);
		buf.append(text.substring(start));
		return buf.toString();
	}
	
	/**
	 * 把一个数组连接成字符串
	 * @param arrays Object 可以转换成字符串的数组
	 * @param split String 分隔符
	 * @return
	 */
	public String join(Object[] arrays, String split){
		StringBuilder sb = new StringBuilder();
		for(Object o: arrays){
			sb.append(split).append(o);
		}
		return sb.substring(split.length());
	}
	
	public static int intVal(String intStr, int defVal) {
		try {
			return Integer.parseInt(intStr);
		}catch(Exception e) {
			//e.printStackTrace();
			return defVal;
		}
	}
	
	/**
	 * 获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
	 * @param length
	 * @return
	 */
	public static String random(int length) {
	    //随机字符串的随机字符库
	    String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    StringBuffer sb = new StringBuffer();
	    int len = KeyString.length();
	    for (int i = 0; i < length; i++) {
	       sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
	    }
	    return sb.toString();
	}
	
	/**
	 * 字符串对象如果为空，返回默认值 
	 * @param valStr String 字符串
	 * @param defaluts String 默认值 
	 * @return String
	 */
	public static String defaultString(String valStr,String defaluts) {
		return isBlank(valStr) ? defaluts : valStr;
	}
}
