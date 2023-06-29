public interface UserRepository extends JpaRepository<User, String>{
    User findByUsername(String username);
}