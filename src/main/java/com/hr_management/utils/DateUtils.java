package com.hr_management.utils;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {

    private static final DateTimeFormatter MM_YYYY = DateTimeFormatter.ofPattern("MM-yyyy");

    public TimeRange fromMonth(String input) {
        try {
            YearMonth ym = YearMonth.parse(input, MM_YYYY);
            LocalDateTime start = ym.atDay(1).atStartOfDay();
            LocalDateTime end = ym.plusMonths(1).atDay(1).atStartOfDay();
            return new TimeRange(start, end);
        } catch (Exception e) {
            throw new RuntimeException("Thời gian không đúng định dạng");
        }
    }


    public record TimeRange(LocalDateTime start, LocalDateTime end) {
    }
}