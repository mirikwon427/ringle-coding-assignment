package ringle.tutoring.domain.booking.service;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ringle.tutoring.domain.booking.dto.response.GetBookLessonResponseDto;
import ringle.tutoring.domain.booking.entity.Booking;
import ringle.tutoring.domain.booking.dto.request.BookLessonRequestDto;
import ringle.tutoring.domain.booking.dto.response.BookLessonResponseDto;
import ringle.tutoring.domain.booking.enums.BookingStatus;
import ringle.tutoring.domain.booking.repository.BookingRepository;
import ringle.tutoring.domain.schedule.entity.ClassTime;
import ringle.tutoring.domain.schedule.entity.TutorSchedule;
import ringle.tutoring.domain.schedule.repository.TutorScheduleRepository;
import ringle.tutoring.domain.tutor.entity.Tutor;
import ringle.tutoring.domain.user.entity.User;
import ringle.tutoring.domain.user.repository.UserRepository;

@Service
public class BookingService {

  private final UserRepository userRepository;
  private final BookingRepository bookingRepository;
  private final TutorScheduleRepository tutorScheduleRepository;

  public BookingService(UserRepository userRepository, TutorScheduleRepository tutorScheduleRepository, BookingRepository bookingRepository) {
    this.userRepository = userRepository;
    this.tutorScheduleRepository = tutorScheduleRepository;
    this.bookingRepository = bookingRepository;
  }

  @Transactional
  public BookLessonResponseDto bookLesson(long userId, BookLessonRequestDto bookLessonRequestDto) {
    User user = findUser(userId);
    TutorSchedule tutorSchedule = findTutorSchedule(bookLessonRequestDto.getTutorScheduleId());

    Booking booking = new Booking(user, tutorSchedule, bookLessonRequestDto.getLessonDuration());
    bookingRepository.save(booking);
    //튜터 스케줄 상태 업데이트
    tutorSchedule.setTutorScheduleIsAvailable(false);

    return new BookLessonResponseDto(
        booking.getBookingId(),
        booking.getBookingDuration(),
        booking.getBookingStatus().toString()
    );
  }

  private User findUser(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));
  }

  private TutorSchedule findTutorSchedule(long tutorScheduleId) {
    return tutorScheduleRepository.findById(tutorScheduleId)
        .orElseThrow(() -> new IllegalArgumentException("TutorSchedule을 찾을 수 없습니다."));
  }

  @Transactional
  public List<GetBookLessonResponseDto> getBookLesson(long userId) {
    User user = findUser(userId);
    List<Booking> bookings = findBooking(user);

    return bookings.stream()
        .map(booking -> {
          TutorSchedule tutorSchedule = booking.getTutorSchedule();
          Tutor tutor = tutorSchedule.getTutor();
          ClassTime classTime = tutorSchedule.getClassTime();
          return new GetBookLessonResponseDto(
              booking.getBookingId(),
              booking.getBookingStatus().toString(),
              tutor.getTutorId(),
              tutor.getTutorName(),
              tutor.getTutorUniversity(),
              tutor.getTutorMajor(),
              classTime.getClassTimeId(),
              classTime.getClassStartTime()
          );
        })
        .collect(Collectors.toList());

  }

  private List<Booking> findBooking(User user) {
    return bookingRepository.findByUserAndBookingStatus(user, BookingStatus.confirmed)
        .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
  }

}
