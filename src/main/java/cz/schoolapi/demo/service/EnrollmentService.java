package cz.schoolapi.demo.service;

import cz.schoolapi.demo.dto.EnrollmentRequest;
import cz.schoolapi.demo.dto.EnrollmentResponse;
import cz.schoolapi.demo.exception.BusinessRuleException;
import cz.schoolapi.demo.exception.ResourceNotFoundException;
import cz.schoolapi.demo.model.Enrollment;
import cz.schoolapi.demo.model.Grade;
import cz.schoolapi.demo.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentService studentService,
            CourseService courseService
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public List<EnrollmentResponse> findAll() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EnrollmentResponse findById(Long id) {
        return toResponse(getEntityById(id));
    }

    public EnrollmentResponse enroll(EnrollmentRequest request) {
        enrollmentRepository.findByStudentIdAndCourseId(request.studentId(), request.courseId())
                .ifPresent(existing -> {
                    throw new BusinessRuleException("Student uz je do tohoto predmetu zapsan");
                });

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(studentService.getEntityById(request.studentId()));
        enrollment.setCourse(courseService.getEntityById(request.courseId()));
        enrollment.setEnrolledAt(request.enrolledAt() != null ? request.enrolledAt() : LocalDate.now());
        enrollment.setGrade(Grade.IN_PROGRESS);

        return toResponse(enrollmentRepository.save(enrollment));
    }

    public EnrollmentResponse updateGrade(Long id, Grade grade) {
        Enrollment enrollment = getEntityById(id);
        enrollment.setGrade(grade);
        return toResponse(enrollmentRepository.save(enrollment));
    }

    public void delete(Long id) {
        Enrollment enrollment = getEntityById(id);
        enrollmentRepository.delete(enrollment);
    }

    public Enrollment getEntityById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zapis s ID " + id + " nebyl nalezen"));
    }

    public EnrollmentResponse toResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                studentService.toResponse(enrollment.getStudent()),
                courseService.toResponse(enrollment.getCourse()),
                enrollment.getEnrolledAt(),
                enrollment.getGrade()
        );
    }
}
