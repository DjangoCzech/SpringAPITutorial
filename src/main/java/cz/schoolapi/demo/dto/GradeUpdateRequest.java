package cz.schoolapi.demo.dto;

import cz.schoolapi.demo.model.Grade;
import jakarta.validation.constraints.NotNull;

public record GradeUpdateRequest(
        @NotNull(message = "Znamka je povinna")
        Grade grade
) {
}
