package com.metropolitan.demo.services;
import com.metropolitan.demo.models.Student;
import java.util.Optional;

public interface IStudentService {
     void addStudent(Student student);
     Optional<Student> getStudentById(Long id);
     void deleteStudent(Long id);
     void updateStudent(Long id, String name, String email);
}
