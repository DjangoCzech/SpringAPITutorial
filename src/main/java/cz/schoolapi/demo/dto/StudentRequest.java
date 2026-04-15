package cz.schoolapi.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentRequest(
        @NotBlank(message = "Jmeno je povinne")
        String firstName,

        @NotBlank(message = "Prijmeni je povinne")
        String lastName,

        @Email(message = "Email nema spravny format")
        @NotBlank(message = "Email je povinny")
        String email,

        @NotNull(message = "Rocnik je povinny")
        @Min(value = 1, message = "Rocnik musi byt mezi 1 a 5")
        @Max(value = 5, message = "Rocnik musi byt mezi 1 a 5")
        Integer studyYear
) {
}
