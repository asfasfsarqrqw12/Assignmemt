package Entities;

import java.time.LocalDate;

public class Member {
    private long id;
    private String fullName;
    private String phone;
    private long membershipTypeId;
    private LocalDate membershipEndDate;

    public Member() {
    }

    public Member(long id, String fullName, String phone, long membershipTypeId, LocalDate membershipEndDate) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.membershipTypeId = membershipTypeId;
        this.membershipEndDate = membershipEndDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getMembershipTypeId() {
        return membershipTypeId;
    }

    public void setMembershipTypeId(long membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }

    public LocalDate getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setMembershipEndDate(LocalDate membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }
}
