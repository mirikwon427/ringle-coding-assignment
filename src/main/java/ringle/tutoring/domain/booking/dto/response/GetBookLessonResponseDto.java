package ringle.tutoring.domain.booking.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetBookLessonResponseDto {

    private long bookingId;
    private String bookingStatus;
    private long tutorId;
    private String tutorName;
    private String tutorUniversity;
    private String tutorMajor;
    private long classTimeId;
    private Instant classStartTime;

}
