package hogwarts.school_2.service;

import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student create(Student student);
    Student edit(Student student);
    Student delete(Long id);
    Student find(Long id);
    Collection<Student> getAllStudents();
    Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge);
    Faculty getFaculty(Long studentId);
//    Collection<Student> getStudentsByAge(Integer age);



    // получение количества всех студентов
    Integer getTotalCountOfStudents();

    // получение среднего возраста студентов
    Double getAverageAgeOfStudents();
    // получение части студентов
    List<Student> getLimitOfStudents();

    // получение количества всех студентов через создание interface projection
    Integer getAmountOfStudents();


}
