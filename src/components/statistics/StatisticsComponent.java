package components.statistics;

import components.classbooking.ClassBookingRepository;

public class StatisticsComponent {

    private final ClassBookingRepository bookingRepo;

    public StatisticsComponent(ClassBookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public int bookingsCountForClass(long classId) throws Exception {
        return bookingRepo.countBookingsForClass(classId);
    }
}
