package ringle.tutoring.domain.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZoneUtil {

  public static String convertUtcToLocalTime(LocalDateTime utcTime, String timeZone) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    ZonedDateTime utcZonedDateTime = utcTime.atZone(ZoneId.of("UTC"));

    ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.of(timeZone));

    return localZonedDateTime.format(formatter);
  }
}
