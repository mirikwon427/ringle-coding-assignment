package ringle.tutoring.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ringle.tutoring.domain.schedule.entity.ClassTime;
import ringle.tutoring.domain.schedule.entity.TutorSchedule;
import ringle.tutoring.domain.tutor.entity.Tutor;

@Repository
public interface TutorScheduleRepository extends JpaRepository<TutorSchedule, Long> {

  boolean existsByTutorAndClassTime(Tutor tutor, ClassTime classTime);
}
