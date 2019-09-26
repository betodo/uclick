package kr.co.uclick.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

//유효성 검사시 validator역할을 하는 클래스
//어노테이션으로 유효성을 검사한다
@Component //이 클래스를 bean으로 등록
public class ValidUserDto {

	@NotEmpty(message="부서를 선택하세요.")//유효성 실패시 뷰단에 메시지 출력
	private String dept;
	
	@NotBlank(message="직급을 선택하세요.")
	private String jobPosition;
	
	@NotBlank(message="필수 입력입니다.")
	//정규식 패턴으로 입력값 조절 가능
	@Pattern(regexp="^[가-힣]*$", message="한글만 입력가능합니다.(공백불가)")
	@Size(min=2, max=6, message="이름은 2~6자 이어야 합니다.")
	private String name;

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
