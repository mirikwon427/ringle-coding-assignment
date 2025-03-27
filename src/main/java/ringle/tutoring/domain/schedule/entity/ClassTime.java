package ringle.tutoring.domain.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
@Table(name = "class_times")
public class ClassTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "class_time_id")
  private long classTimeId;

  @Column(name = "class_start_time", nullable = false)
  private LocalDateTime classStartTime;

  @Column(name = "class_time_is_available", nullable = false)
  private Boolean classTimeIsAvailable = true;
}