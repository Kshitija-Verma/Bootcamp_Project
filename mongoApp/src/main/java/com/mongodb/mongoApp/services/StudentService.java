package com.mongodb.mongoApp.services;

import com.mongodb.mongoApp.dtos.NameDto;
import com.mongodb.mongoApp.dtos.StudentDto;
import com.mongodb.mongoApp.entities.Student;
import com.mongodb.mongoApp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public String saveUser(StudentDto studentDto){
        Student student = new Student();
        student.setAge(studentDto.getAge());
        student.setName(studentDto.getName());
        student.setStandard(studentDto.getStandard());
        studentRepository.save(student);
        return "One user added";
    }


    public Student updateStudent(StudentDto studentDto) {
       Student student = studentRepository.findByName(studentDto.getName());
       student.setAge(studentDto.getAge());
       student.setStandard(studentDto.getStandard());
       studentRepository.save(student);
       return student;
    }

    public List<Student> deleteStudent(NameDto nameDto) {
        Student student = studentRepository.findByName(nameDto.getName());
        studentRepository.delete(student);
        return studentRepository.findAll();
    }
}
