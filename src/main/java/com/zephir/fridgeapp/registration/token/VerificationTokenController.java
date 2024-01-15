package com.zephir.fridgeapp.registration.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/verification-token")
public class VerificationTokenController {

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenController(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @GetMapping("/user/{userId}")
    public VerificationToken getVerificationTokenByUserId(@PathVariable Long userId) {
        VerificationToken verificationToken = verificationTokenRepository.findByUser_Id(userId);
        return verificationToken;
    }
}
