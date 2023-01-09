package br.com.dos.elearning.repository;

import br.com.dos.elearning.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
}
