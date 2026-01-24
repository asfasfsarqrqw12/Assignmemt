package services;

import Entities.FitnessClass;
import Entities.Member;
import Repositories.ClassBookingRepository;
import Repositories.FitnessClassRepository;
import Repositories.MemberRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final MemberRepository memberRepo;
    private final FitnessClassRepository classRepo;
    private final ClassBookingRepository bookingRepo;

    public BookingService(MemberRepository memberRepo,
                          FitnessClassRepository classRepo,
                          ClassBookingRepository bookingRepo) {
        this.memberRepo = memberRepo;
        this.classRepo = classRepo;
        this.bookingRepo = bookingRepo;
    }

    public void book(long memberId, long classId)
            throws Exceptions.BookingAlreadyExistsException,
            Exceptions.ClassFullException,
            Exceptions.MembershipExpiredException,
            SQLException {


        Member m = memberRepo.findById(memberId);
        if (m == null) throw new IllegalArgumentException("Member not found: " + memberId);


        LocalDate end = m.getMembershipEndDate();
        if (end == null || end.isBefore(LocalDate.now())) {
            throw new Exceptions.MembershipExpiredException("Membership expired");
        }


        FitnessClass fc = classRepo.findById(classId);
        if (fc == null) throw new IllegalArgumentException("Class not found: " + classId);


        int booked = bookingRepo.countBookingsForClass(classId);
        if (booked >= fc.getCapacity()) {
            throw new Exceptions.ClassFullException("Class is full");
        }


        bookingRepo.create(memberId, classId);
    }

    public List<String> history(long memberId) throws SQLException {

        var bookings = bookingRepo.findByMemberId(memberId);

        List<String> out = new ArrayList<>();
        for (var b : bookings) {
            FitnessClass fc = classRepo.findById(b.getClassId());
            String title = (fc == null ? "unknown" : fc.getTitle());
            String coach = (fc == null ? "unknown" : fc.getCoachName());
            String start = (fc == null || fc.getStartTime() == null) ? "null" : fc.getStartTime().toString();
            String bookedAt = (b.getBookedAt() == null) ? "null" : b.getBookedAt().toString();

            out.add("booking#" + b.getId()
                    + " | " + title
                    + " | coach=" + coach
                    + " | start=" + start
                    + " | booked=" + bookedAt);
        }
        return out;
    }
}
