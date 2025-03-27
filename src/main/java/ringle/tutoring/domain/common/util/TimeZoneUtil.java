package ringle.tutoring.domain.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZoneUtil {

  public static String convertUtcToLocalTime(String utcTime, String timeZone) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    ZonedDateTime utcZonedDateTime = ZonedDateTime.of(
        LocalDateTime.parse(utcTime, formatter),
        ZoneId.of("UTC")
    );

    ZoneId zoneId = ZoneId.of(timeZone);
    ZonedDateTime localDateTime = utcZonedDateTime.withZoneSameInstant(zoneId);
    
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
