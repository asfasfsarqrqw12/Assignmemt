package services;

import Entities.Member;
import Repositories.MemberRepository;
import components.membership.MembershipTypeRepository;

import components.membership.Membership.MembershipFactory;
import components.membership.Membership.MembershipPackage;
import components.membership.Membership.MembershipPolicy;

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
        if (member == null) throw new IllegalArgumentException("Member not found: " + memberId);

        var type = typeRepo.findById(membershipTypeId);
        if (type == null) throw new IllegalArgumentException("MembershipType not found: " + membershipTypeId);


        MembershipPackage pkg = new MembershipPackage.Builder()
                .type(type.getName())
                .durationDays(type.getDurationDays())
                .price(type.getPrice() == null ? 0 : type.getPrice().doubleValue())
                .build();


        MembershipPolicy policy = MembershipFactory.create(pkg);

        LocalDate today = LocalDate.now();
        LocalDate newEnd = policy.calculateNewEndDate(today, member.getMembershipEndDate());

        memberRepo.updateMembership(memberId, membershipTypeId, newEnd);
    }
}
