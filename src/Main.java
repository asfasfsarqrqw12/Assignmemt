import Repositories.*;
import Repositories.jdbc.*;
import services.BookingService;
import services.MembershipService;

public class Main {
    public static void main(String[] args) {
        try {
            MemberRepository memberRepo = new JdbcMemberRepository();
            MembershipTypeRepository typeRepo = new JdbcMembershipTypeRepository();
            FitnessClassRepository classRepo = new JdbcFitnessClassRepository();
            ClassBookingRepository bookingRepo = new JdbcClassBookingRepository();

            MembershipService membershipService =
                    new MembershipService(memberRepo, typeRepo);

            BookingService bookingService =
                    new BookingService(memberRepo, classRepo, bookingRepo);

            long memberId = 1;
            long membershipTypeId = 1;
            long classId = 1;

            System.out.println("=== DEMO START ===");

            membershipService.buyOrExtend(memberId, membershipTypeId);
            System.out.println("Membership updated");

            bookingService.book(memberId, classId);
            System.out.println("Class booked");

            System.out.println("History:");
            for (String line : bookingService.history(memberId)) {
                System.out.println(line);
            }

            System.out.println("=== DEMO END ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}