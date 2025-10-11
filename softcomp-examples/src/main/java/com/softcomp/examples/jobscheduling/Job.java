package com.softcomp.examples.jobscheduling;

public class Job {
    private final int id;
    private final int processingTime;

    public Job(int id, int processingTime, int dueDate, int weight) {
        this.id = id;
        this.processingTime = processingTime;
    }

    public int getId() {
        return id;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public String toString() {
        return "Job{" + "id=" + id + ", p=" + processingTime + '}';
    }
}