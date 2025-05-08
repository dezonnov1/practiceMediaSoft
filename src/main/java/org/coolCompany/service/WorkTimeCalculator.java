package org.coolCompany.service;

import org.coolCompany.model.CrewMember;
import org.coolCompany.model.Flight;
import org.coolCompany.model.FlightSchedule;
import static org.coolCompany.AppConfig.MONTH_YEAR_FORMATTER;
import static org.coolCompany.AppConfig.WEEK_FIELDS;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class WorkTimeCalculator {

    public static Map<Integer,Map<Integer, Map<Integer, List<Flight>>>> groupByMonthAndWeek(List<Flight> flights) {
        //Map<Year, Map<Month,Map<numWeekInMonth, List<Flight>>>>
        //группировка по году - месяцу - недели в месяце
        Map<Integer, Map<Integer, Map<Integer, List<Flight>>>> result = new HashMap<>();
        for (Flight flight : flights) {
            result.computeIfAbsent(flight.getDepartureTime().getYear(),
                    year -> new HashMap<>()).computeIfAbsent(flight.getDepartureTime().getMonthValue(),
                    month -> new HashMap<>()).computeIfAbsent(flight.getDepartureTime().get(WEEK_FIELDS.weekOfMonth()),
                    week -> new ArrayList<>()).add(flight);
        }
        return result;
    }
    /**
     * Возвращает
     * @param schedule
     * @return Map (Integer, Map(String, WorkTimeReport)) = Map (CrewMemberId, Map(MonthYear, WorkTimeReport))
     */
    static public Map<Integer, Map<String, WorkTimeReport>> calculateWorkTime(FlightSchedule schedule) {
        // Map<CrewMemberId, Map<MonthYear, WorkTimeReport>>
        Map<Integer, Map<String, WorkTimeReport>> result  = new HashMap<>();
        if (schedule == null) {
            throw new IllegalArgumentException("FlightSchedule не должен быть null");
        }
        List<CrewMember> crewList = schedule.getCrewMembers();
        List<Flight> flightList = schedule.getFlights();
        if (flightList == null) {
            throw new IllegalArgumentException("Flight list не должен быть null");
        }
        // !!! добавить проверку наличия crewIds из Flight в CrewMembers






        // до сюда проверка
        //Map<monthYear, Map<numWeekInMonth, List<Flight>>>
        Map<Integer,Map<Integer, Map<Integer, List<Flight>>>> groupFlights = groupByMonthAndWeek(flightList);
        for (Map.Entry<Integer, Map<Integer, Map<Integer, List<Flight>>>> yearEntry : groupFlights.entrySet()) {
            int year = yearEntry.getKey();//нужно для создания key (1-я из 2 частей)
            for (Map.Entry<Integer, Map<Integer, List<Flight>>> monthEntry : yearEntry.getValue().entrySet()) {
                int month = monthEntry.getKey();//нужно для создания key (2-я из 2 частей)
                String monthYearKey = LocalDate.of(year, month, 1).format(MONTH_YEAR_FORMATTER);
                // сам KEY для заполнения result


                // временные таблицы memberId время по дням/неделям
                Map<Integer, Map<LocalDate, Double>> dailyWork = new HashMap<>(); // map дневные переработки Crew
                Map<Integer, Map<Integer, Double>> weeklyWork = new HashMap<>(); // map переработки на неделе Crew
                Map<Integer, Double> monthlyWork = new HashMap<>(); // map переработки в месяце Crew

                for (Map.Entry<Integer, List<Flight>> weekEntry : monthEntry.getValue().entrySet()) {
                    int week = weekEntry.getKey();

                    for (Flight flight : weekEntry.getValue()) {
                        long minutes = Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();
                        // получаем количество отработанных минут (можно конечно и в секундах, но это дикость)
                        double hours = minutes / 60.0; // отработанные часы
                        LocalDate flightDate = flight.getDepartureTime().toLocalDate();

                        for (int crewId : flight.getCrewIds()) {
                            // суммируем отработанные часы членом экипажа по дням
                            dailyWork.computeIfAbsent(crewId, k -> new HashMap<>())
                                    .merge(flightDate, hours, Double::sum);//

                            // суммируем отработанные часы членом экипажа по неделям
                            weeklyWork.computeIfAbsent(crewId, k -> new HashMap<>())
                                    .merge(week, hours, Double::sum);//

                            // суммируем все отработанные часы в этот месяц членом экипажа по месяцам
                            monthlyWork.merge(crewId, hours, Double::sum);
                        }
                    }
                }

                // сбор отчётов по каждому члену экипажа
                for (Integer crewId : monthlyWork.keySet()) {
                    double total = monthlyWork.getOrDefault(crewId, 0.0);
                    boolean exceedsMonthly = total > 80;
                    boolean exceedsWeekly = weeklyWork.getOrDefault(crewId, Collections.emptyMap())
                            .values().stream().anyMatch(v -> v > 36);

                    boolean exceedsDaily = dailyWork.getOrDefault(crewId, Collections.emptyMap())
                            .values().stream().anyMatch(v -> v > 8);

                    WorkTimeReport report = new WorkTimeReport();
                    report.setTotalHours(total);
                    report.setExceedsMonthlyLimit(exceedsMonthly);
                    report.setExceedsWeeklyLimit(exceedsWeekly);
                    report.setExceedsDailyLimit(exceedsDaily);

                    result.computeIfAbsent(crewId, k -> new HashMap<>()).put(monthYearKey, report);
                }
            }
        }
        return result;
    }
}
