package com.watchman.management;

import java.time.LocalDate;

import com.watchman.entities.Batch;
import com.watchman.exceptions.BatchNotFoundException;
import com.watchman.services.AdministratorService;
import com.watchman.utils.UidGen;

public class Administrator implements AdministratorService{
    private Database<Batch> batchDB;
    Administrator(Database<Batch> batchDB){
        this.batchDB = batchDB;
    }
    String createBatch(String courseName, int numberOfSeats, LocalDate startDate, int durationInDays){
        String UID = UidGen.generate();
        Batch batch = new Batch(UID, courseName, numberOfSeats, startDate, durationInDays);
        if(batchDB.add(batch) != null && batchDB.save()) return UID;
        return  null;
    }
    boolean updateBatch(String UID, String courseName, int numberOfSeats, LocalDate startDate, int durationInDays) throws BatchNotFoundException{
        Batch batch = batchDB.get(UID);
        if(batchDB.exist(UID)){
            if(courseName != null) batch.setCourseName(courseName);
            if(numberOfSeats != 0) batch.setNumberOfSeats(numberOfSeats);
            if(startDate != null) batch.setStartDate(startDate);
            if(durationInDays != 0) batch.setDurationInDays(durationInDays);
            return batchDB.save();
        }
        else{
            throw new BatchNotFoundException("Batch with this ID does not exist.");
        }
    }
    void viewBatch(String UID) throws BatchNotFoundException{
        if(batchDB.exist(UID)){
            System.out.println(batchDB.get(UID));
        }
        else{
            throw new BatchNotFoundException("Batch with this ID does not exist.");
        }
    }
}
