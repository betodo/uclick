package kr.co.uclick.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long>, QuerydslPredicateExecutor<Phone> {

	//전화기 id로 조회
	public Phone findPhoneById(Long id);
	
	//사용자 id로 전화기 조회
	public List<Phone> findPhoneByUserId(Long user_id);
	
	
	//전화번호로 검색
	@QueryHints(value= {
	         @QueryHint(name="org.hibernate.cacheable", value="true"),
	         @QueryHint(name="org.hibernate.cacheMode", value="NORMAL"),
	         @QueryHint(name="org.hibernate.cacheRegion", value="Phone")
	})
	public List<Phone> findUserByNumberContaining(String number);
	
	//전화번호 중복 검사
	public boolean existsByNumber(String number);
	
}
