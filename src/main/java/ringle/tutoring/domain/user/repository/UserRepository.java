package ringle.tutoring.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ringle.tutoring.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(long userId);
}
