package com.s_kugel.aldra.database.repository;

import com.s_kugel.aldra.database.entity.gen.BusinessCalendar;
import com.s_kugel.aldra.database.repository.gen.BusinessCalendarMapperDefault;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCalendarMapper extends BusinessCalendarMapperDefault {

  BusinessCalendar findCurrentBusinessDate();
}
