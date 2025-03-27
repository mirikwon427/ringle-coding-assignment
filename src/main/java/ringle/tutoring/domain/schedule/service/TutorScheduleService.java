package ringle.tutoring.domain.schedule.service;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ringle.tutoring.domain.schedule.dto.request.GetTutorScheduleRequestDto;
import ringle.tutoring.domain.schedule.dto.response.GetTutorScheduleResponseDto;
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

  @Transactional
  public GetTutorScheduleResponseDto getTutorSchedules(GetTutorScheduleRequestDto requestDto) {
    ClassTime currentClassTime = findClassTime(requestDto.getClassTimeId());
    List<TutorSchedule> tutorSchedules;

    if (requestDto.getLessonDuration() == 20) {
      tutorSchedules = getTutorSchedulesFor20Minutes(currentClassTime);
    } else if (requestDto.getLessonDuration() == 40) {
      tutorSchedules = getTutorSchedulesFor40Minutes(currentClassTime, requestDto.getClassTimeId());
    } else {
      return new GetTutorScheduleResponseDto(
          currentClassTime.getClassTimeId(),
          currentClassTime.getClassStartTime(),
          Collections.emptyList()
      );
    }

    List<GetTutorScheduleResponseDto.TutorInfo> tutorInfos = convertToTutorInfoList(tutorSchedules);
    return new GetTutorScheduleResponseDto(
        currentClassTime.getClassTimeId(),
        currentClassTime.getClassStartTime(),
        tutorInfos
    );
  }

  private List<TutorSchedule> getTutorSchedulesFor20Minutes(ClassTime currentClassTime) {
    return tutorScheduleRepository.findByClassTimeAndTutorScheduleIsAvailableTrue(currentClassTime);
  }

  private List<TutorSchedule> getTutorSchedulesFor40Minutes(ClassTime currentClassTime, int currentClassTimeId) {
    // 다음 클래스타임 아이디 조회 (예: 현재 id + 1)
    ClassTime nextClassTime = findClassTime(currentClassTimeId + 1);
    if (nextClassTime == null) {
      // 다음 클래스타임이 없으면 40분 수업은 제공할 수 없음
      return Collections.emptyList();
    }

    // 현재 클래스타임과 다음 클래스타임 모두 가능한 튜터만 필터링
    List<TutorSchedule> currentSchedules = tutorScheduleRepository.findByClassTimeAndTutorScheduleIsAvailableTrue(currentClassTime);
    List<TutorSchedule> nextSchedules = tutorScheduleRepository.findByClassTimeAndTutorScheduleIsAvailableTrue(nextClassTime);

    Set<Long> nextTutorIds = nextSchedules.stream()
        .map(ts -> ts.getTutor().getTutorId())
        .collect(Collectors.toSet());

    return currentSchedules.stream()
        .filter(ts -> nextTutorIds.contains(ts.getTutor().getTutorId()))
        .collect(Collectors.toList());
  }

  private List<GetTutorScheduleResponseDto.TutorInfo> convertToTutorInfoList(List<TutorSchedule> tutorSchedules) {
    return tutorSchedules.stream()
        .map(ts -> {
          Tutor tutor = ts.getTutor();
          return new GetTutorScheduleResponseDto.TutorInfo(
              ts.getTutorScheduleId(),
              tutor.getTutorId(),
              tutor.getTutorName(),
              tutor.getTutorUniversity(),
              tutor.getTutorMajor()
          );
        })
        .collect(Collectors.toList());
  }


}
