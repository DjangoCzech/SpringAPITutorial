package cz.schoolapi.demo.dto;

public record DashboardStatsResponse(
        long students,
        long courses,
        long enrollments
) {
}
