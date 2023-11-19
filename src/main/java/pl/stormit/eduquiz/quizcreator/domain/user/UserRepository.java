package pl.stormit.eduquiz.quizcreator.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByNickname(String nickname);

    Optional<List<User>> findByNicknameContainingIgnoreCase(String nickname);

    Optional<List<User>> findByEmailContainingIgnoreCase(String email);
}
