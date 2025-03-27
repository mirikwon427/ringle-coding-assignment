package ringle.tutoring.domain.booking.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ringle.tutoring.domain.booking.dto.request.BookLessonRequestDto;
import ringle.tutoring.domain.booking.dto.response.BookLessonResponseDto;
import ringle.tutoring.domain.booking.dto.response.GetBookLessonResponseDto;
import ringle.tutoring.domain.booking.service.BookingService;
import ringle.tutoring.domain.schedule.dto.request.UserScheduleRequestDto;
import ringle.tutoring.domain.schedule.dto.response.UserScheduleResponseDto;

@RestController
@RequestMapping("booking")
public class BookingController {
  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  // 수업 예약 (예약 성공 시 예약 정보 반환)
  @PostMapping("/lesson/{userId}")
  public ResponseEntity<?> bookLesson(@PathVariable long userId, @RequestBody BookLessonRequestDto bookLessonRequestDto) {
    try {
      BookLessonResponseDto bookLessonResponseDto = bookingService.bookLesson(userId, bookLessonRequestDto);
      return ResponseEntity.ok(bookLessonResponseDto);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

  // 학생이 예약한 모든 수업 조회
  @GetMapping("/lesson/{userId}")
  public ResponseEntity<?> getBookLesson(@PathVariable long userId) {
    try {
      List<GetBookLessonResponseDto> getBookLessonResponseDto = bookingService.getBookLesson(userId);
      return ResponseEntity.ok(getBookLessonResponseDto);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }
}
