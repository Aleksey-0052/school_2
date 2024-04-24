package hogwarts.school_2.service;

import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;

import java.util.Collection;

public interface FacultyService {

    Faculty create(Faculty faculty);
    Faculty edit(Faculty faculty);
    Faculty delete(Long id);
    Faculty find(Long id);
    Collection<Faculty> getAllFaculties();
    Collection<Faculty> getFacultiesByNameOrColor(String name, String color);
    Collection<Student> getStudents(Long facultyId);
//    Collection<Faculty> getFacultiesByColor(String color);

}
