package org.coolCompany.service;

import java.util.Objects;

public class WorkTimeReport {
    private double totalHours; // всего часов в месяце
    private boolean exceedsMonthlyLimit; // в данном месяце суммарное рабочее время > 80 часов
    private boolean exceedsWeeklyLimit; //  в данном месяце были недели, когда суммарное рабочее время > 36 часов
    private boolean exceedsDailyLimit; // в данном месяце были дни, когда суммарное рабочее время > 8 часов
    WorkTimeReport(){}
    WorkTimeReport(double totalHours, boolean exceedsMonthlyLimit, boolean exceedsWeeklyLimit,boolean exceedsDailyLimit){
        setTotalHours(totalHours);
        setExceedsMonthlyLimit(exceedsMonthlyLimit);
        setExceedsWeeklyLimit(exceedsWeeklyLimit);
        setExceedsDailyLimit(exceedsDailyLimit);
    }
    public double getTotalHours() {
        return totalHours;
    }
    public void setTotalHours(double totalHours) {
        if (totalHours > 0){
            this.totalHours = totalHours;
        }
        else {
            throw new IllegalArgumentException("Рабочее время не может быть отрицательным!");
        }
    }

    public boolean isExceedsMonthlyLimit() {
        return exceedsMonthlyLimit;
    }
    public void setExceedsMonthlyLimit(boolean exceedsMonthlyLimit) {
        this.exceedsMonthlyLimit = exceedsMonthlyLimit;
    }

    public boolean isExceedsWeeklyLimit() {
        return exceedsWeeklyLimit;
    }
    public void setExceedsWeeklyLimit(boolean exceedsWeeklyLimit) {
        this.exceedsWeeklyLimit = exceedsWeeklyLimit;
    }

    public boolean isExceedsDailyLimit() {
        return exceedsDailyLimit;
    }
    public void setExceedsDailyLimit(boolean exceedsDailyLimit) {
        this.exceedsDailyLimit = exceedsDailyLimit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        WorkTimeReport report = (WorkTimeReport) obj;

        return Objects.equals(totalHours, report.totalHours) &&
                Objects.equals(exceedsMonthlyLimit, report.exceedsMonthlyLimit) &&
                Objects.equals(exceedsWeeklyLimit, report.exceedsWeeklyLimit) &&
                Objects.equals(exceedsDailyLimit, report.exceedsDailyLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalHours, exceedsMonthlyLimit, exceedsWeeklyLimit, exceedsDailyLimit);
    }
}
