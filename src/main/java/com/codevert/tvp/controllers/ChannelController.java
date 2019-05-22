package com.codevert.tvp.controllers;

import com.codevert.tvp.core.domain.Channel;
import com.codevert.tvp.core.scrappers.rtl.RTLScrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @GetMapping
    private List<Channel> getSchedules() throws IOException {
        return new RTLScrapper().extract();
    }
}