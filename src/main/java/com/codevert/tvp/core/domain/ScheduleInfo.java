package com.codevert.tvp.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleInfo {

    private String title;

    private String type;

    private LocalDateTime date;
}
