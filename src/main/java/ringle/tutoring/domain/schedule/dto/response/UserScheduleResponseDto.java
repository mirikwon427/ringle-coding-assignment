package ringle.tutoring.domain.schedule.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ringle.tutoring.domain.common.util.TimeZoneUtil;
import ringle.tutoring.domain.lesson.UserLesson;
import ringle.tutoring.domain.schedule.entity.ClassTime;
import ringle.tutoring.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class UserScheduleResponseDto {

  private long userId;
  private String userName;
  private String userTimeZone;
  private int userLessonDuration;
  private List<ClassTimeDetails> classTimes;

  // 내부 DTO for classTime details
  @Getter
  public static class ClassTimeDetails {
    private long classTimeId;
    private String classStartTime;

    public ClassTimeDetails(long classTimeId, String classStartTime) {
      this.classTimeId = classTimeId;
      this.classStartTime = classStartTime;
    }
  }

  // DTO 변환 메소드
  public static UserScheduleResponseDto from(User user, UserLesson userLesson, List<ClassTime> classTimes) {
    List<ClassTimeDetails> classTimeDetails = classTimes.stream()
        .map(classTime -> new ClassTimeDetails(
            classTime.getClassTimeId(),
            TimeZoneUtil.convertUtcToLocalTime(classTime.getClassStartTime(), user.getUserTimeZone())
        ))
        .collect(Collectors.toList());

    return new UserScheduleResponseDto(
        user.getUserId(),
        user.getUserName(),
        user.getUserTimeZone(),
        userLesson.getLessonPackage().getLessonPackageduration(),
        classTimeDetails
    );
  }
}

