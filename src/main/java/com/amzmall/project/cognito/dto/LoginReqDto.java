package com.amzmall.project.cognito.dto;

import com.amzmall.project.users.domain.dto.Role;
import com.amzmall.project.users.domain.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {
    @Schema(description = "고객 번호")
    private int id;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀 번호")
    private String password;
    @Schema(description = "이름")
    private String name;

    public Users toEntity() {
        return Users.builder()
            .email(email)
            .name(name)
            .role(Role.USER)
            .password(password)
            .isActivate(true)
            .build();
    }
}
