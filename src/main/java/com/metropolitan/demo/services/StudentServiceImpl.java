package com.metropolitan.demo.services;

import com.metropolitan.demo.exceptions.ApiRequestException;
import com.metropolitan.demo.models.Student;
import com.metropolitan.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public void addStudent(Student student) {
        Optional<Student> studentByEmail = this.studentRepository.findStudentByEmail(student.getEmail());

        if(studentByEmail.isPresent()) {
            // THROW IF EMAIL TAKEN
            throw new ApiRequestException("Email already taken.");
        }

        this.studentRepository.save(student);
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        // THROW IF NOT FOUND
        return this.studentRepository.findById(id);
    }

    public void deleteStudent(Long id) {
        boolean exists = this.studentRepository.existsById(id);

        if (!exists) {
            // THROW IF NOT EXIST
            throw new ApiRequestException("student with id " + id + " does not exists.");
        }

        this.studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = this.studentRepository.findById(id)
                    .orElseThrow(() -> new ApiRequestException("student with id " + id + " does not exist."));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentByEmail = this.studentRepository.findStudentByEmail(email);

            if(studentByEmail.isPresent()) {
                // THROW EMAIL ALREADY TAKEN
                throw new IllegalStateException("Email already taken.");
            }

            student.setEmail(email);
        }
    }
}
