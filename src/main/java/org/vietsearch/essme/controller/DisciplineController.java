package org.vietsearch.essme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.vietsearch.essme.model.academic_disciplines.Discipline;
import org.vietsearch.essme.repository.academic_disciplines.DisciplineCustomRepoImpl;
import org.vietsearch.essme.repository.academic_disciplines.DisciplineRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/discipline")
public class DisciplineController {
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private DisciplineCustomRepoImpl disciplineCustomRepoIpml;

    @GetMapping
    public List<Discipline> getDisciplines(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "20") int size,
                                           @RequestParam(name = "lang", defaultValue = "en") String lang,
                                           @RequestParam(name = "sort", defaultValue = "name") String sortAttr,
                                           @RequestParam(name = "asc", defaultValue = "true") boolean asc) {
        Sort sort = null;
        if (sortAttr.equals("name")) {
            sort = Sort.by("names." + lang);
        } else if (sortAttr.equals("level")) {
            sort = Sort.by("level");
        }
        sort = asc ? sort.ascending() : sort.descending();

        Page<Discipline> disciplinePage = disciplineRepository.findAll(PageRequest.of(page, size, sort));
        return disciplinePage.getContent();
    }

    @GetMapping("/{_id}")
    public Discipline getDisciplineById(@PathVariable("_id") String _id) {
        Optional<Discipline> optionalDiscipline = disciplineRepository.findById(_id);
        optionalDiscipline.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline not found"));
        return optionalDiscipline.get();
    }

    @GetMapping("/search")
    public List<Discipline> searchDisciplines(@RequestParam("text") String text) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).matchingPhrase(text);
        return disciplineRepository.findBy(criteria);
    }

    @GetMapping("/parent")
    public Object parent(@RequestParam(name = "name", required = false) String name) {
        if (!Objects.equals(name, null)) {
            return disciplineCustomRepoIpml.findByParentId(name);
        } else {
            return disciplineCustomRepoIpml.getNumberOfDisciplinesInEachParent();
        }
    }

    @DeleteMapping("/{_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteById(@PathVariable("_id") String _id) {
        disciplineRepository.deleteById(_id);
    }

    @PutMapping("/{_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Discipline updateById(@PathVariable("_id") String _id, @RequestBody Discipline discipline) {
        disciplineRepository.findById(_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline not found"));
        discipline.set_id(_id);
        return disciplineRepository.save(discipline);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Discipline addDiscipline(@RequestBody Discipline discipline) {
        return disciplineRepository.insert(discipline);
    }
}
