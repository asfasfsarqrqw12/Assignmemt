package Membership;

public final class MembershipFactory {

    private MembershipFactory() {}

    public static MembershipPolicy create(String type) {
        if (type == null) throw new IllegalArgumentException("type is null");

        return switch (type.toLowerCase()) {
            case "monthly" -> new MonthlyMembership();
            case "yearly" -> new YearlyMembership();
            case "visit" -> new VisitBasedMembership();
            default -> throw new IllegalArgumentException("Unknown membership type: " + type);
        };
    }
}