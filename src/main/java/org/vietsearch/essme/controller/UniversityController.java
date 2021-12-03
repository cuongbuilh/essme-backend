package org.vietsearch.essme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.vietsearch.essme.model.Expert;
import org.vietsearch.essme.model.University;
import org.vietsearch.essme.repository.UniversityRepository;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/university")
public class UniversityController {
    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping()
    public List<University> getAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size, @RequestParam(value = "sort", defaultValue = "name") String sortAttr, @RequestParam(value = "desc", defaultValue = "false") boolean desc) {
        Sort sort = Sort.by(sortAttr);
        if (desc)
            sort = sort.descending();
        return universityRepository.findAll(PageRequest.of(page, size,sort)).toList();
    }

    @GetMapping("/search")
    public List<University> search(@RequestParam("what") String what) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(what);
        return universityRepository.findBy(criteria);
    }


    @GetMapping("/{id}")
    public University findById(@PathVariable("id") String id) {
        return universityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "University not found", null));
    }




}
