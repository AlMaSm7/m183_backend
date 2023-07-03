package ch.bbw.pwd_manager.service;

import ch.bbw.pwd_manager.config.JwtService;
import ch.bbw.pwd_manager.model.User;
import ch.bbw.pwd_manager.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepo userRepo;
    private final JwtService jwtService;


    public String login(String userName, String password) {
        Optional<User> loginUser = userRepo.findUserByUsername(userName);
        if (loginUser.isPresent()) {
            User user = loginUser.get();
            String loginPassword = hashPassword(user, password); // Hash the user-provided password
            // Compare the hashed password with the stored hashed password
            if (loginPassword.equals(user.getPassword())) {
                return jwtService.generateToken(user.toExtraClaim(), user);
            } else {
                return "invalid";
            }
        } else {
            return "invalid";
        }
    }


    public String registerUser(User user) {
        if (userRepo.findUserByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(hashPassword(user, user.getPassword()));
            userRepo.save(user);
            return jwtService.generateToken(user.toExtraClaim(), user);
        } else {
            return "User not found";
        }
    }

    public void editUser(User user) {
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(String userName) {
        // TODO
    }

    private String hashPassword(User user, String passwordToHash) {
        String salt = user.getSalt();
        if (salt == null) {
            salt = BCrypt.gensalt();
            user.setSalt(salt); // Store the newly generated salt in the User object
        }

        String pepper = "fjksjfaklsdjfuLnbfodjsfhüo";
        String saltedPassword = salt + passwordToHash + pepper;

        return BCrypt.hashpw(saltedPassword, salt);
    }
}
