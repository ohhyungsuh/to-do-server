package com.example.to_do_server.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SignupDto {

    @NotNull(message = "아이디를 입력해주세요")
    private String loginId;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(regexp = ".*[!@#$].*", message = "비밀번호에는 특수문자(\"!,@,#,$\")가 포함되어야 합니다.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "이름은 숫자, 영어, 한글만 포함될 수 있습니다.")
    private String name;

    @Email
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    public SignupDto(String loginId, String password, String name, String email, LocalDate birth) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birth = birth;
    }
}
