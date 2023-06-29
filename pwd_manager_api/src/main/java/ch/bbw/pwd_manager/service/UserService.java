package ch.bbw.pwd_manager.service;

import ch.bbw.pwd_manager.model.User;
import ch.bbw.pwd_manager.repositories.RecordRepo;
import ch.bbw.pwd_manager.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final RecordRepo recordRepo;

    @Autowired
    public UserService(UserRepo userRepo, RecordRepo recordRepo) {
        this.userRepo = userRepo;
        this.recordRepo = recordRepo;
    }

    public boolean login(String userName, String password) {
        System.out.println(userName);
        User loginUser = userRepo.findUserByUsername(userName);

        if(loginUser != null){
            String loginPassword = hashPassword(password);
            System.out.println(loginPassword);
            return loginPassword.equals(loginUser.getPassword());
        } else {
            return false;
        }
    }

    public void registerUser(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        userRepo.save(user);
    }

    public void editUser(User user) {
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(String userName) {
        // TODO
    }

    private String hashPassword(String password) {
        // TODO: Save the Salt value to user db!
        String salt = BCrypt.gensalt();
        String pepper = "fjksjfaklsdjfuLnbfodjsfhüo";
        String saltedPassword = salt + password + pepper;

        return BCrypt.hashpw(saltedPassword, salt);
    }
}
