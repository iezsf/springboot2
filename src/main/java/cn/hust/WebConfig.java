package cn.hust;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.hust.JWTIntercept;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private JWTIntercept jwtIntercept;

	@Override
	public void addInterceptors(InterceptorRegistry registry) { // 自定义拦截器，添加拦截路径和排除拦截路径

		//配置完成后，在后期上线时再放开。
		/*/
		registry.addInterceptor(jwtIntercept)
		.addPathPatterns("/api/**")

		.excludePathPatterns("/api/user/login"
				,"/api/user/logout"
				,"/api/user/lists"
				,"/news/"
				,"/api/news/new/list");
		//*/
	}
}
