package com.wcc.pbl230801.pblService.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeUtils {

    public static ZonedDateTime getTaiwanTime() {
        // 取得台灣的時區
        ZoneId taiwanZone = ZoneId.of("Asia/Taipei");
        // 獲取當前台灣時間
        ZonedDateTime taiwanTime = ZonedDateTime.now(taiwanZone);
        return taiwanTime;
    }
}
