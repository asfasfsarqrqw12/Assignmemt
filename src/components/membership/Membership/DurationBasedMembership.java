package components.membership.Membership;

import java.time.LocalDate;

public class DurationBasedMembership implements MembershipPolicy {

    private final int days;

    public DurationBasedMembership(int days) {
        if (days <= 0) throw new IllegalArgumentException("days must be > 0");
        this.days = days;
    }

    @Override
    public LocalDate calculateNewEndDate(LocalDate today, LocalDate currentEnd) {
        LocalDate base = (currentEnd == null || currentEnd.isBefore(today)) ? today : currentEnd;
        return base.plusDays(days);
          }
}
