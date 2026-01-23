package Entities;

import java.time.LocalDate;

public class MembershipType {
    private String type;
    private LocalDate expiryDate;

    public MembershipType(String type, LocalDate expiryDate) {
        this.type = type;
        this.expiryDate = expiryDate;
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
}
