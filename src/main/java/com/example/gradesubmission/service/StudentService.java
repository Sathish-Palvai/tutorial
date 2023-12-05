package com.example.gradesubmission.service;

import java.util.List;

import com.example.gradesubmission.entity.Student;

public interface StudentService {
    Student getStudent(Long id);
    Student saveStudent(Student student);
    void deleteStudent(Long id);
    List<Student> getStudents();
}