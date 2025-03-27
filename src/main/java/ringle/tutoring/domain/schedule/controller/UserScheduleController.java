package ringle.tutoring.domain.schedule.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ringle.tutoring.domain.schedule.dto.request.UserScheduleRequestDto;
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
      // 학생의 수업 가능한 시간대 조회
      userScheduleService.getClassTime(userId, userScheduleRequestDto);

      // 성공적으로 시간대 반환
      return ResponseEntity.ok(ResponseEntity.ok());
    } catch (Exception e) {
      // 예외 발생 시 400 Bad Request 반환
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
