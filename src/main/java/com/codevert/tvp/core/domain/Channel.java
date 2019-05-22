package com.codevert.tvp.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel {

    private String name;

    private String channelKey;

    private List<ScheduleInfo> schedules = new ArrayList<>();
}
