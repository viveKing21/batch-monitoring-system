package com.watchman.management;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import com.watchman.entities.Batch;
import com.watchman.entities.User;
import com.watchman.exceptions.BatchNotFoundException;
import com.watchman.exceptions.UserNotFoundException;
import com.watchman.services.AdministratorService;
import com.watchman.utils.Messages;
import com.watchman.utils.UidGen;

public class Administrator implements AdministratorService{
    private Database<Batch> batchDB;
    private Database<User> usersDB;

    Administrator(Database<Batch> batchDB, Database<User> usersDB){
        this.batchDB = batchDB;
        this.usersDB = usersDB;
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
            Messages.viewBatch(Arrays.asList(batchDB.get(UID)));
        }
        else{
            throw new BatchNotFoundException("Batch with this ID does not exist.");
        }
    }
    boolean deleteBatch(String UID) throws BatchNotFoundException{
        if(batchDB.exist(UID)){
            batchDB.delete(UID);
            return batchDB.save();
        }
        else{
            throw new BatchNotFoundException("Batch with this ID does not exist.");
        }
    }
    boolean assignFaculty(String facultyID, HashSet<String> batches) throws BatchNotFoundException, UserNotFoundException{
        if(usersDB.exist(facultyID)){
            for(String batchID : batches){
                if(batchDB.exist(batchID)){
                    batchDB.get(batchID).faculties.add(facultyID);
                }
                else{
                    batchDB.restore();
                    throw new BatchNotFoundException("Batch with this ID does not exist.");
                }
            }
        }
        else{
            throw new UserNotFoundException("User with this ID does not exist.");
        }

        return batchDB.save();
    }
}
