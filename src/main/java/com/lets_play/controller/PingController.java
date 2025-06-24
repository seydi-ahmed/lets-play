package com.lets_play.controller;

import com.lets_play.model.Ping;
import com.lets_play.repository.PingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ping")
public class PingController {

    private final PingRepository pingRepository;

    public PingController(PingRepository pingRepository) {
        this.pingRepository = pingRepository;
    }

    @GetMapping
    public List<Ping> getAll() {
        return pingRepository.findAll();
    }

    @PostMapping
    public Ping create(@RequestBody Ping ping) {
        return pingRepository.save(ping);
    }
}
