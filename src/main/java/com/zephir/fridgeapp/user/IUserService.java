package com.zephir.fridgeapp.user;

import com.zephir.fridgeapp.registration.RegistrationRequest;
import com.zephir.fridgeapp.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();

    User registerUser(RegistrationRequest request);

    Optional<User> findByEmail(String email);

    User getUser(String email);

    void saveUserVerificetionToken(User user, String verificationToken);

    String validateToken(String theToken);
}
