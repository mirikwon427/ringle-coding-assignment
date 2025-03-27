package ringle.tutoring.domain.schedule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ringle.tutoring.domain.schedule.service.TutorScheduleService;
import ringle.tutoring.domain.schedule.dto.ScheduleRequestDto;

@RestController
@RequestMapping("tutor")
public class TutorScheduleController {

  private final TutorScheduleService tutorScheduleService;

  public TutorScheduleController(TutorScheduleService tutorScheduleService) {
    this.tutorScheduleService = tutorScheduleService;
  }

  @PostMapping("/schedule/{tutorId}")
  public ResponseEntity<?> createTutorSchedule(@PathVariable long tutorId, @RequestBody ScheduleRequestDto scheduleRequestDto) {
    try {
      tutorScheduleService.createTutorSchedule(tutorId, scheduleRequestDto.getClassTimeIds());
      return ResponseEntity.ok("success");
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
