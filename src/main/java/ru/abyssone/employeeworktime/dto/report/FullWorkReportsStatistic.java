package ru.abyssone.employeeworktime.dto.report;

import lombok.Getter;
import lombok.Setter;
import ru.abyssone.employeeworktime.entity.AbsenceReason;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FullWorkReportsStatistic {
    private String totalMissed;
    private String totalOvertime;
    private Map<AbsenceReason, String> missedForReason = new HashMap<>() {{
        put(AbsenceReason.NO_REASON, null);
        put(AbsenceReason.SICK_LEAVE, null);
        put(AbsenceReason.BUSINESS_TRIP, null);
    }};

    private List<DailyWorkReport> reports = new ArrayList<>();

    public void addReport(DailyWorkReport report) {
        this.reports.add(report);
    }
}
