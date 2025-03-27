package ringle.tutoring.domain.schedule.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ringle.tutoring.domain.common.enums.Active;
import ringle.tutoring.domain.lesson.UserLesson;
import ringle.tutoring.domain.lesson.repository.UserLessonRepository;
import ringle.tutoring.domain.schedule.dto.request.UserScheduleRequestDto;
import ringle.tutoring.domain.schedule.dto.response.TutorScheduleResponseDto;
import ringle.tutoring.domain.schedule.dto.response.UserScheduleResponseDto;
import ringle.tutoring.domain.schedule.entity.ClassTime;
import ringle.tutoring.domain.schedule.entity.TutorSchedule;
import ringle.tutoring.domain.user.entity.User;
import ringle.tutoring.domain.user.repository.UserRepository;

@Service
public class UserScheduleService {

  private final UserRepository userRepository;
  private final UserLessonRepository userLessonRepository;

  public UserScheduleService(UserRepository userRepository, UserLessonRepository userLessonRepository) {
    this.userRepository = userRepository;
    this.userLessonRepository = userLessonRepository;
  }

  @Transactional
  public UserScheduleResponseDto getClassTime(long userId, UserScheduleRequestDto userScheduleRequestDto) {
    User user = findUser(userId);
    UserLesson userLesson = findByUser(user);
    List<ClassTime> classTimes = new ArrayList<>();

    if (userLesson.getUserLessonStatus().equals(Active.active) &&
        userLesson.getLessonPackage().getLessonPackageduration() != userScheduleRequestDto.getLessonDuration()) {
      throw new IllegalArgumentException("보유한 수업권으로는 " + userLesson.getLessonPackage().getLessonPackageduration() +
          "분 수업만 예약할 수 있습니다.");
    }


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
}
