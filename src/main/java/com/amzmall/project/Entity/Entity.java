package com.amzmall.project.Entity;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Operation(summary = "유저 추가", description = "새로운 유저 생성 API 입니다. 유저는 회원가입이 따로 없고 해당 API로 어드민이 생성 해 줍니다.")
@RolesAllowed({"SUPER_ADMIN", "ADMIN"})
@PostMapping
public ResponseEntity<BaseRes<CreateUserRes>> addUser(@RequestBody CreateUserReq req) throws NoSuchAlgorithmException {
    CreateUserRes res = userAdminService.createUser(req);
    return ResponseEntity.ok().body(BaseRes.success(res));
}

@Operation(summary = "유저 업데이트", description = "유저 정보 업데이트 API 입니다.")
@RolesAllowed({"SUPER_ADMIN"})
@PutMapping
public ResponseEntity<BaseRes<UUID>> updateUserInfo(@RequestBody UpdateUserReq req) {
    UUID res = userAdminService.updateUserInfo(req);
    return ResponseEntity.ok().body(BaseRes.success(res));
}