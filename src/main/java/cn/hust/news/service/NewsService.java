package cn.hust.news.service;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.hust.core.BaseMapper;
import cn.hust.core.BaseService;
import cn.hust.news.bean.News;
import cn.hust.news.mapper.NewsMapper;


@Service
public class NewsService extends BaseService<News> {

	@Resource
	private NewsMapper newsMapper;
	
	@Override
	public BaseMapper<News> getMapper() {
		// TODO Auto-generated method stub
		return newsMapper;
	}
}
