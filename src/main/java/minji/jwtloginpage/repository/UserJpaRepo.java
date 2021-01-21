package minji.jwtloginpage.repository;

import minji.jwtloginpage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);
}
