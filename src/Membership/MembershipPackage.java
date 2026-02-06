package Membership;

public class MembershipPackage {
    private final String type;
    private final Integer durationDays; 
    private final Integer visitLimit;   
    private final double price;

    private MembershipPackage(Builder b) {
        this.type = b.type;
        this.durationDays = b.durationDays;
        this.visitLimit = b.visitLimit;
        this.price = b.price;
    }

    public String getType() { return type; }
    public Integer getDurationDays() { return durationDays; }
    public Integer getVisitLimit() { return visitLimit; }
    public double getPrice() { return price; }

    public static class Builder {
        private String type;
        private Integer durationDays;
        private Integer visitLimit;
        private double price;

        public Builder type(String type) { this.type = type; return this; }
        public Builder durationDays(Integer days) { this.durationDays = days; return this; }
        public Builder visitLimit(Integer limit) { this.visitLimit = limit; return this; }
        public Builder price(double price) { this.price = price; return this; }

        public MembershipPackage build() {
            if (type == null || type.isBlank()) throw new IllegalStateException("type required");
            if (price < 0) throw new IllegalStateException("price must be >= 0");
            return new MembershipPackage(this);
        }
    }
}
