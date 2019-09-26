package kr.co.uclick.configuration;

//웹과 관련된 설정들 객체로 정의한 클래스


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration // 설정 파일임을 알림
@EnableWebMvc  //자동으로 WebMvcConfigurationSupport 클래스가 Bean에 등록된다. Spring MVC의 설정임을 표시
//@Component와 @Service, @Repository, @Controller, @Configuration이 붙은 클래스 Bean들을 찾아서 Context에 bean등록을 해주는 Annotation
@ComponentScan("kr.co.uclick.controller")//어노테이션 스캔할 대상지정
public class SpringWebConfiguration implements WebMvcConfigurer {

	//rest-ful 설정을 위한 메소드
	//웹 관련 접근과 읽기에 관한 설정으로 이해
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);//확장자로 ContentType을 구분하지 않도록 설정
		configurer.favorParameter(true);// 특정 파라미터로 ContentType을 구분하도록 설정
		configurer.parameterName("mediaType");// 파라미터 이름을 mediaType으로 지정
		configurer.ignoreAcceptHeader(true);// XML, Json 등 호출하는 URL이 Return값에 따라 변하게 함
		configurer.useJaf(false);
		configurer.mediaType("xml", MediaType.APPLICATION_XML); // xml확장자의 mediaType을 매핑에 추가
		configurer.mediaType("json", MediaType.APPLICATION_JSON); // json확장자의 mediaType을 매핑에 추가
	}

	// HTTP로 접근 시 Controller 앞에 둘 Interceptor 설정 메소드
	// 컨트롤러와 디스페처 서블릿 사이에서 요청을 처리
	// ex)컨트롤러 앞에서 특정 url 접근시 동작하고 싶은게 있으면 처리한다
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	//@RequestMapping에 등록되지 않은 요청 또는 JSP에 대한 요청을 처리하는 DefaultServletHandler 설정(이곳으로 요청전달됨)
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// 다국어 설정을 위한 LocalResolver에 사용할 Resolver를 반환하는 메소드
	// 웹 관련 다국어 설정으로 이해
	@Bean
	public LocaleResolver LocaleResolver() {
		return new CookieLocaleResolver();
	}

	// 다국어 설정에서 사용할 메시지 소스 설정
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("validate-message.properties");
		return resourceBundleMessageSource;
	}

	// 웹 애플리케이션 WAR 파일 내의 뷰 템플릿을 찾아주는 Resolver
	//.jsp가 없어도 되는 이유
	//여기서 리턴값에 .jsp을 입력하지 않아도 위 이름으로 찾아서 페이지 매핑
	@Bean
	public InternalResourceViewResolver InternalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}
}
