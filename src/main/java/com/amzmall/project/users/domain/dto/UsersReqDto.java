package com.amzmall.project.users.domain.dto;

import com.amzmall.project.users.domain.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersReqDto {
    @Schema(description = "고객 번호")
    private int id;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "이름")
    private String name;

    public Users toEntity(String password, Role role) {
        return Users.builder()
            .email(email)
            .name(name)
            .role(role)
            .password(password)
            .isActivate(true)
            .build();
    }
}
