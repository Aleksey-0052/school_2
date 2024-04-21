package hogwarts.school_2.service;

import hogwarts.school_2.model.Student;

import java.util.Collection;

public interface StudentService {

    Student create(Student student);
    Student edit(Student student);
    Student delete(Long id);
    Student find(Long id);
    Collection<Student> getAllStudents();
    Collection<Student> getStudentsByAge(Integer age);
}
