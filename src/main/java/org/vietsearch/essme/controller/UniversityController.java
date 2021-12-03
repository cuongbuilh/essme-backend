package org.vietsearch.essme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.web.bind.annotation.*;
import org.vietsearch.essme.model.Expert;
import org.vietsearch.essme.model.University;
import org.vietsearch.essme.repository.UniversityRepository;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/university")
public class UniversityController {
    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping()
    public List<University> search() {
//        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(what);
        return universityRepository.findAll();
    }

}
