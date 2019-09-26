package kr.co.uclick.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

	//사용자 id로 조회
	public User findUserById(long id);
	
	//사용자 이름으로 검색
	//쿼리캐시 적용 = 쿼리결과를 캐싱
	//cacheable = 쿼리캐시가능여부 기본값은 false
	//cacheMode =세션이 L2 캐시 및 쿼리 캐시와 상호작용하는 방식 제어 nomal = 세션은 캐시에서 항목을 읽고, 추가할 수 있음
	//chacheRegion = xml에서 설정한 캐시영역과 맵핑 지정
   @QueryHints(value= {
	         @QueryHint(name="org.hibernate.cacheable", value="true"), //쿼리 캐시 가능
	         @QueryHint(name="org.hibernate.cacheMode", value="NORMAL"),// 세션과 상호작용 방식 지정(읽고 쓰기 가능)
	         @QueryHint(name="org.hibernate.cacheRegion", value="User")// xml에서 지정한 캐시관련 bean
	})
	public List<User> findUserByNameContaining(String name);

	
	
	
	
}
