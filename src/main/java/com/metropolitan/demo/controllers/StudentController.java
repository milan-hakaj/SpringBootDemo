package com.metropolitan.demo.controllers;

import com.metropolitan.demo.models.Student;
import com.metropolitan.demo.services.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {


    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return this.studentService.getStudents();
    }

    // GET
    @GetMapping("/{studentId}")
    public Optional<Student> getStudent(@PathVariable("studentId") Long id) {
        return this.studentService.getStudentById(id);
    }

    // POST
    @PostMapping
    public void addStudent(@RequestBody Student student) {
        this.studentService.addStudent(student);
    }

    // PUT
    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        this.studentService.updateStudent(id, name, email);
    }

    // DELETE
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id) {
        this.studentService.deleteStudent(id);
    }
}
