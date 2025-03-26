package ringle.tutoring.domain.user;

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
@Table(name = "users")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private long userId;

  @Column(name = "user_email", nullable = false, length = 200)
  private String userEmail;

  @Column(name = "user_password", nullable = false, length = 300)
  private String userPassword;

  @Column(name = "user_name", nullable = false, length = 100)
  private String userName;

  @Column(name = "user_phone_number", nullable = false, length = 50)
  private String userPhoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_status", nullable = false)
  private Active userStatus = Active.active;

  @Column(name = "user_time_zone", nullable = false)
  private String userTimeZone = "UTC";

}
