package ch.bbw.pwd_manager.controller;

import ch.bbw.pwd_manager.dto.LoginForm;
import ch.bbw.pwd_manager.exceptions.LoginException;
import ch.bbw.pwd_manager.model.User;
import ch.bbw.pwd_manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        boolean isAuthenticated = userService.login(loginForm.getUsername(), loginForm.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            throw new LoginException("Login unsuccessful: Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User was onboarded");
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> handleLoginFailedException(LoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}
