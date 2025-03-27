package ringle.tutoring.domain.booking.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ringle.tutoring.domain.booking.dto.BookLessonRequestDto;

@RestController
@RequestMapping("booking")
public class BookingController {

  @PostMapping("/lesson/{userId}")
  public void bookLesson(@PathVariable long userId, @RequestBody BookLessonRequestDto bookLessonRequestDto) {

  }

}
