package ringle.tutoring.domain.booking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookLessonRequestDto {

    private long classTimeId;
    private int lessonDuration;
    private long tutorScheduleId;

}
