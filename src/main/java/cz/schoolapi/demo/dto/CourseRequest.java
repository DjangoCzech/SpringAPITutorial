package cz.schoolapi.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotBlank(message = "Kod predmetu je povinny")
        @Size(max = 20, message = "Kod predmetu muze mit max 20 znaku")
        String code,

        @NotBlank(message = "Nazev predmetu je povinny")
        String title,

        @NotBlank(message = "Vyucujici je povinny")
        String teacherName,

        @NotNull(message = "Kredity jsou povinne")
        @Min(value = 1, message = "Kredity musi byt mezi 1 a 10")
        @Max(value = 10, message = "Kredity musi byt mezi 1 a 10")
        Integer credits
) {
}
