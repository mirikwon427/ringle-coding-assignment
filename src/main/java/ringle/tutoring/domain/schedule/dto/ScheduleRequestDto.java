package ringle.tutoring.domain.schedule.dto;

import java.util.List;

public class ScheduleRequestDto {

  private List<Long> classTimeIds;

  public List<Long> getClassTimeIds() {
    return classTimeIds;
  }

  public void setClassTimeIds(List<Long> classTimeIds) {
    this.classTimeIds = classTimeIds;
  }

}
