package ringle.tutoring.domain.schedule.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class TutorScheduleRequestDto {

  private List<Long> classTimeIds;

}
