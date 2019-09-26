package kr.co.uclick.configuration;

//설정에서 등록한 applicatitonContext가 만들어 놓을 객체들을 정의한 클래스
//톰캣이 시작되면 얘네가 자동 실행되서 루트컨텍스트에 저장(onStrartup메서드)
//한마디로 필요한 객체들을 정의하고 설정하는 클래스라고 보면 됨(웹관련X)
//xml로 있어야 할 애들을 @Configuration으로 분리했다고 보면 됨

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ignite.cache.hibernate.HibernateRegionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//Configuration을 클래스에 적용하고 @Bean을 해당 클래스의 메소드에 적용하면 @Autowired로 빈을 부를 수 있다.
@Configuration //이게 설정 파일 클래스임을 명시
@ImportResource(locations = "classpath:applicationContext-ignite.xml") //이그나이트를 임포트
//이그나이트 = 간단히 말해서 강력한 성능을 지원하는 인메모리 컴퓨팅 플랫폼
//디스크 기반 데이터 접근 + 메모리 중심 데이터 접근 처리로 성능을 뛰어나게 한다
//인메모리 데이터 베이스, 분산 데이터베이스, 캐싱, 처리 플래폼
@ComponentScan({ "kr.co.uclick.service" })//필요 어노테이션을 스캔할 대상 설정
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)//트랜잭션매니저 사용 가능하게
@EnableSpringConfigured // Spring 설정을 주입받을 수 있게
@EnableJpaRepositories(basePackages = "kr.co.uclick.repository") //jpaRepository와 연결가능하게
public class SpringConfiguration {

	//db 커넥션을 위한 설정 객체(jpa, 하이버네이트가 사용)
	@Bean // bean으로 등록하기 위한 annotation
	@Primary // 우선순위 정해줌
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
//		dataSource.setUrl("jdbc:mariadb://127.0.0.1:3306/polytech");
//		dataSource.setUsername("root");
//		dataSource.setPassword("cafe2413");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.23.96:3306/uclick?serverTimezone=UTC");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");
		return dataSource; // bean으로 등록하기 위한 annotation
	}

	//EntityManagerFactory를 bean으로 등록
	//@Entity를 관리하는게 EntityManager이고 이 매니저를 만드는게 지금 이 객체공장
	//sessionFactoryBean과 동일한 역할도 담당
	@Bean
	// 이 bean이 등록될 때 igniteSystem이 빈으로 등록된 후에 등록되도록 설정
	@DependsOn("igniteSystem")// 이 bean이 등록될 때 igniteSystem이 빈으로 등록된 후에 등록되도록 설정
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());//객체공장에 아까 만든 db커넥객체도 넣고
		em.setPackagesToScan("kr.co.uclick.entity");//이 공장이 @Entity를 스캔할 범위 설정
		//jpa규약과 hibernate간의 adapter. 특별히 설정할 내용은 존재하지 않고 showSQL정도의 설정이 존재.
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);//이 객체 공장에 만든 어뎁터 객체 넣기
		em.setJpaProperties(additionalProperties()); // JPA 규약에 쓰일 속성 설정 객체도 공장에 추가
		return em;
	}

	//트랜잭션을 관리하는 TransactionManager 등록, jpa지원
	//spring에서 트랜잭션을 관리, 사용하기 위한 코드
	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	//트랙잭션 예외처리 객체
		//@Repository 클래스들에 대해 자동으로 예외를 Spring의 DataAccessException으로 일괄 변환해준다.
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	// 하이버네이트 세부 설정
	public Properties additionalProperties() {
		Properties properties = new Properties();
		//mapped entity class를 분석하여  schema를 자동으로 생성
		properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "update"); // Domain 변경 시 기존 테이블을 update하도록 설정(DB)
		properties.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());//SQL 정렬해서 보기
		properties.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());//SQL 보기
		properties.setProperty(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());// 여러 RDBMS와 호환이 가능하도록 방언 설정

		properties.setProperty(AvailableSettings.STATEMENT_BATCH_SIZE, "1000");//최대 JDBC 배치 크기 설정

		properties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, Boolean.TRUE.toString());//level2 cache를 사용
		properties.setProperty(AvailableSettings.USE_QUERY_CACHE, Boolean.TRUE.toString());//query cache를 사용
		properties.setProperty(AvailableSettings.GENERATE_STATISTICS, Boolean.TRUE.toString());//통계 수집(collection) 사용
//		properties.setProperty(AvailableSettings.GENERATE_STATISTICS, Boolean.FALSE.toString());//통계 수집(collection) 사용
		// L2 Cache를 사용할 region 설정
		properties.setProperty(AvailableSettings.CACHE_REGION_FACTORY, HibernateRegionFactory.class.getName());

		properties.setProperty("org.apache.ignite.hibernate.ignite_instance_name", "cafe-grid");//ignite 이름을 cafe-grid로 설정 
		//default 접근 방법 : 객체 동시 수정 등에 대한 고려를 하지 않고 캐싱을 한다.(엄격하지 않은 읽기/쓰기)
		// L2 Cache Access 권한을 read, write 모두 줌
		properties.setProperty("org.apache.ignite.hibernate.default_access_type", "NONSTRICT_READ_WRITE");

		// 이름 규칙을 만들어놓은 CustomPhysicalNamingStrategyStandardImpl으로 함
		properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
				CustomPhysicalNamingStrategyStandardImpl.class.getName());
		return properties;
	}

}
