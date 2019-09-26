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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.uclick.dto.ValidPhoneDto;
import kr.co.uclick.dto.ValidUserDto;
import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.PhoneRepositoryService;
import kr.co.uclick.service.UserRepositoryService;

@Controller // 컨트롤러임을 명시
public class UcPhoneController {
	
	//로그객체 생성
	private static final Logger logger = LoggerFactory.getLogger(UcUserController.class);
	
	@Autowired
	private PhoneRepositoryService pRepoService;
	
	@Autowired
	private UserRepositoryService uRepoService;
	
	//전화기 전체 목록 조회
	@GetMapping(value = "phoneJsp/phoneList")
	public String phoneList(String searchNumber, String key, Integer nowPage, Integer size, Model model) {
		
		//전화기번호로 검색
		if("search".equals(key)) {
			logger.info("전화기 검색");
			logger.debug("searchNumber :{}", searchNumber);
			model.addAttribute("phones", pRepoService.searchPhone(searchNumber));
			return "phoneJsp/phoneSearch";
			
		//검색 안했을때
		}else {
			logger.info("전체 전화기 목록");
			Page<Phone> phones;
			if(nowPage == null || size == null) { //int는 null 안잡힘, Integer null 잡힘
				phones = pRepoService.findAllbyPageOfPhone(0,5);//첫 웹페이지 접근시 null처리
			}else {
				phones = pRepoService.findAllbyPageOfPhone(nowPage, size);//페이네이션으로 접근시 처리
			}
			model.addAttribute("phones", phones);
			return "phoneJsp/phoneList";
		}
		
	}
	
	//특정 사용자(userId와 userName필요)의 전화기 목록
	@GetMapping(value = "phoneJsp/phoneList_ofUser/{userId}/{userName}")
	public String phoneForm(@PathVariable("userId") Long userId,
			@PathVariable("userName") String userName, Model model) {
		logger.info("사용자의 전화기 리스트");
		model.addAttribute("userName", userName); //사용자 이름 출력용
		model.addAttribute("userId",userId);
		model.addAttribute("phones", pRepoService.findPhoneByUserId(userId));//특정사용자의 전화기 목록
		return "phoneJsp/phoneList_ofUser";
	}
	
	//전화기 수정 시 입력폼(수정 전의 값을 input태그에 출력)
	@PostMapping(value = "/phoneJsp/inputPhone_forUpdate")
	public String oneUserForm(Long phoneId, Model model) {
		logger.info("전화기 입력- 업데이트");
		logger.debug("phone id :{}", phoneId);
		String[] phoneNumber = pRepoService.phoneNumberSplit(phoneId);
		model.addAttribute("number1", phoneNumber[0]);
		model.addAttribute("number2", phoneNumber[1]);
		model.addAttribute("number3", phoneNumber[2]);
		model.addAttribute("phone", pRepoService.findPhoneById(phoneId));
		return "/phoneJsp/inputPhone_forUpdate"; 
	}
	
	//전화기 수정 수행 및 결과 메세지
	@PostMapping(value = "/phoneJsp/phoneUpdate")
	public String phoneUpdate(Phone phone, Long userId,
			String number1, String number2, String number3, Model model) {
		logger.info("전화기 수정");
		logger.debug("phone number : {}", phone.getId());
		logger.debug("phone number : {}", phone.getNumber());
		logger.debug("phone agency : {}", phone.getAgency());
		logger.debug("user id : {}", userId);
		String result="";
		try {
			String inputNum = number1+"-"+ number2+"-"+ number3;
			phone.setNumber(inputNum);
			phone.setUser(uRepoService.findUserById(userId));
			pRepoService.save(phone);
			result = "수정 성공";
		} catch (Exception e) {
			logger.debug("에러 : {}", e);
			result ="수정 실패, 전화번호가 중복 되었습니다."; //중복에러 발생시 결과메세지 띄움
		}
		model.addAttribute("result", result);
		model.addAttribute("userId",userId);
		model.addAttribute("userName",uRepoService.findUserById(userId).getName());
		return "/phoneJsp/phoneUpdate"; 
	}
	
	//전화기 추가시 입력폼
	@GetMapping(value = "phoneJsp/inputPhone_forInsert/{userId}")
	public String inputPhone_forInsert(@PathVariable("userId") Long userId, Model model) {
		logger.info("전화기 입력- 인서트");
		logger.debug("user id :{}", userId);
		model.addAttribute("userId",userId); //사용자 정보를 찾아가기위한 userId값
		model.addAttribute("validPhoneDto", new ValidPhoneDto());//유효성검사를 위해 빈 dto를 넘겨준다
		return "phoneJsp/inputPhone_forInsert";
	}
	
	//전화기 추가 수행 및 결과 메세지
	@PostMapping(value = "/phoneJsp/phoneInsert")
	public String phoneInsert(@ModelAttribute("validPhoneDto") @Valid ValidPhoneDto validPhoneDto, BindingResult bindingResult, 
			Long userId, Model model) {
		logger.info("전화기 추가");
		logger.debug("user id :{}", userId);
		
		
		if(bindingResult.hasErrors()) { //form에 에러가 있으면
			logger.info("전화기 추가 - 입력값 유효하지 않음");
			FieldError fieldError = bindingResult.getFieldError();
			logger.debug(fieldError.getDefaultMessage());//form 에러메세지 확인
			model.addAttribute("userId",userId);
			
			return "phoneJsp/inputPhone_forInsert";
			
		}else {  //에러가 없으면
			String result="";
			try {
				logger.info("전화기 추가 - 입력값 유효");
				logger.debug("user id :{}", userId);
				Phone phone = new Phone();
				//구분해서 받는 전화번호를 하나로 합침
				String inputNum = validPhoneDto.getNumber1()+"-"+ validPhoneDto.getNumber2()+"-"+ validPhoneDto.getNumber3();
				phone.setAgency(validPhoneDto.getAgency());
				phone.setNumber(inputNum);
				phone.setUser(uRepoService.findUserById(userId));//사용자 정보를 찾아 셋팅
				pRepoService.save(phone);
				result = "추가 성공";
			} catch (Exception e) {
				logger.debug("에러 : {}", e);
				result ="추가 실패, 전화번호가 중복 되었습니다.";//전화번호 중복시 에러메세지
			}
			model.addAttribute("result", result);
			model.addAttribute("userId",userId);//이전페이지 이동시 필요한 변수
			model.addAttribute("userName",uRepoService.findUserById(userId).getName());//이전페이지 이동시 필요한 변수
			
			return "/phoneJsp/phoneInsert"; 
		}
		
	}
	
	//전화번호 삭제 수행 및 결과 메세지
	@PostMapping(value = "/phoneJsp/phoneDelete")
	public String phoneDelete(Long phoneId, Long userId, Model model) {
		logger.info("전화기 삭제");
		logger.debug("phone id :{}", phoneId);
		
		String result="";
		try {
			pRepoService.deleteById(phoneId);
			
			result = "삭제 성공";
		} catch (Exception e) {
			logger.debug("에러 : {}", e);
			result ="삭제 실패";
		}
		model.addAttribute("result", result);
		model.addAttribute("userId",userId);//이전페이지 이동시 필요한 변수
		model.addAttribute("userName",uRepoService.findUserById(userId).getName());//이전페이지 이동시 필요한 변수
		return "/phoneJsp/phoneDelete"; 
	}
	
}















