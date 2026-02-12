package components.membership.Membership;

import java.time.LocalDate;

public interface MembershipPolicy {
    LocalDate calculateNewEndDate(LocalDate today, LocalDate currentEnd);
}