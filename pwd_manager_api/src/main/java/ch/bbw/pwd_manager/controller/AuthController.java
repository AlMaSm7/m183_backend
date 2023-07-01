package ch.bbw.pwd_manager.controller;

import ch.bbw.pwd_manager.dto.LoginForm;
import ch.bbw.pwd_manager.exceptions.DuplicateUserException;
import ch.bbw.pwd_manager.exceptions.LoginException;
import ch.bbw.pwd_manager.model.User;
import ch.bbw.pwd_manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginForm loginForm) {
        String token = userService.login(loginForm.getUsername(), loginForm.getPassword());

        if (token.equals("invalid")) {
            throw new LoginException("Login unsuccessful: Invalid username or password");
        } else {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("auth_token", token);
            return ResponseEntity.ok().body(responseBody);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        String token = userService.registerUser(user);
        if (!token.equals("User not found")) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("auth_token", token);
            return ResponseEntity.ok().body(responseBody);
        } else {
            throw new DuplicateUserException("Username: " + user.getUsername() + " already exists");
        }
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> handleLoginFailedException(LoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> duplicateUsernameException(DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
