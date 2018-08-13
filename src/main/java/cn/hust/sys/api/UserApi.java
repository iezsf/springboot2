package cn.hust.sys.api;

import cn.hust.core.ApiController;
import cn.hust.core.BaseController;
import cn.hust.core.BaseService;
import cn.hust.sys.bean.Menu;
import cn.hust.sys.bean.Role;
import cn.hust.sys.bean.User;
import cn.hust.sys.service.RoleService;
import cn.hust.sys.service.UserService;
import cn.hust.sys.vo.LoginVo;
import cn.hust.utils.ReturnFormat;
import cn.hust.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;

import cn.hust.utils.JWTUtil;
import cn.hust.utils.Md5Util;
 
@RestController
@RequestMapping(value = "/api/user")
public class UserApi  extends ApiController<User>{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    
    @RequestMapping("/login")
    public String login(HttpServletRequest request)
    {
    	String userName = (String) request.getParameter("username");
    	String password = (String) request.getParameter("password");
    	password = Md5Util.password(password);
    	User user = userService.login(userName, password);
    	if(user==null)
    	{
    		return ReturnFormat.error("账号不存在或密码错误!");
    	}
    	String salt = user.getSalt();
		LoginVo vo = new LoginVo();
		vo.setAccount(user.getAccount());
		vo.setNickname(user.getNickname());
		vo.setAccess_token(JWTUtil.sign(userName, password)); 
		System.out.println("登录成功，生成的token为: "+ vo.getAccess_token());
		return ReturnFormat.success("登录成功",vo);
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request)
    {
		HttpSession session = request.getSession();
		session.removeAttribute("user"); 
		return ReturnFormat.success("退出成功 ~~"); 
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
		BaseService<User> bs = this.getService();
		int total = bs.pageCount(where);
		this.order(where, request);
		List<User> nlist = bs.pageSelect(where);
		return returnList(total, nlist);
	}

	//用来在页面上显示用户的基本信息
	@RequestMapping(value="/showUser")
	@ResponseBody
	public String showUser(HttpServletRequest request) {
		String access_token = request.getParameter("access_token");

		String  username = JWTUtil.getUsername(access_token);
		LoginVo vo = new LoginVo();
		vo.setAccount(username);
		vo.setNickname(username);
		vo.setAccess_token(access_token); 
		
		return ReturnFormat.retParam("获取信息成功", "0", "0", "showUser", 1, vo);
	}

	@Override
	public BaseService<User> getService() {
		// TODO Auto-generated method stub
		return userService;
	}
	
	//用来在页面上显示用户的基本信息
	@RequestMapping(value="/insert")
	@ResponseBody
	@Override
	public String insert(@Valid User bean, BindingResult result,HttpServletRequest request) {
			
		String account = request.getParameter("account");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String roleId = request.getParameter("role");
		String password = Md5Util.md5("123456");
		User u = new User();
		u.setAccount(account);
		u.setNickname(nickname);
		u.setMobile(mobile);
		u.setEmail(email);
		u.setRoleId(Integer.parseInt(roleId));
		u.setStatus(1);
		u.setPassword(password);
		u.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		u.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		userService.insert(u);
		return ReturnFormat.success("添加成功 "); 
	}
	
	@RequestMapping(value="/checkexistname")
	@ResponseBody
	public String checkExistName(HttpServletRequest request) {
		String username = request.getParameter("username");
		
		User u = userService.findbyname(username);
		if(u==null)
		{
			return ReturnFormat.success("用户名可用~~"); 
		}else {
			return ReturnFormat.error("已重名，请再选择一个新的用户名"); 
		}
	}
	
	@Override
	protected boolean before_insert(User user, HttpServletRequest request) 	{
		String role = request.getParameter("role");
		if (StringUtils.isBlank(role))
			return true;
		List<Role> roles = new ArrayList<Role>();
		String[] roleArr = role.split(",");
		for (String rid : roleArr) {
			Role r = roleService.find(Integer.parseInt(rid));
			if (r != null)
				roles.add(r);
		}
		user.setRoles(roles);
		return true;
	}
	
	@Override
	protected boolean before_update(User user, HttpServletRequest request) 	{
		return before_insert(user, request);
	}
}
