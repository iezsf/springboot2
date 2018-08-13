package cn.hust.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.ModelAndView;

import cn.hust.utils.StringUtils;

public class BaseController {

	/**
	 * 初始化页面参数
	 * @param mv
	 * @param action
	 */
	public void init(ModelAndView mv, String action, HttpServletRequest request) {
		
		
		String[] classNames = getClass().getName().split("\\.");
		String cname = classNames[4].substring(0, classNames[4].length()-10).toLowerCase();
		mv.addObject("_module", classNames[2]);
		mv.addObject("_controller", cname);
		mv.setViewName("/"+classNames[2]+"/"+cname+"/"+action);
		System.out.println(mv.getViewName());
		
		
	 
	}	
	/**
	 * 生成查询条件
	 * @return Map<String,Object>
	 */
	protected Map<String,Object> where(HttpServletRequest request) {
		return new HashMap<String,Object>();
	}
	
	/**
	 * 计算分页:_page_limit限制行数，_page_start开始行号
	 * @param map Map<String,Object> 查询条件
	 * @param request HttpServletRequest 请求对象
	 */
	protected void page(Map<String,Object> map, HttpServletRequest request) {
		int limit = StringUtils.intVal(request.getParameter("limit"), 12);
		int page = StringUtils.intVal(request.getParameter("page"), 1);
		
		map.put("_page_limit", limit);
		map.put("_page_start", (page-1)*limit);				
	}
	
	/**
	 * 计算排序:_order_field排序字段,_order_direct排序方向
	 * @param map Map<String,Object> 查询条件
	 * @param request HttpServletRequest 请求对象
	 */
	protected void order(Map<String,Object> map, HttpServletRequest request) {
		String field = "id";
		String direct = "DESC";
		String p = request.getParameter("orderField");
		if(p != null && "".equals(p)) field = p;
		
		p = request.getParameter("orderDirect");
		if(p != null && "".equals(p)) direct = p;
		
		map.put("_order_field", field);
		map.put("_order_direct", direct);
	}
	 
	
}
