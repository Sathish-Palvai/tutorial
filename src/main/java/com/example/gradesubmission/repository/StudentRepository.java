package com.example.gradesubmission.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.gradesubmission.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{
    
}