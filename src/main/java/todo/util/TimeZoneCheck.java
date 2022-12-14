package todo.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.TimeZone;

public final class TimeZoneCheck {
    public static boolean check(String timeZone) {
        try {
            LocalDateTime.now()
                    .atZone(TimeZone.getDefault().toZoneId())
                    .withZoneSameInstant(ZoneId.of(timeZone));
            return true;
        } catch (ZoneRulesException e) {
            return false;
        }
    }
}
