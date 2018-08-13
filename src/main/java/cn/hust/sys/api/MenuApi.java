package cn.hust.sys.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hust.core.ApiController;
import cn.hust.core.BaseService;
import cn.hust.news.bean.News;
import cn.hust.sys.bean.Menu;
import cn.hust.sys.bean.User;
import cn.hust.sys.service.MenuService;
import cn.hust.sys.service.RoleService;
import cn.hust.sys.service.UserService;
import cn.hust.utils.JWTUtil;
import cn.hust.utils.ReturnFormat;
import cn.hust.utils.StringUtils;

@RestController
@RequestMapping(value="/api/sys/menu")
public class MenuApi extends ApiController<Menu> {
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	
	@Override
	public BaseService<Menu> getService() {
		// TODO Auto-generated method stub
		return menuService;
	}
	/**
	 * 生成查询条件
	 * @return Map<String,Object>
	 */
	@Override
	protected Map<String,Object> where(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		String p = request.getParameter("appId");
		if(StringUtils.isNotBlank(p)) map.put("appId", StringUtils.intVal(p, 0));
		p = request.getParameter("pid");
		if(StringUtils.isNotBlank(p)) map.put("pid", StringUtils.intVal(p, 0));
		
		p = request.getParameter("title");
		if(StringUtils.isNotBlank(p)) map.put("title", p);
		return map;
	}

	
	@RequestMapping("/navlist")
	public String navlist(HttpServletRequest request, HttpServletResponse response)
	{
		
		String access_token = request.getParameter("access_token");
		User user = userService.findbyname(JWTUtil.getUsername(access_token));
		
		
		List<Menu> list = menuService.findByGrant(user.getRoleId());
		String total  = menuService.getIds(user.getRoleId());
		
		return ReturnFormat.success(total, list);
	}


	/**
	 * 查询数据
	 * @param bean T 查询条件
	 * @return
	 */
	@RequestMapping("/lists")
	@ResponseBody
	@Override
	public String lists(HttpServletRequest request) {
		Map<String,Object> where = this.where(request);
		where.put("status", '1');
		String pid = request.getParameter("pid");
		if(pid!=null && !pid.equals(""))
		{
			where.put("pid", pid);
		}
		
		this.page(where, request);
		BaseService<Menu> bs = this.getService();
		int total = bs.pageCount(where);
		this.order(where, request);
		List<Menu> nlist = bs.pageSelect(where);
		return returnList(total, nlist);
	}


	@RequestMapping("/list")
	@ResponseBody
	public String menu_list(HttpServletRequest request)
	{
		Map<String,Object> where = this.where(request);
		where.put("status", '1');
		String pid = request.getParameter("pid");
		if(pid!=null && !pid.equals(""))
		{
			where.put("pid", pid);
		}
		
		this.page(where, request);
		BaseService<Menu> bs = this.getService();
		int total = bs.pageCount(where);
		this.order(where, request);
		List<Menu> nlist = bs.pageSelect(where);
		return returnList(total, nlist);
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(@Valid Menu bean, BindingResult result,HttpServletRequest request) {
		if(result.hasErrors()) {
			return ReturnFormat.error("您输入的数据有误:"+result.getFieldErrors().get(0).getDefaultMessage());
		}
		if(!before_insert(bean, request)) return ReturnFormat.error("数据处理失败1!");
		BaseService<Menu> bs = this.getService();
		if(bs.insert(bean) <= 0) return ReturnFormat.error("保存数据失败:"+bs.getError());
		if(after_insert(bean, request)){
			return ReturnFormat.success("保存数据成功!");
		}else {
			return ReturnFormat.error("数据处理失败2!");
		}
	}	
	
} 