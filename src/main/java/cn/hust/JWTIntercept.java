package cn.hust;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.hust.sys.bean.User;
import cn.hust.sys.service.UserService;
import cn.hust.utils.JWTUtil;
import cn.hust.utils.ReturnFormat;
import cn.hust.utils.SendMsgUtil;

@Component
public class JWTIntercept implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 如果已经登录返回true。
		// 如果没有登录没有登录，可以使用 reponse.send() 跳转页面。后面要跟return false,否则无法结束;
		// response.sendRedirect("http://localhost:8081/#/user/login");
		// 为了测试，打印一句话
		System.out.println("进入了拦截器。");
		System.out.println("请求路径     " + request.getRequestURI());
		String access_token = request.getParameter("access_token");
		if (access_token == null) {
			response.sendRedirect("/#/user/login");
		} else {
			String username = JWTUtil.getUsername(access_token);
			User u = userService.findbyname(username);
			System.out.println("当前的token为: " +access_token);
			boolean check_date = true;
			//check_date  = JWTUtil.verify(access_token, username, u.getPassword()); 

			Map map = new HashMap();
			if (check_date)
			{

				map.put("access_token", "11111");
				map.put("code", "0");
			//	SendMsgUtil.sendJsonMessage(response, map);
				System.out.println("verify token ok ");
			}else{
				System.out.println("token 已经无效");

				map.put("code", 1001);
				SendMsgUtil.sendJsonMessage(response, map);
				response.sendRedirect("/#/user/login");
			}
			System.out.println(username);
			return true;
		}
		return false;

		/*
		 * HttpSession session = request.getSession(true);
		 * if(session.getAttribute("user") == null) {
		 * //response.sendRedirect(request.getContextPath()+"/#/user/login");
		 * 
		 * System.out.println(request.getContextPath()+"---/#/user/login");
		 * 
		 * return false; }else{ return true; }
		 * 
		 * //
		 */

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}