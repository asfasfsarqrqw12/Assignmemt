package components.membership.Membership;

import java.time.LocalDate;

public class VisitBasedMembership implements MembershipPolicy {
    @Override
    public LocalDate calculateNewEndDate(LocalDate today, LocalDate currentEnd) {
        // пример: 30 дней за пакет визитов (можешь поменять)
        LocalDate base = (currentEnd == null || currentEnd.isBefore(today)) ? today : currentEnd;
        return base.plusDays(30);
    }
}
