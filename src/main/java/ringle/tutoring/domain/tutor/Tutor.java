package ringle.tutoring.domain.tutor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import ringle.tutoring.domain.common.enums.Active;
import ringle.tutoring.domain.common.entity.BaseTimeEntity;

@Entity
@Table(name = "tutors")
public class Tutor extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tutor_id")
  private long tutorId;

  @Column(name = "tutor_email", nullable = false, length = 200)
  private String tutorEmail;

  @Column(name = "tutor_password", nullable = false, length = 300)
  private String tutorPassword;

  @Column(name = "tutor_name", nullable = false, length = 100)
  private String tutorName;

  @Column(name = "tutor_phone_number", nullable = false, length = 50)
  private String tutorPhoneNumber;

  @Column(name = "tutor_university", nullable = false, length = 100)
  private String tutorUniversity;

  @Column(name = "tutor_major", nullable = false, length = 100)
  private String tutorMajor;

  @Enumerated(EnumType.STRING)
  @Column(name = "tutor_status", nullable = false)
  private Active tutorStatus = Active.active;

  @Column(name = "tutor_time_zone")
  private String tutorTimeZone = "UTC";

}