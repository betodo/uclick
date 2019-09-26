package kr.co.uclick.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

//DIO역할(RIO역할)
//하이버네이트에게 이 클래스가 DB랑 매핑된다 알려줌
@Entity
public class Sample {

	@Id //테이블의 기본키에 매핑(pk, primarykey) 요게 있는 칼럼을 식별자 필드라함
	@TableGenerator(name = "sample")// sample이라는 주키 생성자 만듦
	//기본키 생성 전략(auto=db종류에 따라 jpa가 선택(mysql은 identity선택)
	// indentity = 기본키생성을 db에 위임
	// sequence = 데이터베이스 시퀀스 사용해서 기본키 할당
	// tbale = 데이터베이스에 키 생성 전용 테이블을 하나 만들고 이를 사용하여 기본키를 생성
	// 이 테이블의 생성키 전략을 따른다
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sample")
	private Long id;// PK로 쓰일 id값

	private String name;

	private int number;

	//@Column(name="name", nullable=false, length=100)
	//nullable null여부 설정 true=기본 값 false=not null
	//length String 타입만 적용 가능 기본 값은 255	
	//@Column 선언이 꼭 필요한 것은 아니다
	//없으면 멤버 변수명과 일치하는 데이터베이스 컬럼을 매핑한다.
	
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
