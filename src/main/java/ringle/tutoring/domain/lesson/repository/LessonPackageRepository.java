package ringle.tutoring.domain.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ringle.tutoring.domain.lesson.entity.LessonPackage;

public interface LessonPackageRepository extends JpaRepository<LessonPackage, Long> {

}
