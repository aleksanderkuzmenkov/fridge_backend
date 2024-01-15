package com.zephir.fridgeapp.registration;

import com.zephir.fridgeapp.event.RegistrationCompleteEvent;
import com.zephir.fridgeapp.registration.token.VerificationToken;
import com.zephir.fridgeapp.registration.token.VerificationTokenRepository;
import com.zephir.fridgeapp.user.User;
import com.zephir.fridgeapp.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request){
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success Please, check your email";
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);
        if(theToken.getUser().isEnabled()){
            return "Your account has already verified, please login";
        }
        String verificationResult = userService.validateToken(token);

        if(verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login";
        }

        return "Invalid verification token";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
