package ringle.tutoring.domain.schedule.dto.response;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTutorScheduleResponseDto {
  private long classTimeId;
  private Instant classStartTime;
  private List<TutorInfo> tutors;

  @Getter
  @AllArgsConstructor
  public static class TutorInfo {
    private long tutorId;
    private String tutorName;
    private String tutorUniversity;
    private String tutorMajor;
  }
}
