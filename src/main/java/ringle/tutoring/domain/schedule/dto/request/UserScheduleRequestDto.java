package ringle.tutoring.domain.schedule.dto.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class UserScheduleRequestDto {

  private LocalDate startDate;
  private LocalDate endDate;
  private int lessonDuration;

}
