package com.amzmall.project.users.controller;

import com.amzmall.project.users.domain.dto.UsersResDto;
import com.amzmall.project.users.domain.entity.Users;
import com.amzmall.project.users.repository.UsersRepository;
import com.amzmall.project.users.service.UsersService;
import com.amzmall.project.util.dto.ListResult;
import com.amzmall.project.util.dto.SingleResult;
import com.amzmall.project.util.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class usersController {

    private UsersRepository usersRepository;
    private final UsersService usersService;
    private final ResponseService responseService;

    @GetMapping(value = "/{id}")
    @Operation(summary="회원 번호로 조회", description="회원 번호로 회원을 조회합니다.")
    public SingleResult<UsersResDto> getUser(
        @Parameter(name = "id", description = "회원 번호", required = true)
        @PathVariable("id") int id) {
        return responseService.getSingleResult(usersService.findById(id));
    }

    @GetMapping("/find/id")
    @Operation(summary="회원 번호 조회", description="이메일로 회원의 번호를 조회합니다.")
    public SingleResult<Integer> findUserId(
        @Parameter(name = "email", description = "회원 이메일", required = true)
        @RequestParam String email
    ) {
        return responseService.getSingleResult(usersService.findUserId(email));
    }

    @GetMapping("/all")
    @Operation(summary="모든 회원 조회", description="모든 회원을 조회합니다.")
    public ListResult<UsersResDto> findAllUsers() {
        List<UsersResDto> allByName = usersService.findAll();
        return responseService.getListResult(allByName);
    }

    @GetMapping("/all/active")
    @Operation(summary="모든 활동 회원 조회", description="활동 중인 모든 회원을 조회합니다.")
    public ListResult<UsersResDto> findAllActiveUsers() {
        List<UsersResDto> allActive = usersService.findAllActiveUsers();
        return responseService.getListResult(allActive);
    }

    @GetMapping("/all/deActive")
    @Operation(summary="모든 비활동 회원 조회", description="활동 중이지 않은 모든 회원을 조회합니다.")
    public ListResult<UsersResDto> findAllDeActiveUsers() {
        List<UsersResDto> allActive = usersService.findAllDeActiveUsers();
        return responseService.getListResult(allActive);
    }

    @GetMapping("/email")
    @Operation(summary="이메일로 조회", description="이메일로 회원을 조회합니다.")
    public SingleResult<UsersResDto> findUsersByEmail(
        @Parameter(name = "email", description = "회원 이메일", required = true)
        @RequestParam(name = "email") String email
    ) {
        UsersResDto targetEmail = usersService.findByEmail(email);
        return responseService.getSingleResult(targetEmail);
    }
}
