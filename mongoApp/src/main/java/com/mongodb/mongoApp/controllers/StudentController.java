package com.mongodb.mongoApp.controllers;

import com.mongodb.mongoApp.dtos.LtGtDto;
import com.mongodb.mongoApp.dtos.NameDto;
import com.mongodb.mongoApp.dtos.StudentDto;
import com.mongodb.mongoApp.entities.Student;
import com.mongodb.mongoApp.repositories.StudentRepository;
import com.mongodb.mongoApp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;

    //****************Create document **********************
    @PostMapping("/add-student")
    public String addStudent(@RequestBody StudentDto studentDto){
        return studentService.saveUser(studentDto);
    }


    //****************** Read documents ***********************
    @GetMapping("/view-students")
    public List<Student> viewStudent(){
        return studentRepository.findAll();
    }


    //******************* Update documents *********************
    @PatchMapping("/update-students")
    public Student updateStudent(@RequestBody StudentDto studentDto){
        return studentService.updateStudent(studentDto);

    }


    //****************** Delete documents ***********************
    @DeleteMapping("/delete-student")
    public List<Student> deleteStudent(@RequestBody NameDto nameDto){
       return studentService.deleteStudent(nameDto);

    }


    //****************** less than - greater than query ****************
    @PostMapping("/specific-students")
    public List<Student> specificStudent(@RequestBody LtGtDto ltGtDto){
        return studentRepository.findByLessThanGreaterThan(ltGtDto.getLt(),ltGtDto.getGt());
    }

    //******************** filter by age *******************************
    @GetMapping("/filter-age/{age}")
    public List<Student> filterByAge(@PathVariable Integer age){
        return studentRepository.findByAge(age);
    }
}
