package hogwarts.school_2.service;

import hogwarts.school_2.exception.EntityNotFoundException;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    // редактируем данные студента (имя и/или возраст)
    public Student edit(Student student) {
        return studentRepository.save(student);
    }

    public Student delete(Long id) {
        Student student = find(id);
        studentRepository.deleteById(id);
        return student;
    }

    public Student find(Long id) {
       return studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
       // или можно так: return studentRepository.findById(id).get();
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(Integer age) {
        return studentRepository.findByAge(age);

    }
}
