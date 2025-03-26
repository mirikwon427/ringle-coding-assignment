package ringle.tutoring.domain.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ringle.tutoring.domain.tutor.Tutor;

@Entity
@Table(name = "tutor_schedules")
public class TutorSchedule {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tutor_schedule_id")
  private long tutorScheduleId;

  @ManyToOne
  @JoinColumn(name = "tutor_id")
  private Tutor tutorId;

  @ManyToOne
  @JoinColumn(name = "class_time_id")
  private ClassTime classTimeId;

  @Column(name = "tutor_id", nullable = false)
  private Boolean tutorScheduleIsAvailable = true;
}
