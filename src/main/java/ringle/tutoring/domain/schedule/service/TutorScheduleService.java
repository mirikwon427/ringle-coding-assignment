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
    Tutor tutor = findTutor(tutorId);
    List<TutorSchedule> tutorSchedules = new ArrayList<>();

    for (long classTimeId : classTimeIds) {
      ClassTime classTime = findClassTime(classTimeId);
      // 튜터 스케줄 존재 여부 확인 ( 존재하는 경우 예외 발생 )
      checkIfTutorScheduleExists(tutor, classTime, true);

      TutorSchedule tutorSchedule = new TutorSchedule(tutor, classTime);
      tutorScheduleRepository.save(tutorSchedule);

      tutorSchedules.add(tutorSchedule);
    }

    return TutorScheduleResponseDto.from(tutor, tutorSchedules);
  }

  private Tutor findTutor(long tutorId) {
    return tutorRepository.findById(tutorId)
        .orElseThrow(() -> new IllegalArgumentException("Tutor not found"));
  }

  private ClassTime findClassTime(long classTimeId) {
    return classTimeRepository.findByClassTimeId(classTimeId)
        .orElseThrow(() -> new IllegalArgumentException("ClassTime not found"));
  }

  private void checkIfTutorScheduleExists(Tutor tutor, ClassTime classTime, boolean checkIfExists) {
    boolean alreadyExists = tutorScheduleRepository.existsByTutorAndClassTime(tutor, classTime);
    if (checkIfExists && alreadyExists) {
      throw new IllegalArgumentException("TutorSchedule already exists");
    } else if (!checkIfExists && !alreadyExists) {
      throw new IllegalArgumentException("No TutorSchedule found for the given Tutor and ClassTime");
    }
  }

  @Transactional
  public void deleteTutorSchedule(long tutorId, List<Long> classTimeIds) {
    Tutor tutor = findTutor(tutorId);

    for (long classTimeId : classTimeIds) {
      ClassTime classTime = findClassTime(classTimeId);
      // 튜터 스케줄 존재 여부 확인 ( 존재하지 않는 경우 예외 발생 )
      checkIfTutorScheduleExists(tutor, classTime, false);

      // TutorSchedule 삭제
      tutorScheduleRepository.deleteByTutorAndClassTime(tutor, classTime);

    }
  }

}
