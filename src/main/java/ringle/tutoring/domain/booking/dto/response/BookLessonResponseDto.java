package ringle.tutoring.domain.booking.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookLessonResponseDto {
  private long bookingId;
  private int bookingDuration;
  private String bookingStatus;
}
