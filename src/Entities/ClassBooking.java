package Entities;

import java.time.LocalDateTime;

public class ClassBooking {

    private long id;
    private long memberId;
    private long classId;
    private LocalDateTime bookedAt;

    public ClassBooking() {
    }

    public ClassBooking(long memberId, long classId) {
        this.memberId = memberId;
        this.classId = classId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}
