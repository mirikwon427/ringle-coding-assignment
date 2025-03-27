package ringle.tutoring.domain.schedule.dto.request;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTutorScheduleRequestDto {

  private int classTimeId;
  private int lessonDuration;

}
