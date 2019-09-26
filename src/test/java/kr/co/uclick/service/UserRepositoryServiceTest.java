package kr.co.uclick.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order.Direction;

import kr.co.uclick.configuration.SpringConfiguration;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.UserRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class UserRepositoryServiceTest {

	@Autowired
	UserRepositoryService userService;
	
	@Autowired
	PhoneRepositoryService phoneService;
	
	@Autowired
	private UserRepository userRepo;
	
	//유저 추가 insert
//	@Test
//	public void saveTest(){
//		//유저 입력시 장치 입력 같이 안하는 view로 짬
//		User u = new User(); 
//		u.setName("설기현");
//		u.setDept("영업");
//		u.setJobPosition("부장");
//		userService.save(u);
//	}
	
	//사용자 입력 테스트
	@Test 
	public void insertTest() {
		Long count = userRepo.count();
		User user = new User(); 
		user.setName("가나다");//이름변경
		user.setDept("영업본부");
		user.setJobPosition("사원");
		userService.save(user);//실행
		assertEquals(count+1,userRepo.count());//test
	}
	
//	@Test
//	public void deleteByIdTest() {
//		userService.deleteById((long) 1);
//	}
	
	//모든 row 조회
//	@Test
//	public void findAllTest() {
//		List<User> users = userService.findAll();
//		System.out.println("==============실행=================");
//		for (int i = 0 ; i < users.size(); i++) {
//			System.out.println(users.get(i).getId());
//			System.out.println(users.get(i).getName());
//			System.out.println(users.get(i).getJobPosition());
//			System.out.println(users.get(i).getRegDate());
//		}
//		System.out.println("===================================");
//	}
	
	//한 row 조회
//	@Test
//	public void findUserByIdTest() {
//		System.out.println("==============실행=================");
//		User u = userService.findUserById(4);
//		System.out.println(u.getId());
//		System.out.println(u.getName());
//		System.out.println("===================================");
//	}
	
	
//	@Test
//	public void findByIdTest() {
//		Optional<User> op_user = userService.findByID((long) 10);
//		System.out.println("==============실행=================");
//		//op_user.get() = 옵셔널 클래스에 있는 user클래스에 접근 
//		System.out.println(op_user.get().getId());
//		System.out.println(op_user.get().getName());
//		System.out.println(op_user.get().getReg_date());
//		System.out.println(op_user.get().getJob_position());
//		System.out.println("===================================");
//	}
	
//	@Test
//	public void count() {
//		Long c = userService.IdOfNewUser();
//		System.out.println(c);
//	}
	
//	@Test
//	public void nowDate() {
//		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
//		Calendar calt = Calendar.getInstance();
//		
//		String d = userService.nowDate();
//		System.out.println(d);
//		System.out.println(sdf.format(d));
//		System.out.println("======================================");
//		
//		String a = sdf.format(calt.getTime());
//		System.out.println(a);
//		
//	}
	
//	@Test
//	public void page() {
//		
//	Page<User> p = userService.findAllbyPage(0, 5);
//	System.out.println(p.getNumber());
//	System.out.println(p.getSize());
//	System.out.println(p.getTotalPages());
//	System.out.println(p.getTotalElements());
//	
//	
//	}
	
//	@Test
//	public void saveDatas(){
//		//유저 입력시 장치 입력 같이 안하는 view로 짬
//		for(int k = 0; k < 50; k++) {
//			User u = new User(); 
//			u.setName("테스트유저 "+k);
//			u.setDept("UC사업");
//			u.setJobPosition("사원");
//			userService.save(u);
//		}
//	}
	
	
}













