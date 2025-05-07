package org.coolCompany.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class WorkTimeReport {
    @JacksonXmlProperty(localName = "totalHours")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private double totalHours; // всего часов в месяце
    private boolean exceedsMonthlyLimit; // в данном месяце суммарное рабочее время > 80 часов
    private boolean exceedsWeeklyLimit; //  в данном месяце были недели, когда суммарное рабочее время > 36 часов
    private boolean exceedsDailyLimit; // в данном месяце были дни, когда суммарное рабочее время > 8 часов
    WorkTimeReport(){}
    public double getTotalHours() {
        return totalHours;
    }
    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
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
}
