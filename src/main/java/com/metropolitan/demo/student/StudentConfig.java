package com.metropolitan.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student s1 = new Student(
                    "Milanka Milanic",
                    "milanka@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            Student s2 = new Student(
                    "Branislava Brankic",
                    "branislava@gmail.com",
                    LocalDate.of(1992, Month.JANUARY, 15)
            );

            studentRepository.saveAll(List.of(s1, s2));
        };
    }
}
