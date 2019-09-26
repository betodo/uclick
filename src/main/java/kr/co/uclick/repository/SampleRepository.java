package kr.co.uclick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Sample;

//스프링은 기본적인 CRUD가 가능하도록 JpaRepository 인트페이스 제공 
//JpaRepository 상속받기만 해도 @Repository어노 추가 안해도됨
//이걸 상속 받을땐 Entity클래스와 id변수형 들어가야 함
//인터페이스 , 상속 구현만 해도 아래와 같은 기능 사용가능
//save()=insert,update / findOne()= pk로 1row찾기  / findAll()=전체 레코드 조회 정렬,페이징 가능
//count() = 레코드 갯수 delete()레코드 삭제등등
//findBy~있음
//필요한건 선언

public interface SampleRepository
		extends JpaRepository<Sample, Long>,QuerydslPredicateExecutor<Sample>, CustomSampleRepository {

	public List<Sample> findSampleByName(String name);

}
