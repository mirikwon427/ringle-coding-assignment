package ringle.tutoring.domain.schedule.dto.request;

import java.time.Instant;
import lombok.Getter;

@Getter
public class UserScheduleRequestDto {

  private Instant startDate;
  private Instant endDate;
  private int lessonDuration;

}
