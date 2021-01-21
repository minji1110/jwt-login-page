package minji.jwtloginpage.repository;

import minji.jwtloginpage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User,Long> {
}
