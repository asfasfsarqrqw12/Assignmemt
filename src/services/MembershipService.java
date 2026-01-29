package services;

import Entities.Member;
import Entities.MembershipType;
import Repositories.MemberRepository;
import Repositories.MembershipTypeRepository;

import java.sql.SQLException;
import java.time.LocalDate;

public class MembershipService {
    private final MemberRepository members;
    private final MembershipTypeRepository types;

    public MembershipService(MemberRepository members, MembershipTypeRepository types) {
        this.members = members;
        this.types = types;
    }


    public void buyOrExtend(long memberId, long membershipTypeId) throws SQLException {
        Member m = members.findById(memberId);
        if (m == null) throw new IllegalArgumentException("Member not found: " + memberId);

        MembershipType t = types.findById(membershipTypeId);
        if (t == null) throw new IllegalArgumentException("Membership type not found: " + membershipTypeId);

        Integer days = t.getDurationDays();


        if (days == null) {
            members.updateMembership(memberId, membershipTypeId, null);
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate curEnd = m.getMembershipEndDate();
        LocalDate base = (curEnd == null || curEnd.isBefore(today)) ? today : curEnd;

        LocalDate newEnd = base.plusDays(days);
        members.updateMembership(memberId, membershipTypeId, newEnd);
    }
}
