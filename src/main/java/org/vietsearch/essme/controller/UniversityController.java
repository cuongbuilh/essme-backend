package org.vietsearch.essme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.vietsearch.essme.model.university.University;
import org.vietsearch.essme.repository.UniversityRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {
    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping("/search")
    public List<University> searchUniversities(@RequestParam("name") String name) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(name);
        List<University> list = universityRepository.findBy(criteria);
        if (list.isEmpty())
            return universityRepository.findByNameStartsWithIgnoreCase(name);
        return list;
    }

    @GetMapping
    public Page<University> getUniversities(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size, @RequestParam(value = "sort", required = false) String sort) {
        return universityRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public University findById(@PathVariable("id") String id) {
        return universityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "University not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public University createUniversity(@Valid @RequestBody University university) {
        checkExistsByName(university.getName());
        return universityRepository.save(university);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        if (universityRepository.existsById(id)) {
            universityRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "University not found");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public University update(@PathVariable("id") String id, @Valid @RequestBody University university) {
        universityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "University not found"));
        checkExistsByName(university.getName());
        university.set_id(id);
        return universityRepository.save(university);
    }

    private void checkExistsByName(String name) {
        if (universityRepository.findByNameIgnoreCase(name) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "University already exists");
        }
    }
}
