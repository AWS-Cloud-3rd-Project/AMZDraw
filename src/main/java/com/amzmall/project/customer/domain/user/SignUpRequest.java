package com.amzmall.project.customer.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
public class SignUpRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    private String confirmPassword;

    private String phoneNumber;

    public SignUpRequest(String email, String password, String confirmPassword, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        checkCapsLock();
    }
    private void checkCapsLock() {
        boolean capsLockOn = Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
        if (capsLockOn) {
            System.out.println("Caps Lock이 켜져 있습니다.");
        }
    }

    public String validatePasswordConfirmation() {
        if (!Objects.equals(password, confirmPassword)) {
            return "비밀번호가 일치하지 않습니다.";
        }
        return null;
    }

    public String validatePhoneNumber() {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "전화번호를 입력해 주세요.";
        }

        String phoneNumberPattern = "^[0-9]{9,11}$";
        if (!phoneNumber.matches(phoneNumberPattern)) {
            return "전화번호 형식이 올바르지 않습니다. 숫자만 입력하세요.";
        }

        return null;
    }
}
