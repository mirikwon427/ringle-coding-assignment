package ringle.tutoring.domain.schedule.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;
import ringle.tutoring.domain.schedule.entity.ClassTime;
import ringle.tutoring.domain.schedule.entity.TutorSchedule;
import ringle.tutoring.domain.schedule.repository.ClassTimeRepository;
import ringle.tutoring.domain.schedule.repository.TutorScheduleRepository;
import ringle.tutoring.domain.tutor.entity.Tutor;
import ringle.tutoring.domain.tutor.repository.TutorRepository;

@Service
public class TutorScheduleService {

  private final TutorScheduleRepository tutorScheduleRepository;
  private final TutorRepository tutorRepository;
  private final ClassTimeRepository classTimeRepository;

  public TutorScheduleService(TutorScheduleRepository tutorScheduleRepository, TutorRepository tutorRepository,
      ClassTimeRepository classTimeRepository) {
    this.tutorScheduleRepository = tutorScheduleRepository;
    this.tutorRepository = tutorRepository;
    this.classTimeRepository = classTimeRepository;
  }

  @Transactional
  public void createTutorSchedule(long tutorId, List<Long> classTimeIds) {
    Tutor tutor = tutorRepository.findById(tutorId)
        .orElseThrow(() -> new IllegalArgumentException("Tutor not found"));

    for (long classTimeId : classTimeIds) {

      ClassTime classTime = classTimeRepository.findByClassTimeId(classTimeId)
          .orElseThrow(() -> new IllegalArgumentException("ClassTime not found"));

      TutorSchedule tutorSchedule = new TutorSchedule(tutor, classTime);
      tutorScheduleRepository.save(tutorSchedule);
    }
  }
}
