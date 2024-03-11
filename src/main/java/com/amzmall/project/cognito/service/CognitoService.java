package com.amzmall.project.cognito.service;

import com.amzmall.project.cognito.entity.User;
import com.amzmall.project.cognito.repository.UserRepository;
import com.amzmall.project.config.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import software.amazon.awssdk.utils.ImmutableMap;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CognitoService {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${aws.cognito.pool-id}")
    private final String userPoolId = "ap-northeast-2_qD0HzA3fK";

    @Value("${aws.cognito.client-id}")
    private final String clientId = "70aa9cojdarvlrj6e2hubvcckh";

    @Autowired
    private UserRepository userRepository;

    private final CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder().build();

    public SignUpResponse signUp(String username, String password) {
        userRepository.save(new User(username, password));
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .clientId(clientId)
                .username(username)
                .password(password)
                .build();
        SignUpResponse signUpResponse = cognitoClient.signUp(signUpRequest);
        AdminConfirmSignUpRequest confirmSignUpRequest = AdminConfirmSignUpRequest.builder()
                .userPoolId(userPoolId)
                .username(username)
                .build();
        cognitoClient.adminConfirmSignUp(confirmSignUpRequest);
        return signUpResponse;
    }

    public String login(String username, String password) {
        try {
            InitiateAuthRequest initiateAuthRequest = InitiateAuthRequest.builder()
                    .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .clientId(clientId)
                    .authParameters(ImmutableMap.of(
                            "USERNAME", username,
                            "PASSWORD", password
                    ))
                    .build();

            InitiateAuthResponse initiateAuthResponse = cognitoClient.initiateAuth(initiateAuthRequest);

            if (initiateAuthResponse.authenticationResult() != null) {
                AuthenticationResultType authResult = initiateAuthResponse.authenticationResult();
                return authResult.accessToken();
            } else {
                throw new RuntimeException("Invalid username or password");
            }
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Authentication failed: " + e.awsErrorDetails().errorMessage());
        }
    }

    public void logout(String accessToken) {
        try {
            GlobalSignOutRequest globalSignOutRequest = GlobalSignOutRequest.builder()
                    .accessToken(accessToken)
                    .build();

            GlobalSignOutResponse globalSignOutResponse = cognitoClient.globalSignOut(globalSignOutRequest);

            if (!globalSignOutResponse.sdkHttpResponse().isSuccessful()) {
                throw new RuntimeException("Logout failed");
            }
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Logout failed: " + e.awsErrorDetails().errorMessage());
        }
    }

    public UserDetails loadUserByJwt(String jwt) {
        // 토큰 유효성 검사
        if (!jwtUtil.validateToken(jwt)) {
            throw new RuntimeException("Invalid JWT token");
        }

        // JWT에서 사용자 이름 추출
        String username = jwtUtil.getUsernameFromToken(jwt);

        // Cognito 서비스의 API를 사용하여 사용자 정보 가져오기
        GetUserRequest getUserRequest = GetUserRequest.builder()
                .accessToken(jwt) // 사용자 정보를 가져올 때 토큰을 사용합니다.
                .build();

        GetUserResponse response = cognitoClient.getUser(getUserRequest);

        // 가져온 사용자 정보로 UserDetails 객체 생성
        UserDetails userDetails = buildUserDetailsFromCognitoResponse(response);

        return userDetails;
    }

    private UserDetails buildUserDetailsFromCognitoResponse(GetUserResponse response) {
        // 가져온 사용자 정보로 UserDetails 객체를 생성
        String username = response.username();

        // 사용자의 권한 정보를 설정하지 않고 UserDetails 객체를 생성합니다.
        return new org.springframework.security.core.userdetails.User(
                username,
                "", // 비밀번호 필드는 비워둡니다.
                Collections.emptyList()); // 권한을 비어있는 리스트로 설정
    }
}