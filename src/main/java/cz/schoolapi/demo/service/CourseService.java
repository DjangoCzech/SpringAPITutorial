package cz.schoolapi.demo.service;

import cz.schoolapi.demo.dto.CourseRequest;
import cz.schoolapi.demo.dto.CourseResponse;
import cz.schoolapi.demo.exception.BusinessRuleException;
import cz.schoolapi.demo.exception.ResourceNotFoundException;
import cz.schoolapi.demo.model.Course;
import cz.schoolapi.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResponse> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CourseResponse findById(Long id) {
        return toResponse(getEntityById(id));
    }

    public CourseResponse create(CourseRequest request) {
        validateUniqueCode(request.code(), null);
        Course course = new Course();
        applyRequest(course, request);
        return toResponse(courseRepository.save(course));
    }

    public CourseResponse update(Long id, CourseRequest request) {
        Course course = getEntityById(id);
        validateUniqueCode(request.code(), id);
        applyRequest(course, request);
        return toResponse(courseRepository.save(course));
    }

    public void delete(Long id) {
        Course course = getEntityById(id);
        courseRepository.delete(course);
    }

    public Course getEntityById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Predmet s ID " + id + " nebyl nalezen"));
    }

    private void validateUniqueCode(String code, Long currentId) {
        courseRepository.findByCode(code).ifPresent(existing -> {
            boolean isOtherRecord = currentId == null || !existing.getId().equals(currentId);
            if (isOtherRecord) {
                throw new BusinessRuleException("Kod predmetu '" + code + "' uz existuje");
            }
        });
    }

    private void applyRequest(Course course, CourseRequest request) {
        course.setCode(request.code());
        course.setTitle(request.title());
        course.setTeacherName(request.teacherName());
        course.setCredits(request.credits());
    }

    public CourseResponse toResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getTeacherName(),
                course.getCredits()
        );
    }
}
