package pl.stormit.eduquiz.quizcreator.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByNickname(String nickname);

    Optional<List<User>> findByNicknameContainingIgnoreCase(String nickname);

    Optional<List<User>> findByEmailContainingIgnoreCase(String email);

    long count();

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :date")
    long countNewUsersLast30Days(@Param("date") Instant date);
}
