package Entities;

public class Member {
    private int id;
    private String name;
    private MembershipType membershipType;

    public Member(int id, String name, MembershipType membershipType) {
        this.id = id;
        this.name = name;
        this.membershipType = membershipType;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }
}
