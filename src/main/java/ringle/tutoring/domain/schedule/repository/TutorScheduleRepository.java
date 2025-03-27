package ringle.tutoring.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ringle.tutoring.domain.schedule.entity.TutorSchedule;

@Repository
public interface TutorScheduleRepository extends JpaRepository<TutorSchedule, Long> {

}
