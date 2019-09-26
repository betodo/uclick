package kr.co.uclick.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.configuration.SpringConfiguration;
import kr.co.uclick.entity.Phone;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class PhoneRepositoryServiceTest {

	@Autowired
	private PhoneRepositoryService phoneService;
	
	@Autowired
	private UserRepositoryService userService;
	
	//user에 새로운 전화기 추가
//	@Test
//	public void saveTest() {
//		
////		Phone phone = new Phone();
////		phone.setUser(userService.findUserById(3)); //user의 id
////		phone.setNumber("010-2143-1234");
////		phone.setAgency("LG");
////		phoneService.save(phone);
//		
//		Phone phone = new Phone();
//		User user = new User();
//		user.setId((long)4);
//		phone.setUser(user);
//		phone.setNumber("000-0000-0000");
//		phone.setAgency("SKT");
//		phoneService.save(phone);
//	}
	
	//전화기 id로 수정 update
//	@Test
//	public void updateTest() {
//		
//		Phone phone = new Phone();
//		phone.setId((long)3);
//		phone.setUser(userService.findUserById(3)); //user의 id
//		phone.setNumber("010-5678-1234");
//		phone.setAgency("LG");
//		phoneService.save(phone);
//	
//	}
	
	//전화기 id로 삭제
//	@Test
//	public void deleteByIdTest() {
//		phoneService.deleteById((long) 3);
//	}
	
	//전화기 모두 조회
	//자식 Fetch가 Lazy면 자식이 부모를 가져올때 익셉션 발생
	//select값이 반환될때 이미 트랜잭션이 종료
	//이때 Entity가 준영속 상태로 빠짐
	//Eager면 함께 가져오겠지만 Lazy라면 하위 엔터티가 없는 상태
	//해결 1. lazy를 eager로 2. Hibernate.initialize() 이용
	//2번 방법 시 해당 엔터티를 프록시에 로드 해두는 것
	//여기서 사용할때는 트랜어노만 붙여도 된다(세션유지)
	//@Transactional(readOnly = true) // 사용할 메서드에 트랜잭션 붙이기
//	@Test
//	public void findAllTest() {
//		
//		List<Phone> phone = phoneService.findAll();
//		
//		System.out.println("==============실행=================");
//		for (int i = 0 ; i < phone.size(); i++) {
//			//Phone p = phoneService.findPhoneById(phone.get(i).getId());
//			System.out.println(phone.get(i).getId());
//			System.out.println(phone.get(i).getAgency());
//			System.out.println(phone.get(i).getNumber());
//			System.out.println(phone.get(i).getUser().getId());//이거 찾기 위해 셀렉트가 리스트 수만큼 날아감
//			//System.out.println(p.getId());
//		}
//		System.out.println("===================================");
//	}
	
	//참고=하이버네이트 초기화 이용 방법
//	public Member getMember(Long memberId){
//    Member member = MemberDao.findMember(memberId);
//    Hibernate.initialize(member.getTeam());
//    Hibernate.initialize(member.getTeam().getTeamName()); // 오직 팀 이름만 필요하다면  이 방법도 가능.
//    return member;    
//}
	
//참고=서비스에서 사용할때 해법
//서비스 클래스 내에 다음과 같이 Hibernate.initialize()를 이용해서 EAGER 용 메서드를 만들고,
//Job 클래스에서 이 메서드를 호출해서 쓰면 된다.
//	@Transactional(readOnly = true)
//    public List<Task> getTaskListEager(Long tasksId) {
//        List<Task> taskList = this.tasksRepository.findOne(tasksId).getTaskList();
//        Hibernate.initialize(taskList);
//        return taskList;
//    }
	
	//전화기 하나 조회
	//@Transactional(readOnly = true)
//	@Test
//	public void findPhoneByIdTest() {
//		System.out.println("==============실행=================");
//		Phone p = phoneService.findPhoneById(1);
//		System.out.println(p.getId());
//		System.out.println(p.getAgency());
//		System.out.println(p.getUser().getId());		
//		System.out.println("===================================");
//	}
	
	//전화기 유저 번호로 조회
//	@Transactional(readOnly = true)
//	@Test
//	public void findPhoneByUserIdTest() {
//		System.out.println("==============실행=================");
//		List<Phone> p = phoneService.findPhoneByUserId((long)1);
//		for(int i =0; i < p.size(); i++) {
//			System.out.println(p.get(i).getId());
//			System.out.println(p.get(i).getAgency());
//			System.out.println(p.get(i).getUser().getId());
//		}
//		System.out.println("===================================");
//	}
	
	//전화번호 분리
//	@Test
//	public void numberSeperate() {
//		System.out.println("==============실행=================");
//		Phone p = phoneService.findPhoneById((long)1);
//		System.out.println(p.getNumber());
//		
//		String number1 = p.getNumber().split("-")[0];
//		String number2 = p.getNumber().split("-")[1];
//		String number3 = p.getNumber().split("-")[2];
//		
//		System.out.println(number1);
//		System.out.println(number2);
//		System.out.println(number3);
//		
//		System.out.println("===================================");
//		
//	}
	
	@Test
	public void existsTest() {
		boolean exists = phoneService.existsByNumber("000-1232-0123");
		System.out.println(exists);
	}
	
	
}














