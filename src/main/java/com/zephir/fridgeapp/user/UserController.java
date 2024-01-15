package com.zephir.fridgeapp.user;

import com.zephir.fridgeapp.event.RegistrationCompleteEvent;
import com.zephir.fridgeapp.exception.UserNotFoundException;
import com.zephir.fridgeapp.exception.UserPasswordError;
import com.zephir.fridgeapp.fridge.Fridge;
import com.zephir.fridgeapp.registration.token.VerificationToken;
import com.zephir.fridgeapp.registration.token.VerificationTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/all")
    public List<User> getUsers(){

        return userService.getUsers();
    }

    @PostMapping("/get")
    public ResponseEntity getUser(@RequestBody Map<String, String> user) {
        try {
            System.out.println(user);
            User loggedInUser = userService.loginUser(user.get("email"), user.get("password"));
            return ResponseEntity.ok(loggedInUser);
        } catch (UserPasswordError e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/fridge")
    public ResponseEntity<?> getUserFridge(@PathVariable Long userId) {
        Fridge fridge = userService.getUserFridge(userId);
        if (fridge != null) {
            return ResponseEntity.ok(fridge);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fridge not found for User ID: " + userId);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshUserToken(@RequestParam Long userId, final HttpServletRequest request) {
        try {
            User user = userService.getUserById(userId);

            publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

            return ResponseEntity.ok("Token refreshed successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error refreshing token");
        }
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


    @GetMapping("/test")
    public User test(){
        return userService.findByEmail("aleksand.kuz@gmail.com").get();
    }
}