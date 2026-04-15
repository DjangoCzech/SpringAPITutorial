package cz.schoolapi.demo.service;

import cz.schoolapi.demo.dto.DashboardStatsResponse;
import cz.schoolapi.demo.repository.CourseRepository;
import cz.schoolapi.demo.repository.EnrollmentRepository;
import cz.schoolapi.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public DashboardService(
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public DashboardStatsResponse getStats() {
        return new DashboardStatsResponse(
                studentRepository.count(),
                courseRepository.count(),
                enrollmentRepository.count()
        );
    }
}
