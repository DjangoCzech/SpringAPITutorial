package cz.schoolapi.demo.dto;

import java.util.List;

public record JsonSeedPayload(
        List<JsonStudentSeed> students,
        List<JsonCourseSeed> courses,
        List<JsonEnrollmentSeed> enrollments
) {
    public record JsonStudentSeed(
            Long id,
            String firstName,
            String lastName,
            String email,
            Integer studyYear
    ) {
    }

    public record JsonCourseSeed(
            Long id,
            String code,
            String title,
            String teacherName,
            Integer credits
    ) {
    }

    public record JsonEnrollmentSeed(
            Long studentId,
            Long courseId,
            String enrolledAt,
            String grade
    ) {
    }
}
