package com.s_kugel.aldra.common.utils;

import com.s_kugel.aldra.database.repository.FixedClockMapper;
import com.s_kugel.aldra.enums.ClockMode;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DateTimeUtils {

  private final FixedClockMapper fixedClockMapper;

  private static final ZoneId JST = ZoneId.of("Asia/Tokyo");

  @Value("${app.clock.mode}")
  private ClockMode clockMode;

  @Getter //
  @Setter //
  private static Clock clock = Clock.system(JST);

  public static LocalDate today() {
    return LocalDate.now(clock);
  }

  public static LocalDateTime now() {
    return LocalDateTime.now(clock);
  }

  public void loadFixedClock() {
    if (ClockMode.System == clockMode) {
      return;
    }
    try {
      var rows = fixedClockMapper.selectAll();
      if (rows.isEmpty()) {
        log.warn("固定時刻設定のレコードが存在しません。システム時刻をロードします");
        setClock(Clock.system(JST));
        return;
      }
      var row = rows.getFirst();
      var timeZone = ZoneId.of(row.getTimeZone());
      var fixedClock = Clock.fixed(row.getBaseTime().atZone(timeZone).toInstant(), timeZone);
      setClock(fixedClock);
      log.info("固定時刻設定を読み込みました。時刻={}, タイムゾーン={}", row.getBaseTime(), row.getTimeZone());
    } catch (Exception e) {
      log.warn("固定時刻設定の読み込みに失敗しました。システム時刻をロードします。");
      setClock(Clock.system(JST));
    }
  }
}
