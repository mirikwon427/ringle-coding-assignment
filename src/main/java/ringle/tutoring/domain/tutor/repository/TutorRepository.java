package ringle.tutoring.domain.tutor.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ringle.tutoring.domain.tutor.entity.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
  Optional<Tutor> findById(long tutorId);
}
