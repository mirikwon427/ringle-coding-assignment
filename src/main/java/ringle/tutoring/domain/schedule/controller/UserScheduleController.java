package ringle.tutoring.domain.schedule.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ringle.tutoring.domain.schedule.dto.request.UserScheduleRequestDto;
import ringle.tutoring.domain.schedule.dto.response.UserScheduleResponseDto;
import ringle.tutoring.domain.schedule.service.UserScheduleService;

@RestController
@RequestMapping("student")
public class UserScheduleController {

  private final UserScheduleService userScheduleService;

  public UserScheduleController(UserScheduleService userScheduleService) {
    this.userScheduleService = userScheduleService;
  }

  @GetMapping("/schedule/{userId}")
  public ResponseEntity<?> getStudentSchedule(@PathVariable long userId, @RequestBody UserScheduleRequestDto userScheduleRequestDto) {
    try {
      UserScheduleResponseDto userScheduleResponseDto = userScheduleService.getClassTime(userId, userScheduleRequestDto);
      return ResponseEntity.ok(userScheduleResponseDto);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

}
