package ringle.tutoring.domain.lesson.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import ringle.tutoring.domain.common.entity.BaseTimeEntity;
import ringle.tutoring.domain.common.enums.Active;
import ringle.tutoring.domain.user.entity.User;

@Entity
@Getter
@Table(name = "user_lessons")
public class UserLesson extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_lesson_id")
  private long userLessonId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id")
  private LessonPackage lessonPackage;

  @Column(name = "user_lesson_purchased_at", nullable = false)
  private Instant userLessonPurchasedAt;

  @Column(name = "user_lesson_start_date", nullable = false)
  private Instant userLessonStartDate;

  @Column(name = "user_lesson_end_date", nullable = false)
  private Instant userLessonEndDate;

  @Column(name = "user_lesson_remained", nullable = false)
  private int userLessonRemained = 0;

  @Column(name = "user_lesson_status", nullable = false)
  private Active userLessonStatus = Active.active;

}
