package cn.hust.core;

 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hust.utils.ReturnFormat;
import cn.hust.utils.StringUtils;

public abstract class ApiController<T> extends BaseController {
		
	/**
	 * 新增数据
	 * @param bean T 数据
	 * @return
	 */
	@RequestMapping("insert")
	@ResponseBody
	public String insert(@Valid T bean, BindingResult result,HttpServletRequest request) {
		if(result.hasErrors()) {
			return ReturnFormat.error("您输入的数据有误:"+result.getFieldErrors().get(0).getDefaultMessage());
		}
		if(!before_insert(bean, request)) return ReturnFormat.error("数据处理失败1!");
		BaseService<T> bs = this.getService();
		if(bs.insert(bean) <= 0) return ReturnFormat.error("保存数据失败:"+bs.getError());
		if(after_insert(bean, request)){
			return ReturnFormat.success("保存数据成功!");
		}else {
			return ReturnFormat.error("数据处理失败2!");
		}
	}	
	protected boolean  before_insert(T bean, HttpServletRequest request) {
		return true;
	}
	protected boolean  after_insert(T bean, HttpServletRequest request) {
		return true;
	}
	
	/**
	 * 更新数据操作
	 * @param bean T数据
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public String update(@Valid T bean, BindingResult result, HttpServletRequest request) {
		if(!before_update(bean, request)) return ReturnFormat.success("数据处理失败1!");
		BaseService<T> bs = this.getService();
		if(bs.update(bean) == 0) return ReturnFormat.error("更新数据失败:"+bs.getError());
		if(after_update(bean, request)){
			return ReturnFormat.success("更新数据成功!");
		}else {
			return ReturnFormat.error("数据处理失败2!");
		}
	}
	protected boolean  before_update(T bean, HttpServletRequest request) {
		return true;
	}
	protected boolean  after_update(T bean, HttpServletRequest request) {
		return true;
	}
	
	/**
	 * 删除数据
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam("id") String id) {
		if(StringUtils.isBlank(id)) {
			return ReturnFormat.error("至少要选择一个删除对象");
		}
		BaseService<T> bs = this.getService();
		String[] ids = id.split(",");
		int count = 0;
		for(String idd:ids) {
			if(bs.delete(Integer.parseInt(idd)) < 1) {
				return ReturnFormat.error("删除数据失败:"+bs.getError());
			}
			count ++;
		}
		return ReturnFormat.success("成功删除"+count+"条数据!");
	}
	
	
	/**
	 * 禁用数据
	 */
	@RequestMapping("/disable")
	@ResponseBody
	public String disable(@RequestParam("id") String id) {
		if(StringUtils.isBlank(id)) {
			return ReturnFormat.error("至少要选择一个禁用对象");
		}
		BaseService<T> bs = this.getService();
		String[] ids = id.split(",");
		int count = 0;
		for(String idd:ids) {
			if(bs.disable(Integer.parseInt(idd)) < 1) {
				return ReturnFormat.error("禁用数据失败:"+bs.getError());
			}
			count ++;
		}
		return ReturnFormat.success("成功禁用"+count+"条数据!");
	}
	
	/**
	 * 启用数据
	 */
	@RequestMapping("/enable")
	@ResponseBody
	public String enable(@RequestParam("id") String id) {
		if(StringUtils.isBlank(id)) {
			return ReturnFormat.error("至少要选择一个启用对象");
		}
		
		BaseService<T> bs = this.getService();
		String[] ids = id.split(",");
		int count = 0;
		for(String idd:ids) {
			if(bs.enable(Integer.parseInt(idd)) < 1) {
				return ReturnFormat.error("启用数据失败:"+bs.getError());
			}
			count ++;
		}
		return ReturnFormat.success("成功启用"+count+"条数据!");
	}
		
	/**
	 * 查询数据
	 * @param bean T 查询条件
	 * @return
	 */
	@RequestMapping("/lists")
	@ResponseBody
	public String lists(HttpServletRequest request) {
		Map<String,Object> where = this.where(request);

		String title = request.getParameter("title");
		if(title != null && !title.equals("")) {
			where.put("title", title);
		}
		this.page(where, request);
		BaseService<T> bs = this.getService();
		int total = bs.pageCount(where);
		this.order(where, request);
		List<T> list = bs.pageSelect(where);
		return returnList(total, list);
	}
	
	/**
	 * 返回数据
	 * @param total
	 * @param list
	 * @return
	 */
	public String returnList(int total, List<T> list) {
		return ReturnFormat.success(total, list);
	}

	/**
	 * 简要查询
	 */
	@RequestMapping("/slist")
	@ResponseBody
	public String slist(Map<String,Object> map) {
		BaseService<T> bs = this.getService();
		List<T> list = bs.selectAll(map);
		return returnList(list.size(), list);
	}
	
	
	@RequestMapping("/listOne")
	@ResponseBody
	public String listOne(@RequestParam("id") String id) {
		BaseService<T> bs = this.getService();
		int int_id = Integer.parseInt(id);
		T t = bs.find(int_id);
		List<T> list = new ArrayList<T>();
		list.add(t);
		return returnList(list.size(), list);
	}
	/**
	 * 未授权响应
	 * @return
	 */
    @RequestMapping(value="/noauth")
	@ResponseBody
	public String noauth() {
		return ReturnFormat.error("未授权", "401");
	}
    
    
    abstract public BaseService<T> getService();
}