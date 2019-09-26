package kr.co.uclick.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.PhoneRepository;

@Service
@Transactional
public class PhoneRepositoryService {

	//의존성 주입
	@Autowired
	private PhoneRepository phoneRepo;

	//전화기 추가 및 수정
	public void save(Phone phone) {
		phoneRepo.save(phone);
	}
	
	//전화기 삭제
	public void deleteById(Long id) {
		phoneRepo.deleteById(id);
	}
	
	//사용자 id로 전화기 조회
	@Transactional(readOnly = true) 
	public List<Phone> findPhoneByUserId(Long userId) {
		return phoneRepo.findPhoneByUserId(userId);
	}
	
	
	//모든 전화기(목록) 조회
	@Transactional(readOnly = true) 
	public List<Phone> findAll(){
		
		List<Phone> phones = phoneRepo.findAll();
		//매니투언 패치전략이 lazy일때 -- 수험용
//		phone entity manytoone LazyInitializationc처리
//		for (int i = 0 ; i < phones.size(); i++) { 
//			Hibernate.initialize(phones.get(i).getUser());
//		}
		//트랜잭션내에서 세션이 끊기기 전에 참조테이블의 정보를 이니셜라이즈 해놓음
		return phones;
	}
	
	//페이징을 위한 조회PageRequest.of(페이지번호, 조회할 row수, 정렬조건)
	@Transactional(readOnly = true) 
	public Page<Phone> findAllbyPageOfPhone(int page, int size){
		return phoneRepo.findAll(PageRequest.of(page, size, new Sort(Direction.DESC, "id")));

	}
	
	
	//전화번호 첫 중간 끝으로 나누는 메소드(뷰단에서 전화번호를 분리해서 출력하기 위함)
	@Transactional(readOnly = true)
	public String[] phoneNumberSplit(Long id) {
		String result[] = phoneRepo.findPhoneById(id).getNumber().split("-");
		return result;
	}
	
	//전화기 하나 조회
	@Transactional(readOnly = true) 
	public Phone findPhoneById(Long id) {
		Phone phone = phoneRepo.findPhoneById(id);
		//매니투언 패치전략이 lazy일때 -- 수험용
		//phone entity manytoone lazy일때 LazyInitializationc 처리
		//Hibernate.initialize(phone.getUser());
		return phone;
	}
	
	//전화번호로 검색
	@Transactional(readOnly = true)
	public List<Phone> searchPhone(String number){
		return phoneRepo.findUserByNumberContaining(number);
	}
	
//////////////////현 프로젝트에서 미사용//////////////////////////////////////////////////
	
	//전화번호 중복 검사 -현프로젝트에서 미사용
	//중복검사를 수행할 수 있으나 중복오류가 나면 에러 메시지를 띄워 처리함
	@Transactional(readOnly = true)
	public boolean existsByNumber(String number) {
		return phoneRepo.existsByNumber(number); //중복값이 있으면 true
	}
	
}
