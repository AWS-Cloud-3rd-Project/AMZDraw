package com.amzmall.project.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    @GetMapping("/")
    public String Home(Model model, Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            String email = makeEmail(authentication);
            model.addAttribute("email", email);
            return "result";
        }
        return "home";
    }

    public String makeEmail(Authentication authentication){
        String principal =authentication.getPrincipal().toString();
        int principalEmailIndex = principal.indexOf("email=");
        String email = principal.substring(principalEmailIndex+6, principal.length()-2);
        return email;
    }
}