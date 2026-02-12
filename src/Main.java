import Entities.FitnessClass;
import Repositories.FitnessClassRepository;
import Repositories.MemberRepository;
import Repositories.jdbc.JdbcFitnessClassRepository;
import Repositories.jdbc.JdbcMemberRepository;

import components.classbooking.BookingService;
import components.classbooking.ClassBookingRepository;
import components.classbooking.JdbcClassBookingRepository;

import components.membership.JdbcMembershipTypeRepository;
import components.membership.MembershipTypeRepository;

import components.notification.NotificationComponent;
import components.statistics.StatisticsComponent;

import services.MembershipService;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            MemberRepository memberRepo = new JdbcMemberRepository();
            MembershipTypeRepository typeRepo = new JdbcMembershipTypeRepository();
            FitnessClassRepository classRepo = new JdbcFitnessClassRepository();
            ClassBookingRepository bookingRepo = new JdbcClassBookingRepository();


            MembershipService membershipService = new MembershipService(memberRepo, typeRepo);
            BookingService bookingService = new BookingService(memberRepo, classRepo, bookingRepo);


            NotificationComponent notifier = new NotificationComponent();
            StatisticsComponent stats = new StatisticsComponent(bookingRepo);

            long memberId = 1;
            long membershipTypeId = 1;
            long classId = 1;

            System.out.println("=== DEMO START ===");
            notifier.notifyUser("System started");


            try (Connection con = edu.aitu.oop3.db.DatabaseConnection.getInstance().getConnection();
                 Statement st = con.createStatement()) {
                st.executeUpdate("DELETE FROM class_bookings;");
                System.out.println("Table class_bookings cleared.");
            } catch (Exception e) {
                System.out.println("Table class_bookings clear skipped: " + e.getMessage());
            }


            try {
                membershipService.buyOrExtend(memberId, membershipTypeId);

                System.out.println("Membership updated");
                notifier.notifyUser("Membership updated for memberId=" + memberId);
            } catch (Exception e) {
                System.out.println("Membership update error: " + e.getMessage());
            }


            try {
                bookingService.book(memberId, classId);
                System.out.println("Class booked (first time)");
                notifier.notifyUser("Booking created: memberId=" + memberId + ", classId=" + classId);


                System.out.println("Bookings for class " + classId + ": " + stats.bookingsCountForClass(classId));

            } catch (Exceptions.BookingAlreadyExistsException | Exceptions.ClassFullException | Exceptions.MembershipExpiredException e) {
                System.out.println("Booking error: " + e.getMessage());
            }


            try {
                bookingService.book(memberId, classId);
                System.out.println("Class booked (second time) - SHOULD NOT HAPPEN");
            } catch (Exceptions.BookingAlreadyExistsException e) {
                System.out.println("Expected exception (booking already exists): " + e.getMessage());
                notifier.notifyUser("Duplicate booking blocked (expected)");
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


            try {
                System.out.println("Filtered classes (start hour >= 18):");

                List<FitnessClass> eveningClasses = bookingService.filterClasses(
                        c -> c.getStartTime() != null && c.getStartTime().getHour() >= 18
                );

                for (FitnessClass c : eveningClasses) {
                    System.out.println(
                            "class#" + c.getId()
                                    + " | " + c.getTitle()
                                    + " | coach=" + c.getCoachName()
                                    + " | start=" + (c.getStartTime() == null ? "null" : c.getStartTime())
                    );
                }

                System.out.println("Evening classes count = " + eveningClasses.size());
            } catch (Exception e) {
                System.out.println("Filter demo error: " + e.getMessage());
            }

            notifier.notifyUser("Demo finished");
            System.out.println("=== DEMO END ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
