package kr.co.uclick.entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;



//생성자 전략으로 TABLE을 하는 이유 db가 오라클, mysql상관없이 적용하기 위해
//mysql은 상관 없지만 오라클은 autoincrement하려면 table로 관리해줘야한다.

//캐시 : 자주 액세스하는 데이터나 프로그램 명령을 반복해서 검색하지 않고도 즉각 사용할 수 있도록 저장해두는 영역
//jpa캐시 db접근 최소화 목적으로 조회한 데이터를 메모리에 올려놓고 동일한 요청이 오면 db에 접근하지않고 메모리에있는 데이터를 제공해준다.
//1차캐시 : 영속성 컨택스트 자체 엔터티를 캐시에 올림 이후 같은 엔터티를 조회하면 캐시에 있는 엔터티를 그대로 반환
//2차 캐시 : app가 공유하는 캐시 shared캐시, 2차캐시, level2캐시 L2캐시 라고 함
//2차 캐시가 1차와 다른 점 : 캐시한 객체를 직접 반환하지 않고 복사본을 만들어서 반환 
//& 1차는 세션범위, 세션이 닫히면 캐시도 종료, 2차는 세션팩토리범위, 세션팩토리로 생성된 모든 세션에서 공유됨
//동시성을 극대화 하기 위함


//@Cacheable(true) //엔터티에 L2캐시 적용 ,기본값 트루
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)=캐시 동시성 전략결정
//ex)READ_WRITE의 경우 하나의 객체가 동시에 수정되는 경우 고려해서 데이터의 일관성을 보장해줌
//region="User" -- appcontext-이그나이트 xml에서 지정한 캐시영역과 맵핑시킨다는 의미
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//컬랜션에 L2캐시 적용
@Entity
@TableGenerator(name = "user_generator", // 전략 이름 지정
				table = "pk_tb_user", // auto_increment관리할 테이블이름
				initialValue = 0, //시작 번호
				allocationSize = 1)//증가범위
@Table(name = "user")//db의 테이블 이름 지정
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "User")//L2캐시설정
public class User {

//============칼럼들================
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_generator")
	@Column(name = "id")
	private Long id; //사용자 id
	
	@Column(length = 20, nullable = false)
	private String name; 
	
	@Column(nullable = false)
	private String dept; //사용자 부서
	
	@Column(nullable = false)
	private String jobPosition; //사용자 직책
	
	//@UpdateTimestamp
	@Column(name="reg_date",updatable = false) // 수정할때 바뀌지 않게
	@CreationTimestamp // 인서트일자 자동 입력
	private Date regDate;
	
	
	
//===========1:N구현===============
	//1:N 구현시 양방향 매핑이 가장 좋음 (오버헤드가 없음(생성된 sql문)=성능good)
	//그 다음은 단방향 조인컬럼 (오버헤드가 적음 벗 중간에 조인컬럼에 대한 db처리가 일어나기 때문에 여전히 오버헤드 존재)
	//부모테이블에 @OneToMany만 달랑 붙이는게 가장 않좋음
	//ex) 자식 2개 지우고 싶은데 같은 부모 가진 10개 지우고 8개 삽입하는 sql처리가 벌어짐
	
	//mappedBy:  phone.java에서 user를 가리키는 변수명과 일치해야함
	//cascade : 엔터티 변경에 대해 관계를 맺은 엔터티도 변경전략 결정
	// all은 영속화, 수정, 삭제등 할때 같이 되라는 것.
	//fetch : 관계 엔터티의 데이터 읽기 전략 결정
	//	EAGER : 관계 E 미리 읽기 (불필요 조회, 불륨 큰 데이터 성능 저하 단점)
	//  LAZY : 요청들어 오면 읽기 (LAZY로 처리하고 영속성 문제는 @Transational로 잡자)
	//orphanRemoval  : 부모와 관계가 끊어진 자식을 자동삭제=true, 기본=false
	//cascade all과 orphan true면 부모 update작업시 자식이 delete됨
	@OneToMany(mappedBy="user", 
			cascade = CascadeType.ALL, 
			fetch=FetchType.LAZY, //원투매니는 기본적으로 패치타입이 레이지
			orphanRemoval = false)//(부모 변경시 자식을 삭제해버림 ->유저 키값유무로 처리)
	//하이버네이트는 콜렉션을 사용할때 바로 초기화하는 것을 권장(영속성 때문에 그렇다 한다)
	//콜렉션 = 자바의 자료구조 데이터형 통칭
	//콜렉션 인터페이스의 다양한 메서드 사용할 수 있으니 콜렉션으로	
	//기본적으로 컬랙션(OneToMany 또는 ManyToMany)은 캐시되지 않으므로 캐시사용시 명시적으로 표시해야함
	//콜랙션 캐시는 결과 집합의 식별자만 값만 캐시 요걸로 조회해서 실제 엔터티찾음
	//대상엔터티에 엔터티 캐시를 적용안하면 성능상 심각문제 가능 --폰엔터티에 캐시 적용해야함
	//결과집합에 식별자만 있어서 식별자로 한건씩 조회하기 때문
	//따라서 쿼리 캐시나 컬렉션 캐시를 사용하면 결과 대상 엔티티에는 꼭 엔티티 캐시를 적용해야함
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "User.phones")
	private Collection<Phone> phones= new ArrayList<>();
	
//===============get set=================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Collection<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Collection<Phone> phones) {
		this.phones = phones;
	}

	public void addPhone(Collection<Phone> phones) {
		this.phones.addAll(phones);
    }
	
	
}
