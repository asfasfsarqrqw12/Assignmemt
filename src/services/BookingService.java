package services;

import Entities.FitnessClass;
import Entities.Member;
import Exceptions.BookingAlreadyExistsException;
import Exceptions.ClassFullException;
import Exceptions.MembershipExpiredException;
import Repositories.ClassBookingRepository;
import Repositories.FitnessClassRepository;
import Repositories.MemberRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookingService {

    private final MemberRepository members;
    private final FitnessClassRepository classes;
    private final ClassBookingRepository bookings;

    public BookingService(MemberRepository members, FitnessClassRepository classes, ClassBookingRepository bookings) {
        this.members = members;
        this.classes = classes;
        this.bookings = bookings;
    }

    public void book(long memberId, long classId)
            throws SQLException, MembershipExpiredException, ClassFullException, BookingAlreadyExistsException {

        Member m = members.findById(memberId);
        if (m == null) throw new IllegalArgumentException("Member not found");

        if (m.getMembershipEndDate() != null && m.getMembershipEndDate().isBefore(LocalDate.now())) {
            throw new MembershipExpiredException("Membership expired");
        }

        FitnessClass c = classes.findById(classId);
        if (c == null) throw new IllegalArgumentException("Class not found");

        if (bookings.exists(memberId, classId)) {
            throw new BookingAlreadyExistsException("Booking already exists");
        }

        if (bookings.countByClassId(classId) >= c.getCapacity()) {
            throw new ClassFullException("Class is full");
        }

        bookings.insert(memberId, classId);
    }

    // user story: attendance history
    public List<String> history(long memberId) throws SQLException {
        return bookings.historyLines(memberId);
    }
}
