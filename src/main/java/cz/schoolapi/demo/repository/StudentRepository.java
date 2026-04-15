package cz.schoolapi.demo.repository;

import cz.schoolapi.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
