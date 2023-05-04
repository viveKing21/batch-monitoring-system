package com.watchman.entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.watchman.services.DatabaseService;

public class Batch implements DatabaseService, Serializable{
    final private String ID;
    private String courseName;
    private int numberOfSeats;
    private LocalDate startDate;
    private int durationInDays;
    
    public Batch(String id, String courseName, int numberOfSeats, LocalDate startDate, int durationInDays) {
        this.ID = id;
        this.courseName = courseName;
        this.numberOfSeats = numberOfSeats;
        this.startDate = startDate;
        this.durationInDays = durationInDays;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public int getDurationInDays() {
        return durationInDays;
    }
    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }
    @Override
    public String getID() {
        return ID;
    }
    @Override
    public String toString() {
        return "Batch [ID=" + ID + ", courseName=" + courseName + ", numberOfSeats=" + numberOfSeats + ", startDate="
                + startDate + ", durationInDays=" + durationInDays + "]";
    }
    
}
