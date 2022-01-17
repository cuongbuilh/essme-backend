package org.vietsearch.essme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vietsearch.essme.model.Footer;
import org.vietsearch.essme.repository.FooterRepository;

@RestController
@RequestMapping("/api/footer")
public class FooterController {

    @Autowired
    FooterRepository footerRepository;

    @GetMapping
    public Footer get() {
        return footerRepository.findAll().get(0);
    }

    @PutMapping
    public Footer update(@RequestBody Footer data) {
        String id = footerRepository.findAll().get(0).getId();
        data.setId(id);
        return footerRepository.save(data);
    }
}
