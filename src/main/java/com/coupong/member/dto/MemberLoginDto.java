package com.coupong.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberLoginDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public MemberLoginDto() {
    }

    public MemberLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
