package kr.co.uclick.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Component
public class ValidPhoneDto {

	@NotBlank(message="필수 입력입니다.")
	@Pattern(regexp="^[0-9]*$", message="숫자만 입력가능합니다.(공백불가)")
	@Size(max=3, message="3자리이하로 입력해주세요.")
	private String number1;
	
	@NotBlank(message="필수 입력입니다.")
	@Pattern(regexp="^[0-9]*$", message="숫자만 입력가능합니다.(공백불가)")
	@Size(max=4, message="4자리이하로 입력해주세요.")
	private String number2;
	
	@NotBlank(message="필수 입력입니다.")
	@Pattern(regexp="^[0-9]*$", message="숫자만 입력가능합니다.(공백불가)")
	@Size(max=4, message="4자리이하로 입력해주세요.")
	private String number3;
	
	@NotBlank(message="필수 입력입니다.")
	private String agency;

	public String getNumber1() {
		return number1;
	}

	public void setNumber1(String number1) {
		this.number1 = number1;
	}

	public String getNumber2() {
		return number2;
	}

	public void setNumber2(String number2) {
		this.number2 = number2;
	}

	public String getNumber3() {
		return number3;
	}

	public void setNumber3(String number3) {
		this.number3 = number3;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	
}
