package com.cookie.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.cookie.basic.intetceptor.MemberInterCeptor;

@Configuration								
public class InterceptorConfig implements WebMvcConfigurer{

	@Autowired
	private MemberInterCeptor memberInterCeptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//참고하기 인터셉터만들때~~~~~~~~~~~~~~~~~~~~~~~~~
		//Interceptor 등록하기
		//registry.addInterceptor(customInterceptor)
	
		//INTERCEPTOR를 사용할 URI 패턴 등록
		//.addPathPatterns("/member/*")//패턴추가.
		//.addPathPatterns("/member/memberPage");메서드 체인닝 이라고 게속 이어서 패턴을 추가가능.
		
		//INTERCEPTOR를 제외할 URI 패턴 등록
		//.excludePathPatterns("/member/memberLogin")
		//.excludePathPatterns("/member/memberJoin");//패턴을 제외.
		
		//WebMvcConfigurer.super.addInterceptors(registry);
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		//멤버인터셉터 등록
		registry.addInterceptor(memberInterCeptor)
		.addPathPatterns("/member/*")
		.excludePathPatterns("/member/memberLogin")
		.excludePathPatterns("/member/selectJoin")
		.excludePathPatterns("/member/memberJoin")
		.excludePathPatterns("/member/memberIndex")//빼야함
		.excludePathPatterns("/member/memberIdCheck")//빼야함.
		.excludePathPatterns("/member/memberNickCheck")//빼야함.
		.excludePathPatterns("/member/memberLogout");//뺴야함.
		//멤버페이지로 들어왔을떄 패턴등록.
	}
}