package cn.hust.utils;

import java.security.MessageDigest;

public class Md5Util {
	private static Md5Util instance = new Md5Util();

	public static String MD5_SALT = "48024573fc3c5a35651c255a5daa43e9";
	
	private Md5Util(){}
	
	public static Md5Util getInstance(){
		return instance;
	}
	
	/**
	 * Md5 加密
	 * 
	 * @param str String 被加密字符串
	 * @return String
	 */
	public static String md5(String str) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			byte[] md5Bytes = md5.digest();
			String res = "";
			for (int i = 0; i < md5Bytes.length; i++) {
				int temp = md5Bytes[i] & 0xFF;
				if (temp <= 0XF) { // 转化成十六进制不够两位，前面加零
					res += "0";
				}
				res += Integer.toHexString(temp);
			}
			return res;
		} catch (Exception e) {
			return "";
		}

	}

	public static  String password(String pass) {
		return md5(pass + MD5_SALT);
	}
}
