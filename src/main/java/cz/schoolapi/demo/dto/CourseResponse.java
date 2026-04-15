package cz.schoolapi.demo.dto;

public record CourseResponse(
        Long id,
        String code,
        String title,
        String teacherName,
        Integer credits
) {
}
