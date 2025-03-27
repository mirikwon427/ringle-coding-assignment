package ringle.tutoring.domain.lesson;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import ringle.tutoring.domain.common.entity.BaseTimeEntity;

@Entity
@Table(name = "lessons_packages")
public class LessonPackage extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_id")
  private long lessonPackageId;

  @Column(name = "lesson_package_name", nullable = false, length = 100)
  private String lessonPackageName;

  @Column(name = "lesson_package_description", nullable = false, length = 1000)
  private int lessonPackageduration;

  @Column(name = "lesson_package_total_lessons", nullable = false)
  private int lessonPackageTotalLessons = 0;

  @Column(name = "lesson_package_validity_days", nullable = false)
  private int lessonPackagevaliditydays = 0;

  @Column(name = "lesson_package_price", nullable = false)
  private BigDecimal lessonPackagePrice = new BigDecimal(0);

  @Column(name = "lesson_package_discount", nullable = false)
  private BigDecimal lessonPackageDiscount = new BigDecimal(0);

}
