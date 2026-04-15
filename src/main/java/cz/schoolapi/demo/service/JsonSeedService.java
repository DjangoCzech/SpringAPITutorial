package cz.schoolapi.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.schoolapi.demo.dto.JsonSeedPayload;
import cz.schoolapi.demo.exception.BusinessRuleException;
import cz.schoolapi.demo.model.Course;
import cz.schoolapi.demo.model.Enrollment;
import cz.schoolapi.demo.model.Grade;
import cz.schoolapi.demo.model.Student;
import cz.schoolapi.demo.repository.CourseRepository;
import cz.schoolapi.demo.repository.EnrollmentRepository;
import cz.schoolapi.demo.repository.StudentRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class JsonSeedService {

    private final ObjectMapper objectMapper;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public JsonSeedService(
            ObjectMapper objectMapper,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        this.objectMapper = objectMapper;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public JsonSeedPayload readJsonSeed() {
        try (InputStream inputStream = new ClassPathResource("data/sample-data.json").getInputStream()) {
            return objectMapper.readValue(inputStream, JsonSeedPayload.class);
        } catch (IOException ex) {
            throw new IllegalStateException("JSON seed se nepodarilo nacist", ex);
        }
    }

    @Transactional
    public void importJsonIntoDatabase() {
        JsonSeedPayload payload = readJsonSeed();

        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        Map<Long, Student> studentsByOriginalId = new HashMap<>();
        for (JsonSeedPayload.JsonStudentSeed studentSeed : payload.students()) {
            Student student = new Student();
            student.setFirstName(studentSeed.firstName());
            student.setLastName(studentSeed.lastName());
            student.setEmail(studentSeed.email());
            student.setStudyYear(studentSeed.studyYear());
            Student saved = studentRepository.save(student);
            studentsByOriginalId.put(studentSeed.id(), saved);
        }

        Map<Long, Course> coursesByOriginalId = new HashMap<>();
        for (JsonSeedPayload.JsonCourseSeed courseSeed : payload.courses()) {
            Course course = new Course();
            course.setCode(courseSeed.code());
            course.setTitle(courseSeed.title());
            course.setTeacherName(courseSeed.teacherName());
            course.setCredits(courseSeed.credits());
            Course saved = courseRepository.save(course);
            coursesByOriginalId.put(courseSeed.id(), saved);
        }

        for (JsonSeedPayload.JsonEnrollmentSeed enrollmentSeed : payload.enrollments()) {
            Student student = studentsByOriginalId.get(enrollmentSeed.studentId());
            Course course = coursesByOriginalId.get(enrollmentSeed.courseId());
            if (student == null || course == null) {
                throw new BusinessRuleException("JSON enrollment odkazuje na neexistujici studentId/courseId");
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrolledAt(LocalDate.parse(enrollmentSeed.enrolledAt()));
            enrollment.setGrade(Grade.valueOf(enrollmentSeed.grade()));
            enrollmentRepository.save(enrollment);
        }
    }
}
