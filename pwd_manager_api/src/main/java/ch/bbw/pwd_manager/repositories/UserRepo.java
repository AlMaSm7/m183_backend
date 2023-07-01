package ch.bbw.pwd_manager.repositories;

import ch.bbw.pwd_manager.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
