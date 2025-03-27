package ringle.tutoring.domain.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "class_times")
public class ClassTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "class_time_id")
  private long classTimeId;

  @Column(name = "class_start_time")
  private String classStartTime;

  @Column(name = "class_time_is_available")
  private Boolean classTimeIsAvailable = true;
}
