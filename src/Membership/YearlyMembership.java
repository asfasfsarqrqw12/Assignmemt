package Membership;

import java.time.LocalDate;

public class YearlyMembership implements MembershipPolicy {
    @Override
    public LocalDate calculateNewEndDate(LocalDate today, LocalDate currentEnd) {
        LocalDate base = (currentEnd == null || currentEnd.isBefore(today)) ? today : currentEnd;
        return base.plusDays(365);
    }
}