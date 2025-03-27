package ringle.tutoring.domain.schedule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ringle.tutoring.domain.schedule.dto.request.GetTutorScheduleRequestDto;
import ringle.tutoring.domain.schedule.dto.response.GetTutorScheduleResponseDto;
import ringle.tutoring.domain.schedule.dto.response.TutorScheduleResponseDto;
import ringle.tutoring.domain.schedule.service.TutorScheduleService;
import ringle.tutoring.domain.schedule.dto.request.TutorScheduleRequestDto;

@RestController
@RequestMapping("tutor")
public class TutorScheduleController {

  private final TutorScheduleService tutorScheduleService;

  public TutorScheduleController(TutorScheduleService tutorScheduleService) {
    this.tutorScheduleService = tutorScheduleService;
  }

  @PostMapping("/schedule/{tutorId}")
  public ResponseEntity<?> createTutorSchedule(@PathVariable long tutorId, @RequestBody TutorScheduleRequestDto tutorScheduleRequestDto) {
    try {
      TutorScheduleResponseDto tutorScheduleResponseDto = tutorScheduleService.createTutorSchedule(tutorId, tutorScheduleRequestDto.getClassTimeIds());
      return ResponseEntity.ok(tutorScheduleResponseDto);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

  @DeleteMapping("/schedule/{tutorId}")
  public ResponseEntity<?> deleteTutorSchedule(@PathVariable long tutorId, @RequestBody TutorScheduleRequestDto tutorScheduleRequestDto) {
    try {
      tutorScheduleService.deleteTutorSchedule(tutorId, tutorScheduleRequestDto.getClassTimeIds());
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

  @GetMapping("/schedule")
  public ResponseEntity<?> getStudentSchedule(@RequestBody GetTutorScheduleRequestDto getTutorScheduleRequestDto) {
    try {
      GetTutorScheduleResponseDto getTutorScheduleResponseDto = tutorScheduleService.getTutorSchedules(getTutorScheduleRequestDto);
      return ResponseEntity.ok(getTutorScheduleResponseDto);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }
}
