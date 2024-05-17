package hogwarts.school_2.service;

import hogwarts.school_2.exception.EntityNotFoundException;
import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.repository.AllStudentsRepository;
import hogwarts.school_2.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final AllStudentsRepository allStudentsRepository;

    public StudentServiceImpl(StudentRepository studentRepository, AllStudentsRepository allStudentsRepository) {
        this.studentRepository = studentRepository;
        this.allStudentsRepository = allStudentsRepository;
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    // редактируем данные студента (имя и/или возраст)
    @Override
    public Student edit(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student delete(Long id) {
        Student student = find(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Student find(Long id) {
       return studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
       // или можно так: return studentRepository.findById(id).get();
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFaculty(Long studentId) {
        return find(studentId).getFaculty();
        // находим по идентификатору студента и вызываем у объекта "student" геттер для получения поля faculty
    }


//    public Collection<Student> getStudentsByAge(Integer age) {
//        return studentRepository.findByAge(age);
//
//    }


    public Integer getTotalCountOfStudents() {
        return studentRepository.getTotalCountOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLimitOfStudents() {
        return studentRepository.getLimitOfStudents();
    }

    // получение количества всех студентов через создание interface projection
    public Integer getAmountOfStudents() {
        return allStudentsRepository.getAmountOfStudents();
    }


}
