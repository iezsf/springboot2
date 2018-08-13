package cn.hust.sys.controller;

import java.io.IOException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import cn.hust.core.BaseController;
import cn.hust.news.bean.News;
import cn.hust.news.service.NewsService;

@Controller
public class IndexController extends BaseController {

	@Autowired
	private NewsService newsService;

	@GetMapping("/")
	String index(HttpServletRequest rq) {
		return "/start/index.html";
	}

	@GetMapping("/news/index")
	String news(HttpServletRequest rq) {
		return "/src/views/news/index";
	}
	@GetMapping("/user/login")
	String login(HttpServletRequest rq) {
		return "/src/views/user/login";
	}
	@GetMapping("/news/mark")
	String mark(HttpServletRequest rq) throws IOException {
		String id = rq.getParameter("id");
		System.out.println(id);
		News news = newsService.find(Integer.parseInt(id));
		rq.setAttribute("news", news);
		return "/src/views/news/mark";
	}
}
