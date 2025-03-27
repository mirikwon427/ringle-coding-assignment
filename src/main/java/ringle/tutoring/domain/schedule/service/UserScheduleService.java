package ringle.tutoring.domain.schedule.service;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import ringle.tutoring.domain.common.enums.Active;
import ringle.tutoring.domain.lesson.UserLesson;
import ringle.tutoring.domain.lesson.repository.UserLessonRepository;
import ringle.tutoring.domain.schedule.dto.request.UserScheduleRequestDto;
import ringle.tutoring.domain.schedule.dto.response.UserScheduleResponseDto;
import ringle.tutoring.domain.schedule.entity.ClassTime;
import ringle.tutoring.domain.schedule.repository.ClassTimeRepository;
import ringle.tutoring.domain.user.entity.User;
import ringle.tutoring.domain.user.repository.UserRepository;

@Service
public class UserScheduleService {

  private final UserRepository userRepository;
  private final UserLessonRepository userLessonRepository;
  private final ClassTimeRepository classTimeRepository;

  public UserScheduleService(UserRepository userRepository, UserLessonRepository userLessonRepository,
      ClassTimeRepository classTimeRepository) {
    this.userRepository = userRepository;
    this.userLessonRepository = userLessonRepository;
    this.classTimeRepository = classTimeRepository;
  }

  @Transactional
  public UserScheduleResponseDto getClassTime(long userId, UserScheduleRequestDto userScheduleRequestDto) {
    User user = findUser(userId);
    UserLesson userLesson = findByUser(user);

    validateUserLesson(userLesson, userScheduleRequestDto.getLessonDuration());

    Instant[] validPeriod = checkValidPeriod(userLesson, userScheduleRequestDto);
    Instant validStartDate = validPeriod[0];
    Instant validEndDate = validPeriod[1];

    List<ClassTime> classTimes = findAvailableClassTimes(validStartDate, validEndDate);

    return UserScheduleResponseDto.from(user, userLesson, classTimes);

  }

  private User findUser(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  private UserLesson findByUser(User user) {
    return userLessonRepository.findByUser(user)
        .orElseThrow(() -> new IllegalArgumentException("UserLesson not found"));
  }

  private void validateUserLesson(UserLesson userLesson, int requestedLessonDuration) {
    if (userLesson.getUserLessonStatus().equals(Active.active) &&
        userLesson.getLessonPackage().getLessonPackageduration() != requestedLessonDuration) {
      throw new IllegalArgumentException("보유한 수업권으로는 " + userLesson.getLessonPackage().getLessonPackageduration() +
          "분 수업만 예약할 수 있습니다.");
    }
  }

  private Instant[] checkValidPeriod(UserLesson userLesson, UserScheduleRequestDto userScheduleRequestDto) {
    Instant validStartDate = userLesson.getUserLessonStartDate().isAfter(userScheduleRequestDto.getStartDate())
        ? userLesson.getUserLessonStartDate()
        : userScheduleRequestDto.getStartDate();

    Instant validEndDate = userLesson.getUserLessonEndDate().isBefore(userScheduleRequestDto.getEndDate())
        ? userLesson.getUserLessonEndDate()
        : userScheduleRequestDto.getEndDate();

    if (validStartDate.isAfter(validEndDate)) {
      throw new IllegalArgumentException("유효한 기간이 없습니다.");
    }

    return new Instant[] {validStartDate, validEndDate};
  }

  private List<ClassTime> findAvailableClassTimes(Instant validStartDate, Instant validEndDate) {
    List<ClassTime> classTimes = classTimeRepository.findByClassStartTimeBetweenAndClassTimeIsAvailableTrue(validStartDate, validEndDate);

    if (classTimes.isEmpty()) {
      throw new IllegalArgumentException("시간이 없습니다.");
    }

    return classTimes;
  }
}
