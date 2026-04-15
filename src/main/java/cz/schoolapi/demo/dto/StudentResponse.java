package cz.schoolapi.demo.dto;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Integer studyYear
) {
}
