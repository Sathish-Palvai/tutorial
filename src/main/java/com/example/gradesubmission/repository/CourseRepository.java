package com.example.gradesubmission.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.gradesubmission.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

}