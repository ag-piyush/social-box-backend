package com.socialbox.controllers;

import com.socialbox.model.SocialBox;
import com.socialbox.repository.SocialBoxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
@RestController
@Slf4j
public class SocialBoxController {
    private final SocialBoxRepository socialBoxRepository;

    @Autowired
    public SocialBoxController(SocialBoxRepository socialBoxRepository){
        this.socialBoxRepository = socialBoxRepository;
    }

    @GetMapping
    public List<SocialBox> getSample() {
        List<SocialBox> list = this.socialBoxRepository.findAll();
        log.info("{}", list);
        return list;
    }

    @GetMapping("/2")
    public SocialBox getSample2() {
        return SocialBox.builder().id("1").text("text").build();
    }

    @PostMapping
    public SocialBox saveSample(@RequestBody SocialBox socialBox) {
        return this.socialBoxRepository.save(socialBox);
    }
}
