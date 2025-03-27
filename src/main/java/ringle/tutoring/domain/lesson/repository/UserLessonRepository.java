package ringle.tutoring.domain.lesson.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ringle.tutoring.domain.common.enums.Active;
import ringle.tutoring.domain.lesson.entity.UserLesson;
import ringle.tutoring.domain.user.entity.User;

public interface UserLessonRepository extends JpaRepository<UserLesson, Long> {
  Optional<UserLesson> findByUser(User user);
  Optional<UserLesson>findByUserAndUserLessonStatus(User user, Active userLessonStatus);
}
