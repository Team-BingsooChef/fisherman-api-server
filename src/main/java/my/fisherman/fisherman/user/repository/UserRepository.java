package my.fisherman.fisherman.user.repository;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

import java.util.Optional;
import my.fisherman.fisherman.user.domain.OAuthProvider;
import my.fisherman.fisherman.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.oauthType = :oauthType")
    Optional<User> findUserByEmailWithWriteLock(String email, OAuthProvider oauthType);

    Optional<User> findUserByEmailAndOauthType(String email, OAuthProvider oauthType);
}