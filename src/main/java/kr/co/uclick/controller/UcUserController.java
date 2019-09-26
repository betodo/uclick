package kr.co.uclick.controller;


import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.uclick.dto.ValidUserDto;
import kr.co.uclick.entity.Sample;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.SampleService;
import kr.co.uclick.service.UserRepositoryService;

//뷰단에서 요청이 오면 컨트롤러가 매핑하여 서비스단에 전달///
//서비스단의 응답을 뷰단에 전달

//유저관련 컨트롤러
@Controller // 컨트롤러임을 명시
public class UcUserController {

	//로그 찍기 위한 로그객체 생성
	private static final Logger logger = LoggerFactory.getLogger(UcUserController.class);

	@Autowired // 서비스 bean 찾아가도록 의존성 주입
	//의존성 주입 = A a = new A() 이런 생성 종료 신경 안쓰게 해주는 것
	// like a가 b를 사용할때(b가 있어야함) 그 b를 편하게 가져다 쓰게 해줌
	private SampleService sampleService;
	
	@Autowired
	private UserRepositoryService uRepoService;
	
	@GetMapping(value = "/")
	public String index(Model model) {
		return "redirect:userJsp/userList"; //newForm.jsp로 이동
	}

	//사용자 전체 조회
	@GetMapping(value = "userJsp/userList")
	public String userList(Model model, String searchName, String key, Integer nowPage, Integer size) {
		logger.debug("searchName :{}", searchName);
		logger.debug("key :{}", key);
		
		//유저 이름으로 검색 버튼 클릭시
		if("search".equals(key)) {
			logger.info("유저 검색");
			logger.debug("searchName :{}", searchName);
			model.addAttribute("users", uRepoService.searchUser(searchName));
			return "userJsp/userSearch"; // 검색결과를 보여주는 웹 페이지로 이동
		
		//검색 안 했을때
		}else {
			logger.info("유저 목록");
			logger.debug("size :{}", size);
			Page<User> users;
			if(nowPage == null || size == null) { //int는 null 안잡힘, Integer null 잡힘
				users = uRepoService.findAllbyPage(0,5);//첫 웹페이지 접근시 null처리
			}else {
				users = uRepoService.findAllbyPage(nowPage, size);//페이네이션으로 접근시 처리
			}
			model.addAttribute("users", users);
			return "userJsp/userList";
		}
	}
	
	
	//@GetMapping(value = "inputUser_forUpdate/{id}") //겟맵핑 테스트용
	//public String inputUser_forUpdate(@PathVariable("id") Long userId, Model model) {
	
	//사용자 수정시 입력폼
	@PostMapping(value = "userJsp/inputUser_forUpdate")
	public String inputUser_forUpdate(Long userId, Model model) {
		logger.info("유저 입력-업데이트");
		logger.debug("user id :{}", userId);
		model.addAttribute("user", uRepoService.findUserById(userId));
		return "userJsp/inputUser_forUpdate"; 
	}
	
	//사용자 수정 및 결과 메시지
	@PostMapping(value = "userJsp/userUpdate")
	public String userUpdate(User user, Model model) {
		logger.info("유저 업데이트");
		logger.debug("user id : {}", user.getId());
		logger.debug("user name : {}", user.getName());
		logger.debug("user position : {}", user.getJobPosition());
		
		String result="";
		try {
			uRepoService.save(user);
			result = "수정 성공";
		} catch (Exception e) {
			logger.debug("에러 : {}", e);
			result ="수정 실패";
		}
		model.addAttribute("result", result);

		return "userJsp/userUpdate"; 
	}
	
	//사용자 삭제 수행 및 결과 메시지
	@PostMapping(value = "userJsp/userDelete")
	public String userDelete(Long userId, Model model) {
		logger.info("유저 삭제");
		logger.debug("user id : {}", userId);
		String result="";
		try {
			uRepoService.deleteById(userId);
			result = "삭제 성공";
		} catch (Exception e) {
			logger.debug("에러 : {}", e);
			result ="삭제 실패";
		}
		model.addAttribute("result", result);

		return "userJsp/userDelete"; 
	}
	
	//사용자 추가시 입력폼
	@GetMapping(value = "/userJsp/inputUser_forInsert")
	public String inputUser_forInsert(Model model) {
		logger.info("유저 입력-인서트");
		//유효성검사를 위해 빈 dto를 넘겨준다
		model.addAttribute("validUserDto", new ValidUserDto());
		
    	return "/userJsp/inputUser_forInsert";
	}
	
	
	//사용자 추가 수행 및 결과 메세지
	@PostMapping(value = "userJsp/userInsert")
	public String userInsert(Model model, @ModelAttribute("validUserDto") @Valid ValidUserDto validUserDto,
			BindingResult bindingResult) {
		logger.info("유저 추가");
		
		if(bindingResult.hasErrors()) { //form에 에러가 있으면
			logger.info("사용자 추가 - 입력값 유효하지 않음");
			logger.debug("userDto name : {}", validUserDto.getName());
			logger.debug("userDto jobPosition : {}", validUserDto.getJobPosition());
			logger.debug("userDto dept : {}", validUserDto.getDept());
			FieldError fieldError = bindingResult.getFieldError();
			logger.info(fieldError.getDefaultMessage());

			return "userJsp/inputUser_forInsert";
    		
    	}else {  //에러가 없으면
    		String resultMessage="";
    		try {
    			User user = new User();
    			user.setName(validUserDto.getName());
    			user.setDept(validUserDto.getDept());
    			user.setJobPosition(validUserDto.getJobPosition());
    			uRepoService.save(user);
    			resultMessage = "추가 성공";
    		} catch (Exception e) {
    			logger.debug("에러 : {}", e);
    			resultMessage ="추가 실패";
    		}
    		model.addAttribute("resultMessage", resultMessage);

    		return "userJsp/userInsert"; 
    		
    	}
    
	}
	
	
// 페이지구현 전 findAll()	
//	//사용자 전체 목록 조회
//	@GetMapping(value = "userJsp/userList")
//	public String userList(Model model, String searchName, String key) {
//		logger.info("유저 목록");
//		logger.debug("searchName :{}", searchName);
//		logger.debug("key :{}", key);
//		
//		//유저 이름으로 검색했을때
//		if("search".equals(key)) {
//			logger.info("유저 검색");
//			logger.debug("searchName :{}", searchName);
//			model.addAttribute("users", uRepoService.searchUser(searchName));
//		//검색 안 했을때 -- key가 null로 들어옴
//		}else {
//			logger.info("find all");
//			model.addAttribute("users", uRepoService.findAll());
//		}
//		return "userJsp/userList";
//	}
	
	
	
	
	
//샘플	
/////////////////////////////////////////////////////////////////////////////////////	
	// newForm.html 경로에 Get 방식으로 접근했을 경우
	@GetMapping(value = "newForm.html")
	public String newForm(Model model) {
		return "newForm"; //newForm.jsp로 이동
	}

	@GetMapping(value = "editForm.html")
	public String editForm(Long sampleId, Model model) {
		sampleService.findById(sampleId);
		return "editForm";
	}

	// save.html 경로에 Post 방식으로 접근했을 경우
	@PostMapping(value = "save.html")
	public String save(Sample sample, Model model) {
		return "redirect:list.html"; // list.html로 redirect함
	}

	@DeleteMapping(value = "delete.html")
	public String delete(Long sampleId, Model model) {
		return "redirect:list.html";
	}

	@GetMapping(value = "sample.html")
	public String sample(String name, Sample sample, Model model) {

		logger.debug("sample name : {}", name);
		logger.debug("Sample.name : {}", sample.getName());

		model.addAttribute("samples", sampleService.findAll());
		model.addAttribute("sample", sample);
		model.addAttribute("findSampleByName", sampleService.findSampleByName(name));
		return "sample";
	}
}
