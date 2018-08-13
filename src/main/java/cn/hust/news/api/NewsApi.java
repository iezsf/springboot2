package cn.hust.news.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hust.core.ApiController;
import cn.hust.core.BaseService;
import cn.hust.news.bean.News;
import cn.hust.news.service.NewsService;
import cn.hust.utils.ReturnFormat;

@RestController
@RequestMapping(value = "/api")
public class NewsApi extends ApiController<News>{
	@Autowired
	public NewsService newsService;

	@Override
	public BaseService<News> getService() {
		// TODO Auto-generated method stub
		return  newsService;
	}
	
	@RequestMapping("/news/new/list")
	@ResponseBody
	public String new_list(	HttpServletRequest request)
	{
		Map<String,Object> where = this.where(request);
		String typename = request.getParameter("typename");
		if(typename!=null && !typename.equals(""))
		{
			where.put("type", typename);
		}
		this.page(where, request);
		BaseService<News> bs = this.getService();
		int total = bs.pageCount(where);
		this.order(where, request);
		List<News> nlist = bs.pageSelect(where);
		
		//List<News> nlist = newsService.findbytype(typename);
		return returnList(total, nlist);
		
	}

	@RequestMapping("/news/new/nomarklist")
	@ResponseBody
	public String nomark_list(	HttpServletRequest request)
	{
		Map<String,Object> where = this.where(request);
		String typename = request.getParameter("typename");
		if(typename!=null && !typename.equals(""))
		{
			where.put("type", typename);
		}
		where.put("lable_no_mark", 1);
		this.page(where, request);
		BaseService<News> bs = this.getService();
		int total = bs.pageCount(where);
		this.order(where, request);
		List<News> nlist = bs.pageSelect(where);
		
		//List<News> nlist = newsService.findbytype(typename);
		
		
		return returnList(total, nlist);
		
	}


	@RequestMapping("/news/new/markedlist")
	@ResponseBody
	public String marked_list(	HttpServletRequest request)
	{
		Map<String,Object> where = this.where(request);
		String typename = request.getParameter("typename");
		if(typename!=null && !typename.equals(""))
		{
			where.put("type", typename); 
		}
		where.put("lable_marked", 1);
		this.page(where, request);
		BaseService<News> bs = this.getService();
		int total = bs.pageCount(where);
		this.order(where, request);
		List<News> nlist = bs.pageSelect(where);
		
		//List<News> nlist = newsService.findbytype(typename);
		
		
		return returnList(total, nlist);
		
	}
	
	
	@RequestMapping("/news/domark")
	@ResponseBody
	public String domark(HttpServletRequest request)
	{
		 
		String article_id = request.getParameter("article_id");
		String article_lable = request.getParameter("article_lable");
		News n = newsService.find(Integer.parseInt(article_id));
		int pv = n.getPv();
		n.setLable(article_lable);
		n.setIschange(1);
		n.setPv(pv+1);
		
		newsService.update(n);
		
		System.out.println("domark"+article_id+ article_lable);
		return ReturnFormat.success("登录成功");
		
	}
}
