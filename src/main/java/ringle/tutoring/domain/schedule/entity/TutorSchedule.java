package ringle.tutoring.domain.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ringle.tutoring.domain.tutor.entity.Tutor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tutor_schedules")
public class TutorSchedule {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tutor_schedule_id")
  private long tutorScheduleId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tutor_id")
  private Tutor tutor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_time_id")
  private ClassTime classTime;

  @Column(name = "tutor_schedule_is_available", nullable = false)
  private Boolean tutorScheduleIsAvailable = true;

  public TutorSchedule(Tutor tutor, ClassTime classTime){
    this.tutor = tutor;
    this.classTime = classTime;
  }

  // 상태 업데이트 메서드 추가
  public void setTutorScheduleIsAvailable(Boolean tutorScheduleIsAvailable) {
    this.tutorScheduleIsAvailable = tutorScheduleIsAvailable;
  }
}
