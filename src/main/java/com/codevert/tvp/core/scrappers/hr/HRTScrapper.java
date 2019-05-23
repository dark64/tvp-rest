package com.codevert.tvp.core.scrappers.hr;

import com.codevert.tvp.core.domain.Channel;
import com.codevert.tvp.core.domain.ScheduleInfo;
import com.codevert.tvp.core.scrappers.Scrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HRTScrapper implements Scrapper<Channel> {

    private final Logger log = LoggerFactory.getLogger(HRTScrapper.class);

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:47.0) Gecko/20100101 Firefox/47.0";
    private static final String HRT_URL = "https://raspored.hrt.hr/?raspored=1&mreza=%d&datum=%s";

    @Getter
    @AllArgsConstructor
    private enum HRTChannel {
        HRT_1(2, "HRT1", "HRT_1"),
        HRT_2(3, "HRT2", "HRT_2"),
        HRT_3(4, "HRT2", "HRT_3"),
        HRT_4(40, "HRT4", "HRT_4");

        private final int code;
        private final String name;
        private final String channelKey;
    }

    @Override
    public List<Channel> extract() throws IOException {
        List<Channel> channels = new ArrayList<>();
        for (HRTChannel channel : HRTChannel.values()) {
            channels.add(extractChannel(channel));
        }
        log.info("Scrapped {} channels", channels.size());
        return channels;
    }

    private Channel extractChannel(HRTChannel hrtChannel) throws IOException {
        String baseUrl = String.format(HRT_URL, hrtChannel.getCode(), LocalDate.now().toString());
        log.info("Scraping from '{}'", baseUrl);

        Connection connection = Jsoup.connect(baseUrl);
        connection.userAgent(USER_AGENT);

        Document document = connection.get();
        log.info("User-Agent : {}", USER_AGENT);
        log.info("Document Title : {}", document.title());
        log.info("Charset : {}", document.charset());

        Element table = document.select("#tblRaspored").first();
        Elements rows = table.getElementsByTag("tbody").first().children();

        Channel channel = new Channel();
        channel.setName(hrtChannel.getName());
        channel.setChannelKey(hrtChannel.getChannelKey());

        for (Element row : rows) {
            if (row.hasClass("rasporedHeader")) {
                continue;
            }

            StringBuilder stringBuilder = new StringBuilder();
            if (row.getAllElements().hasClass("link")) {
                stringBuilder.append(row.selectFirst("td.link").getElementsByTag("a").first().text());
            } else {
                stringBuilder.append(row.selectFirst("td.sivi").text());
            }

            String title = stringBuilder.toString().replace("*", "");
            String[] time = row.getElementsByTag("th")
                    .first()
                    .getElementById("color1").text()
                    .split("\\.");

            try {
                ScheduleInfo scheduleInfo = new ScheduleInfo(title, "", LocalDate.now().atTime(Integer.parseInt(time[0]), Integer.parseInt(time[1])));
                channel.getSchedules().add(scheduleInfo);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return channel;
    }
}
