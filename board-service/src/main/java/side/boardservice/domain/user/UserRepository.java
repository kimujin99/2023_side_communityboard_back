package side.boardservice.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNickname(String userNickname);

    User findByUserEmailId(String userEmailId);
}
