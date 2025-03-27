package ringle.tutoring.domain.schedule.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ringle.tutoring.domain.schedule.dto.response.TutorScheduleResponseDto;
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

  public TutorScheduleService(TutorScheduleRepository tutorScheduleRepository,
      TutorRepository tutorRepository,
      ClassTimeRepository classTimeRepository) {
    this.tutorScheduleRepository = tutorScheduleRepository;
    this.tutorRepository = tutorRepository;
    this.classTimeRepository = classTimeRepository;
  }

  @Transactional
  public TutorScheduleResponseDto createTutorSchedule(long tutorId, List<Long> classTimeIds) {
    Tutor tutor = tutorRepository.findById(tutorId)
        .orElseThrow(() -> new IllegalArgumentException("Tutor not found"));

    List<TutorSchedule> tutorSchedules = new ArrayList<>();

    for (long classTimeId : classTimeIds) {
      // ClassTime 찾기와 중복 여부 확인
      ClassTime classTime = findClassTimeAndCheckExistence(classTimeId, tutor);

      // TutorSchedule 생성 및 저장
      TutorSchedule tutorSchedule = createAndSaveTutorSchedule(tutor, classTime);

      tutorSchedules.add(tutorSchedule);
    }

    return TutorScheduleResponseDto.from(tutor, tutorSchedules);
  }

  private ClassTime findClassTimeAndCheckExistence(long classTimeId, Tutor tutor) {
    ClassTime classTime = classTimeRepository.findByClassTimeId(classTimeId)
        .orElseThrow(() -> new IllegalArgumentException("ClassTime not found"));

    boolean alreadyExists = tutorScheduleRepository.existsByTutorAndClassTime(tutor, classTime);
    if (alreadyExists) {
      throw new IllegalArgumentException("ClassTime already exists");
    }

    return classTime;
  }

  private TutorSchedule createAndSaveTutorSchedule(Tutor tutor, ClassTime classTime) {
    TutorSchedule tutorSchedule = new TutorSchedule(tutor, classTime);
    tutorScheduleRepository.save(tutorSchedule);
    return tutorSchedule;
  }

}
