package kr.co.uclick.configuration;

/* ---web.xml대체---
 * 서블릿은 3.0 이후부터 web.xml 없이도 서블릿 컨텍스트 초기화 작업이 가능
 * 프레임워크 레벨에서 직접 초기화할 수 있게 도와주는 ServletContainerInitializer API를 제공하기 때문
 * 서블릿 컨텍스트 초기화  = web.xml에서 했던 서블릿 등록/매핑, 리스너 등록, 필터 등록 같은 작업들
 * 인터페이스 WebApplicationInitializer를 구현한 클래스를 만들어두면 웹 app이 시작할때
 * 자동으로 onstartup()메서드가 실행되고 여기서 초기화 작업을 수행 
 * void onStartup(ServletContext servletCxt) { 
 * */

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

//WAS(tomcat)가 시작될 때 필요한 설정들이 시작되는 곳 = web.xml을 자바클래스 형태로 설정한 것
//xml형식으로 등록했던걸 자바 코드로 바꾼 것
public class MyWebApplicationInitializer implements WebApplicationInitializer {

	//WAS가 시작될 때 제일 먼저 실행되는 메소드
	//생성과 소멸시기를 잡음
	//웹 어플리케이션이 시작될 때 초기화 되고 종료될때 종료되기 위해
	@Override
	public void onStartup(ServletContext servletCxt) {

		// Create the 'root' Spring application context
		// root-context 해당, 생성하고 -> SpringConfiguration.class라고 등록
		// 이것도 스프링 컨테이너의 한 종류 ->웹과 관련없는 객체들을 저장 ex) DAO
		// SpringConfiguration 클래스에 Annotation을 다는 효과
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfiguration.class);

		// Manage the lifecycle of the root application context
		// 리스너등록 (요청을 듣는다) 앱 종료시 죽음 메모리 관리 안해도 되도록
		// onStartup에 리스너 등록 이유 웹 app 시작될때 생성되고 종료되기 위해
		// 생성 소멸 시기가 서블릿과 같음
		servletCxt.addListener(new ContextLoaderListener(rootContext));

		// Create the dispatcher servlet's Spring application context
		// servlet-context 해당
		// 디스패처서블릿컨택스트 생성 하고 SpringWebConfiguration.class를 등록
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringWebConfiguration.class);

		// Register and map the dispatcher servlet
		// 디스패처 서블릿 등록 구현
		// 디스패처서블릿 = HTTP 프로토콜을 통해 들어오는 모든 요청을 컨트롤러 앞에서 처리해주는 Front Controller
		ServletRegistration.Dynamic dispatcher = servletCxt.addServlet("politech",
				new DispatcherServlet(dispatcherContext));//위에서 등록한 서블릿컨택스트 사용
		dispatcher.setLoadOnStartup(1);//메소드의 실행순서(우선순위) 설정
		dispatcher.addMapping("/");//요청이 ip:포트/로 왔을 때 실행되게 해줌 //이 서블릿이 매핑할 url 패턴 설정
									//이 설정으로 인해 주소가 그냥 들어와도 시작되는 것

		//값 주고 받을때 utf-8로 하라는 한글처리필터 생성
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);// 다른 설정이 되어 있어도 강제로 인코딩하도록 설정
		
		//설정한 한글처리 필터를서블릿설정에 추가
		servletCxt.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null, false,
				"/*");
	}

}
