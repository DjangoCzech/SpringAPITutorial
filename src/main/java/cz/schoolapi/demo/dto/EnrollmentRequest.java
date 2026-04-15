package cz.schoolapi.demo.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EnrollmentRequest(
        @NotNull(message = "ID studenta je povinne")
        Long studentId,

        @NotNull(message = "ID predmetu je povinne")
        Long courseId,

        LocalDate enrolledAt
) {
}
