package cn.hust.utils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 格式化返回客户端数据格式（json）
 * @author yepanpan
 *
 */
public class ReturnFormat {
	private String msg;
	//为了适应layui的状态位判定，添加code字段
	private String code;
	

	private String error;
	private String url;
	private int count;
	private Object data;
	
	public ReturnFormat(){}

	public ReturnFormat(String msg, Object data) {		
		this(msg, "0","0", "", data);
	}
	
	public ReturnFormat(String msg, String error) {		
		this(msg, error, error, "", null);
	}
	
	public ReturnFormat(String msg, String error, String code ,String url, Object data) {
		super();
		this.msg = msg;
		this.error = error;
		this.code=code;
		this.url = url;
		this.count = 0;
		if(data != null ) {
			this.data = data;
			this.count = 1;
		}
	}
	public ReturnFormat(String msg, String error,String code ,String url, int count, Object data) {
		super();
		this.msg = msg;
		this.error = error;
		this.code=code;
		this.url = url;
		this.count = count;
		this.data = data;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getcount() {
		return this.count;
	}
	public void setcount(int count) {
		this.count = count;
	}
	
	public String toString() {
		return "";
	}
		
	public static String retParam(String msg, String error, String code,String url, int count, Object data)  {
		try {
			ReturnFormat object = new ReturnFormat(msg, error,code, url, count, data);
			return new ObjectMapper().writeValueAsString(object);
		}catch(JsonProcessingException e) {
			e.printStackTrace();
			return "{error:1:msg:\"生成JSON数据失败！\"}";
		}		
	}
	
	public static String formatData(Object object)  {
		try {
			return new ObjectMapper().writeValueAsString(object);
		}catch(JsonProcessingException e) {
			return "{error:1:msg:\"生成JSON数据失败！\"}";
		}		
	}

	public static String error(String msg, String error, String url)  {
		return retParam(msg, error,error, url, 0, null);
	}

	public static String error(String msg, String error)  {
		return retParam(msg, error, error,"", 0, null);
	}

	public static String error(String msg)  {
		return retParam(msg, "1","1", "", 0, null);
	}
	
	public static String success(String msg)  {
		return retParam(msg, "0","0", "", 0, null);
	}
	public static String success(String msg, String url)  {
		return retParam(msg, "0","0", url, 0, null);
	}
	public static String success(String msg, Object data)  {
		return retParam(msg, "0", "0","", 0, data);
	}	
	
	public static String success(int count, List list)  {
		return retParam("查询结果!", "0","0", "", count, list);
	}
	
}
