package com.watchman.management;

import com.watchman.entities.Batch;
import com.watchman.exceptions.BatchNotFoundException;
import com.watchman.exceptions.DatabaseException;
import com.watchman.print.*;
import java.time.LocalDate;

public class Main {
    Design defaultDesign(){
        Design design = new Design(100);
        design.setBorder(2);
        design.setBorderStyle("|");
        design.setBorderColor(Design.RED);
        design.setPadding(1);
        design.setTextColor(Design.BLUE);
        return design;
    }
    public static void main(String[] args) throws DatabaseException, BatchNotFoundException{
        Authentication auth = new Authentication();

        // auth.loginAsAdmin("admin", "admin@123");
        
        final Database<Batch> BATCH_DB = new Database<>("batch.ser");

        Administrator admin = new Administrator(BATCH_DB);

        // String b1 = admin.createBatch("Flamingo", 20, LocalDate.now(), 30);

        // admin.updateBatch("bffc8bb5-2a3a-4e29-8189-922a7fcd99c5", "Skycrapper", 0, null, 0);
        admin.viewBatch("bffc8bb5-2a3a-4e29-8189-922a7fcd99c5");
        
    }
}
