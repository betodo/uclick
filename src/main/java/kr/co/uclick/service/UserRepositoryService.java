package kr.co.uclick.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.User;
import kr.co.uclick.repository.UserRepository;

@Service
@Transactional // crud 모두 트랜잭션으로 db관리
public class UserRepositoryService {

	@Autowired
	private UserRepository userRepo;
	
	//사용자 수정 및 추가
	//사용자의 키값이 id의 set 유무에 따라 수정 또는 추가
	public void save(User user) {
		userRepo.save(user);
	}

	//사용자 삭제
	// delete(Entity entity)도 jpa가 지원하나 entity를 넣어주는 코드가 필요함
	// 굳이 그럴 필요 없이 같은 결과를 볼 수 있으니 deleteById 사용
	public void deleteById(Long id) {
		userRepo.deleteById(id);
	}
	
	//모든 사용자(목록) 조회
	//@Transactional(readOnly = true) = 조회중에 수정하거나 인서트 하지 말라는 의미
	@Transactional(readOnly = true) // for성능최적화 트랜잭션 작업 안에서 쓰기 작업 방지
	public List<User> findAll(){
		return userRepo.findAll();
	}
	
	//Page리턴은 Pageable매개변수 필요
	//Pageable은 PageRequest.of()로 생성 가능
	//페이징을 위한 조회 PageRequest.of(페이지번호, 조회할 row수, 정렬조건)
	@Transactional(readOnly = true) 
	public Page<User> findAllbyPage(int page, int size){
		return userRepo.findAll(PageRequest.of(page, size, new Sort(Direction.DESC, "regDate")));
	}
	
	//사용자 이름으로 검색 조회
	@Transactional(readOnly = true)
	public List<User> searchUser(String name){
		return userRepo.findUserByNameContaining(name);
	}
	
	
//////////////////현 프로젝트에서 미사용//////////////////////////////////////////////////
	
	//optional 자바에서 널포인트익셉션을 처리하기 위해 생성한 클래스-현프로젝트에서 미사용
	//수험용으로 구현
	@Transactional(readOnly = true)
	public Optional<User> findByID (long id) {
		return userRepo.findById(id);
	}
	
	//매니투원에서 패치전략이 lazy일때 eager와 같은 결과를 얻는 메소드
	//수험용으로 구현 -현프로젝트에서 미사용
	@Transactional(readOnly = true)
	public User getTaskListEager(Long id) {
		User u =  this.findUserById(id);
		Hibernate.initialize(u.getPhones());
		return u;
	}
	
	//사용자 한 명 조회--현 프로젝트에서 미사용
	public User findUserById(long id) {
		return userRepo.findUserById(id);
	}
	
	
}
