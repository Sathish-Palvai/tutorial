package com.example;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.gradesubmission.entity.Student;
import com.example.gradesubmission.repository.StudentRepository;

@SpringBootApplication
public class TutorialApplication implements CommandLineRunner {

	@Autowired
	StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(TutorialApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Student[] students = new Student[] {
				new Student(1L, "Harry Potter", LocalDate.parse(("1980-07-31")), null),
				new Student(2L, "Sathish Palvai", LocalDate.parse(("1982-09-01")), null),
				new Student(3L, "Santhosh Palvai", LocalDate.parse(("1985-09-01")), null),
				new Student(3L, "Gnyapika Dontula", LocalDate.parse(("1988-05-08")), null)
		};

		for (int i = 0; i < students.length; i++) {
			studentRepository.save(students[i]);
		}
	}

}
