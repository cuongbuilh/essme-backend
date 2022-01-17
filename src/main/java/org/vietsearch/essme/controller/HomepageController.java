package org.vietsearch.essme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vietsearch.essme.repository.EventRepository;
import org.vietsearch.essme.repository.FooterRepository;
import org.vietsearch.essme.repository.NewsRepository;
import org.vietsearch.essme.service.ExpertService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/homepage")
public class HomepageController {

    @Resource
    ExpertService expertService;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    FooterRepository footerRepository;

    @GetMapping
    public Map<String, ?> get() {
        return Map.of(
                "top_experts", expertService.getTop9ExpertDistinctByRA(),
                "top_news", newsRepository.findAll(PageRequest.of(0, 6)).getContent(),
                "top_events", eventRepository.findAll(PageRequest.of(0, 6)).getContent(),
                "footer", footerRepository.findAll().get(0)
        );
    }
}
