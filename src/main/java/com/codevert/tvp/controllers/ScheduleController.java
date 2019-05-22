package com.codevert.tvp.controllers;

import com.codevert.tvp.core.domain.Channel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @GetMapping("/{channelKey}")
    private List<Channel> getSchedulesForChannel(@PathVariable String channelName) throws IOException {
        return new ArrayList<>();
    }
}
