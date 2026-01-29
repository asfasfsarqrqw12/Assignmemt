import Repositories.*;
import Repositories.jdbc.*;
import services.BookingService;
import services.MembershipService;

import java.sql.Connection;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            MemberRepository memberRepo = new JdbcMemberRepository();
            MembershipTypeRepository typeRepo = new JdbcMembershipTypeRepository();
            FitnessClassRepository classRepo = new JdbcFitnessClassRepository();
            ClassBookingRepository bookingRepo = new JdbcClassBookingRepository();

            MembershipService membershipService = new MembershipService(memberRepo, typeRepo);
            BookingService bookingService = new BookingService(memberRepo, classRepo, bookingRepo);

            long memberId = 1;
            long membershipTypeId = 1;
            long classId = 1;

            System.out.println("=== DEMO START ===");


            try (Connection con = edu.aitu.oop3.db.DatabaseConnection.getConnection();
                 Statement st = con.createStatement()) {
                st.executeUpdate("DELETE FROM class_bookings;");
            }


            try {
                membershipService.buyOrExtend(memberId, membershipTypeId);
                System.out.println("Membership updated");
            } catch (Exception e) {
                System.out.println("Membership update error: " + e.getMessage());
            }


            try {
                bookingService.book(memberId, classId);
                System.out.println("Class booked (first time)");
            } catch (Exceptions.BookingAlreadyExistsException | Exceptions.ClassFullException | Exceptions.MembershipExpiredException e) {
                System.out.println("Booking error: " + e.getMessage());
            }



            try {
                bookingService.book(memberId, classId);
                System.out.println("Class booked (second time) - SHOULD NOT HAPPEN");
            } catch (Exceptions.BookingAlreadyExistsException e) {
                System.out.println("Expected exception (booking already exists): " + e.getMessage());
            } catch (Exceptions.ClassFullException | Exceptions.MembershipExpiredException e) {
                System.out.println("Other booking exception: " + e.getMessage());
            }


            try {
                System.out.println("History:");
                for (String line : bookingService.history(memberId)) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.out.println("History error: " + e.getMessage());
            }

            System.out.println("=== DEMO END ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
