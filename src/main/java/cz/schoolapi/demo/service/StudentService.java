package cz.schoolapi.demo.service;

import cz.schoolapi.demo.dto.StudentRequest;
import cz.schoolapi.demo.dto.StudentResponse;
import cz.schoolapi.demo.exception.ResourceNotFoundException;
import cz.schoolapi.demo.model.Student;
import cz.schoolapi.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponse> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public StudentResponse findById(Long id) {
        return toResponse(getEntityById(id));
    }

    public StudentResponse create(StudentRequest request) {
        Student student = new Student();
        applyRequest(student, request);
        return toResponse(studentRepository.save(student));
    }

    public StudentResponse update(Long id, StudentRequest request) {
        Student student = getEntityById(id);
        applyRequest(student, request);
        return toResponse(studentRepository.save(student));
    }

    public void delete(Long id) {
        Student student = getEntityById(id);
        studentRepository.delete(student);
    }

    public Student getEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student s ID " + id + " nebyl nalezen"));
    }

    private void applyRequest(Student student, StudentRequest request) {
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setStudyYear(request.studyYear());
    }

    public StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getStudyYear()
        );
    }
}
