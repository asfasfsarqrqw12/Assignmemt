import Repositories.*;
import Repositories.jdbc.*;
import services.BookingService;
import services.MembershipService;

import java.sql.Connection;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            // Создание репозиториев
            MemberRepository memberRepo = new JdbcMemberRepository();
            MembershipTypeRepository typeRepo = new JdbcMembershipTypeRepository();
            FitnessClassRepository classRepo = new JdbcFitnessClassRepository();
            ClassBookingRepository bookingRepo = new JdbcClassBookingRepository();

            // Создание сервисов
            MembershipService membershipService = new MembershipService(memberRepo, typeRepo);
            BookingService bookingService = new BookingService(memberRepo, classRepo, bookingRepo);

            long memberId = 1;
            long membershipTypeId = 1;
            long classId = 1;

            System.out.println("=== DEMO START ===");

            // Очистка таблицы бронирований (если таблица существует)
            try (Connection con = edu.aitu.oop3.db.DatabaseConnection.getConnection();
                 Statement st = con.createStatement()) {
                st.executeUpdate("DELETE FROM class_bookings;");
                System.out.println("Table class_bookings cleared.");
            } catch (Exception e) {
                System.out.println("Table class_bookings does not exist yet: " + e.getMessage());
            }

            // Покупка или продление абонемента
            try {
                membershipService.buyOrExtend(memberId, membershipTypeId);
                System.out.println("Membership updated");
            } catch (Exception e) {
                System.out.println("Membership update error: " + e.getMessage());
            }

            // Первое бронирование
            try {
                bookingService.book(memberId, classId);
                System.out.println("Class booked (first time)");
            } catch (Exceptions.BookingAlreadyExistsException | Exceptions.ClassFullException | Exceptions.MembershipExpiredException e) {
                System.out.println("Booking error: " + e.getMessage());
            }

            // Второе бронирование (ожидаем исключение)
            try {
                bookingService.book(memberId, classId);
                System.out.println("Class booked (second time) - SHOULD NOT HAPPEN");
            } catch (Exceptions.BookingAlreadyExistsException e) {
                System.out.println("Expected exception (booking already exists): " + e.getMessage());
            } catch (Exceptions.ClassFullException | Exceptions.MembershipExpiredException e) {
                System.out.println("Other booking exception: " + e.getMessage());
            }

            // История бронирований
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
