package kr.co.uclick.entity;


import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@TableGenerator(name = "phone_generator",
					table = "pk_tb_phone",
					initialValue = 0,
					allocationSize = 1)
@Table(name = "phone")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "Phone")
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "phone_generator")
	@Column(name = "id")
	private Long id;
	
	@Column(nullable = false, unique = true) //unique를 지정함으로서 중복방지하고 에러처리 가능
	private String number;
	
	@Column(nullable = false)
	private String agency;

	//매니투원의 기본전략은 eager, 
	//nullable과 optional은 같다고 볼수 있으나 nullable db단, optional은 메모리단, fetch=FetchType.LAZY,
	//lazy가 성능상 유효할 수 있으나 현 프로젝트에선 굳이 lazy를 활용하지 않음
	//자식이 부모 찾을때는 보통 eager
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "Phone.user")
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name = "user_id")
	private User user;
	
//===============get set=================	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	
	
}
