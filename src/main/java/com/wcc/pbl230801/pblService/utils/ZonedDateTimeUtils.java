package com.wcc.pbl230801.pblService.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeUtils {

    public static ZonedDateTime getTaiwanTime() {
        // 取得台灣的時區
        ZoneId taiwanZone = ZoneId.of("Asia/Taipei");
        // 獲取當前台灣時間
        ZonedDateTime taiwanTime = ZonedDateTime.now(taiwanZone);
        return taiwanTime;
    }

    public static ZonedDateTime getZonedDateTime(String dateString, String timeString) {
        // 取得台灣的時區
        ZoneId taiwanZone = ZoneId.of("Asia/Taipei");

        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

        LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

        ZonedDateTime zonedDateTime = ZonedDateTime.of(date, time, taiwanZone);

        return zonedDateTime;
    }
}
