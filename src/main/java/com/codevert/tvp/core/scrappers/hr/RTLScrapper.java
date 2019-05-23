package com.codevert.tvp.core.scrappers.hr;

import com.codevert.tvp.core.domain.Channel;
import com.codevert.tvp.core.domain.ScheduleInfo;
import com.codevert.tvp.core.scrappers.Scrapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RTLScrapper implements Scrapper<Channel> {

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:47.0) Gecko/20100101 Firefox/47.0";
    private static final String RTL_URL = "https://www.rtl.hr/televizija/tv-raspored/";

    private final Logger log = LoggerFactory.getLogger(RTLScrapper.class);

    @Override
    public List<Channel> extract() throws IOException {
        log.info("Scrapping from '{}'", RTL_URL);

        Connection connection = Jsoup.connect(RTL_URL);
        connection.userAgent(USER_AGENT);
        Document document = connection.get();

        log.info("User-Agent : {}", USER_AGENT);
        log.info("Document Title : {}", document.title());
        log.info("Charset : {}", document.charset());

        List<Channel> channels = new ArrayList<>();
        Elements channelGroups = document.select("div.ChannelGroup-item");

        for (Element channelGroup : channelGroups) {
            Channel channel = extractChannel(channelGroup);
            channels.add(channel);
        }

        log.info("Scrapped {} channels", channels.size());
        return channels;
    }

    private Channel extractChannel(Element channelGroup) {
        Channel channel = new Channel();
        Element h1 = channelGroup.getElementsByTag("h1").first();
        Element channelName = h1.select("span.u-isHiddenVisually").first();
        channel.setName(channelName.html());
        channel.setChannelKey(channel.getName().replace(" ", "_").toUpperCase());

        Elements schedules = channelGroup.getElementsByTag("ul").first().getElementsByTag("li");
        for (Element schedule : schedules) {
            try {
                Element inner = schedule.select("div.Schedule-inner").first();
                String name = inner.getElementsByClass("Schedule-title").first().text();
                String type = StringUtils.capitalize(inner.getElementsByClass("Schedule-type").first().text());
                String date = inner.getElementsByTag("time").first().attr("datetime");

                ScheduleInfo scheduleInfo = new ScheduleInfo(name, type,
                        LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                channel.getSchedules().add(scheduleInfo);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return channel;
    }
}