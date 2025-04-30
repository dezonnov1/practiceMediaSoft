package org.coolCompany.service;

import org.coolCompany.model.FlightSchedule;
import java.util.Map;

public class WorkTimeCalculator {
    /**
     * Возвращает
     * @param schedule
     * @return Map (CrewMemberId, Map(MonthYear, WorkTimeReport))
     */
    public Map<String, Map<String, WorkTimeReport>> calculateWorkTime(FlightSchedule schedule) {
        WorkTimeReport s;

        // Возвращает:
        // Map<CrewMemberId, Map<MonthYear, WorkTimeReport>>
        // где WorkTimeReport содержит:
        //   - общее время
        //   - флаги превышений
        return null;
    }
}
