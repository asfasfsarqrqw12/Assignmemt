package Entities;

import java.time.LocalDateTime;

public class FitnessClass {
    private long id;
    private String title;
    private String coachName;
    private LocalDateTime startTime;
    private int capacity;

    public FitnessClass() {
    }

    public FitnessClass(long id, String title, String coachName, LocalDateTime startTime, int capacity) {
        this.id = id;
        this.title = title;
        this.coachName = coachName;
        this.startTime = startTime;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
