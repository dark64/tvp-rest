package com.codevert.tvp.controller;

import com.codevert.tvp.core.domain.Channel;
import com.codevert.tvp.service.ChannelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private ChannelService channelService;

    public ChannelController() {
        this.channelService = new ChannelService();
    }

    @GetMapping
    private List<Channel> getChannels() throws IOException {
        return channelService.scrap();
    }
}