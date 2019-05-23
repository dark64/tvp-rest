package com.codevert.tvp.service;

import com.codevert.tvp.core.domain.Channel;
import com.codevert.tvp.core.scrappers.hr.HRTScrapper;
import com.codevert.tvp.core.scrappers.hr.RTLScrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChannelService {

    // Note: placeholder service

    public List<Channel> scrap() throws IOException {
        List<Channel> channels = new ArrayList<>();
        channels.addAll(new RTLScrapper().extract());
        channels.addAll(new HRTScrapper().extract());
        return channels;
    }
}