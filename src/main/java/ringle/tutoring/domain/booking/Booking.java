package ringle.tutoring.domain.booking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ringle.tutoring.domain.common.entity.BaseTimeEntity;
import ringle.tutoring.domain.schedule.TutorSchedule;
import ringle.tutoring.domain.user.User;

@Entity
@Table(name = "bookings")
public class Booking extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="booking_id")
  private long bookingId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tutor_schedule_id")
  private TutorSchedule tutorSchedule;

  @Column(name = "booking_duration", nullable = false)  // 분
  private int bookingDuration = 0;

  @Column(name = "booking_status", nullable = false)  // 시간
  private BookingStatus bookingStatus = BookingStatus.confirmed;

}
