package hogwarts.school_2.service;

import hogwarts.school_2.exception.EntityNotFoundException;
import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty edit(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty delete(Long id) {
        Faculty  faculty = find(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Faculty find(Long id) {
        return facultyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        // или можно так: return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColor(color);
    }
}
