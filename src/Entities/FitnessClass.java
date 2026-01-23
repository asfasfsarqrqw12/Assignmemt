package Entities;

public class FitnessClass {
    private int id;
    private String name;
    private int capacity;

    public FitnessClass(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}

