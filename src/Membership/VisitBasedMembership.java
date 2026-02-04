package Membership;

import java.time.LocalDate;

public class VisitBasedMembership implements MembershipPolicy {
    @Override
    public LocalDate calculateNewEndDate(LocalDate today, LocalDate currentEnd) {

        return null;
    }
}