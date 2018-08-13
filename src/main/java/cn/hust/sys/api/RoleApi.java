package cn.hust.sys.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hust.core.ApiController;
import cn.hust.core.BaseService;
import cn.hust.sys.bean.Menu;
import cn.hust.sys.bean.Role;
import cn.hust.sys.bean.User;
import cn.hust.sys.service.MenuService;
import cn.hust.sys.service.RoleService;
import cn.hust.utils.ReturnFormat;

@RestController
@RequestMapping(value = "/api/sys/role")
public class RoleApi extends ApiController<Role>{
	@Autowired
	    private RoleService roleService;
	@Autowired 
		private MenuService menuService;
	
	@Override
	public BaseService<Role> getService() {
		// TODO Auto-generated method stub
		return roleService;
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
		this.page(where, request);
		BaseService<Role> bs = this.getService();
		int total = bs.pageCount(where);
		this.order(where, request);
		List<Role> nlist = bs.pageSelect(where);
		return returnList(total, nlist);
	}
	
	
	/**
	 * 新增一个角色， 同时为角色添加对应的菜单 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addrolemenu")
	@ResponseBody
	public String addMenu(HttpServletRequest request) {
		String title = request.getParameter("title");
		String remark = request.getParameter("remark");
		String menu_ids = request.getParameter("menu_ids");
		Role r = new Role();
		r.setRemark(remark);
		r.setTitle(title);
		r.setStatus('1');
		roleService.insert(r); 
		System.out.println(r.getId());
		roleService.addRoles(r.getId(), menu_ids);
		return ReturnFormat.success("保存数据成功!");
	}
	
	@RequestMapping("/selectrolemenu")
	@ResponseBody
	public String getRolesMenu(HttpServletRequest request) {
		String role_id = request.getParameter("role_id");
		if(role_id == null)
		{
			return ReturnFormat.error("获取角色ID失败，请稍候再试");
		}
		//List<> role_menu_ids = roleService.
		List<Menu> m_list = menuService.selectRoleMenu(Integer.parseInt(role_id));
		return ReturnFormat.success(m_list.size(),m_list);
	}
	
	@RequestMapping("/updaterole")
	@ResponseBody
	public String updateRole(HttpServletRequest request) {
		String role_id = request.getParameter("role_ids");
		if(role_id == null)
		{
			return ReturnFormat.error("获取角色ID失败，请稍候再试");
		}
		String remark = request.getParameter("remark");
		String menu_ids = request.getParameter("menu_ids");
		Role r = roleService.find(Integer.parseInt(role_id));
		r.setRemark(remark);
		roleService.update(r);
		roleService.updateRoles(Integer.parseInt(role_id),menu_ids);
		return ReturnFormat.success("操作成功");
	}
}
