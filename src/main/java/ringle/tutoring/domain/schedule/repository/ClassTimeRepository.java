package ringle.tutoring.domain.schedule.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ringle.tutoring.domain.schedule.entity.ClassTime;

public interface ClassTimeRepository extends JpaRepository<ClassTime, Long> {
  Optional<ClassTime> findByClassTimeId(long classTimeId);
}
