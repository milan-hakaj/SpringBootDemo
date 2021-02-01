package com.metropolitan.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public void addStudent(Student student) {
        Optional<Student> studentByEmail = this.studentRepository.findStudentByEmail(student.getEmail());

        if(studentByEmail.isPresent()) {
            throw new IllegalStateException("Email already taken.");
        }

        this.studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = this.studentRepository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("student with id " + id + " does not exists.");
        }

        this.studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = this.studentRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("student with id " + id + " does not exist."));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentByEmail = this.studentRepository.findStudentByEmail(email);

            if(studentByEmail.isPresent()) {
                throw new IllegalStateException("Email already taken.");
            }

            student.setEmail(email);
        }
    }
}
