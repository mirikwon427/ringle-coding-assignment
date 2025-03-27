package ringle.tutoring.domain.booking.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ringle.tutoring.domain.booking.entity.Booking;
import ringle.tutoring.domain.booking.enums.BookingStatus;
import ringle.tutoring.domain.user.entity.User;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  Optional<List<Booking>> findByUserAndBookingStatus(User user, BookingStatus bookingStatus);
}
