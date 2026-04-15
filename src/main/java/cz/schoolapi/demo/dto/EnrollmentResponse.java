package cz.schoolapi.demo.dto;

import cz.schoolapi.demo.model.Grade;

import java.time.LocalDate;

public record EnrollmentResponse(
        Long id,
        StudentResponse student,
        CourseResponse course,
        LocalDate enrolledAt,
        Grade grade
) {
}
