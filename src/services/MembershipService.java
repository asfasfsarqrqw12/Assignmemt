package services;

import Entities.Member;
import Repositories.MemberRepository;
import Repositories.MembershipTypeRepository;

import java.sql.SQLException;
import java.time.LocalDate;

public class MembershipService {

    private final MemberRepository memberRepo;
    private final MembershipTypeRepository typeRepo;

    public MembershipService(MemberRepository memberRepo,
                             MembershipTypeRepository typeRepo) {
        this.memberRepo = memberRepo;
        this.typeRepo = typeRepo;
    }

    public void buyOrExtend(long memberId, long membershipTypeId) throws SQLException {
        Member member = memberRepo.findById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member not found: " + memberId);
        }

        var type = typeRepo.findById(membershipTypeId);
        if (type == null) {
            throw new IllegalArgumentException("MembershipType not found: " + membershipTypeId);
        }

        LocalDate today = LocalDate.now();
        LocalDate currentEnd = member.getMembershipEndDate();

        LocalDate newEnd;
        if (currentEnd == null || currentEnd.isBefore(today)) {
            newEnd = today.plusDays(type.getDurationDays());
        } else {
            newEnd = currentEnd.plusDays(type.getDurationDays());
        }

        memberRepo.updateMembership(memberId, membershipTypeId, newEnd);
    }
}