package ringle.tutoring.domain.schedule.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ringle.tutoring.domain.common.util.TimeZoneUtil;
import ringle.tutoring.domain.schedule.entity.TutorSchedule;
import ringle.tutoring.domain.tutor.entity.Tutor;

@Getter
@AllArgsConstructor
public class TutorScheduleResponseDto {
  private long tutorId;
  private String tutorName;
  private String tutorTimeZone;
  private List<TutorScheduleDetails> schedules;

  // 엔티티 -> DTO 변환
  public static TutorScheduleResponseDto from(Tutor tutor, List<TutorSchedule> tutorSchedules) {
    List<TutorScheduleDetails> scheduleDetails = tutorSchedules.stream()
        .map(tutorSchedule -> new TutorScheduleDetails(
            tutorSchedule.getTutorScheduleId(),
            tutorSchedule.getClassTime().getClassTimeId(),
            TimeZoneUtil.convertUtcToLocalTime(tutorSchedule.getClassTime().getClassStartTime(), tutor.getTutorTimeZone())
        ))
        .collect(Collectors.toList());

    return new TutorScheduleResponseDto(
        tutor.getTutorId(),
        tutor.getTutorName(),
        tutor.getTutorTimeZone(),
        scheduleDetails
    );
  }

  @Getter
  @AllArgsConstructor
  public static class TutorScheduleDetails {
    private long tutorScheduleId;
    private long classTimeId;
    private String classStartTime;
  }


}
