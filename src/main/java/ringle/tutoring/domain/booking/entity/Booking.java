package ringle.tutoring.domain.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ringle.tutoring.domain.booking.enums.BookingStatus;
import ringle.tutoring.domain.common.entity.BaseTimeEntity;
import ringle.tutoring.domain.schedule.entity.TutorSchedule;
import ringle.tutoring.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookings")
public class Booking extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="booking_id")
  private long bookingId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tutor_schedule_id")
  private TutorSchedule tutorSchedule;

  @Column(name = "booking_duration", nullable = false)
  private int bookingDuration = 0;

  @Column(name = "booking_status", nullable = false)
  private BookingStatus bookingStatus = BookingStatus.confirmed;

  public Booking(User user, TutorSchedule tutorSchedule, int bookingDuration) {
    this.user = user;
    this.tutorSchedule = tutorSchedule;
    this.bookingDuration = bookingDuration;
  }

}
