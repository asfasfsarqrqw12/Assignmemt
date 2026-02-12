package components.membership.Membership;

public class MembershipPackage {
    private final String type;
    private final Integer durationDays;
    private final double price;

    private MembershipPackage(Builder b) {
        this.type = b.type;
        this.durationDays = b.durationDays;
        this.price = b.price;
    }

    public String getType() { return type; }
    public Integer getDurationDays() { return durationDays; }
    public double getPrice() { return price; }

    public static class Builder {
        private String type;
        private Integer durationDays;
        private double price;

        public Builder type(String type) { this.type = type; return this; }
        public Builder durationDays(Integer durationDays) { this.durationDays = durationDays; return this; }
        public Builder price(double price) { this.price = price; return this; }

        public MembershipPackage build() { return new MembershipPackage(this); }
    }
}
