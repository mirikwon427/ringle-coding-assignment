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
import ringle.tutoring.domain.common.enums.Active;
import ringle.tutoring.domain.lesson.entity.LessonPackage;
import ringle.tutoring.domain.lesson.entity.UserLesson;
import ringle.tutoring.domain.lesson.repository.LessonPackageRepository;
import ringle.tutoring.domain.lesson.repository.UserLessonRepository;
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
  private final UserLessonRepository userLessonRepository;
  private final LessonPackageRepository lessonPackageRepository;

  public BookingService(UserRepository userRepository, TutorScheduleRepository tutorScheduleRepository, BookingRepository bookingRepository,
      UserLessonRepository userLessonRepository,
      LessonPackageRepository lessonPackageRepository) {
    this.userRepository = userRepository;
    this.tutorScheduleRepository = tutorScheduleRepository;
    this.bookingRepository = bookingRepository;
    this.userLessonRepository = userLessonRepository;
    this.lessonPackageRepository = lessonPackageRepository;
  }

  @Transactional
  public BookLessonResponseDto bookLesson(long userId, BookLessonRequestDto bookLessonRequestDto) {
    // 수업 예약 로직: 사용자 및 튜터 일정 확인 후 예약 처리, 수업권 검증 후 예약 상태 업데이트
    User user = findUser(userId);
    TutorSchedule tutorSchedule = findTutorSchedule(bookLessonRequestDto.getTutorScheduleId());
    UserLesson userLesson = findUserLesson(user, Active.active);
    LessonPackage lessonPackage = findLessonPackage(userLesson.getLessonPackage().getLessonPackageId());

    validateLessonDuration(lessonPackage, bookLessonRequestDto);

    Booking booking = createBooking(user, tutorSchedule, bookLessonRequestDto);
    //튜터 스케줄 상태 업데이트
    updateTutorScheduleAvailability(tutorSchedule);

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

  private UserLesson findUserLesson(User user, Active userLessonStatus) {
    System.out.println(user.getUserId());
    System.out.println(userLessonStatus);
    return userLessonRepository.findByUserAndUserLessonStatus(user, userLessonStatus)
        .orElseThrow(() -> new IllegalArgumentException("UserLesson을 찾을 수 없습니다."));
  }

  private LessonPackage findLessonPackage(long lessonPackageId) {
    return lessonPackageRepository.findById(lessonPackageId)
        .orElseThrow(() -> new IllegalArgumentException("LessonPackage를 찾을 수 없습니다."));
  }

  private void validateLessonDuration(LessonPackage lessonPackage, BookLessonRequestDto bookLessonRequestDto) {
    // 요청한 수업 시간과 사용자의 수업권이 맞지 않으면 예약 불가
    if (lessonPackage.getLessonPackageduration() != bookLessonRequestDto.getLessonDuration()) {
      throw new IllegalArgumentException("보유한 수업권으로는 " + lessonPackage.getLessonPackageduration() + "분 수업만 예약할 수 있습니다.");
    }
  }

  private Booking createBooking(User user, TutorSchedule tutorSchedule, BookLessonRequestDto bookLessonRequestDto) {
    Booking booking = new Booking(user, tutorSchedule, bookLessonRequestDto.getLessonDuration());
    bookingRepository.save(booking);
    return booking;
  }

  private void updateTutorScheduleAvailability(TutorSchedule tutorSchedule) {
    tutorSchedule.setTutorScheduleIsAvailable(false);
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
