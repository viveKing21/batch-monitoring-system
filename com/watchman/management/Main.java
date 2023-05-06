package com.watchman.management;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.watchman.entities.Batch;
import com.watchman.entities.User;
import com.watchman.exceptions.BatchNotFoundException;
import com.watchman.exceptions.DatabaseException;
import com.watchman.exceptions.UserNotFoundException;
import com.watchman.print.*;
import com.watchman.utils.Messages;
import com.watchman.utils.InputTaking;

public class Main {
    public static void main(String[] args) throws DatabaseException, BatchNotFoundException, UserNotFoundException{

        Database<User> USER_DB = new Database<>("users.ser");

        Messages.initial();

        InputTaking it = new InputTaking();

        Authentication auth = new Authentication(USER_DB);


        MainLoop:
        while (true) {
            if(auth.user == null){
                // login page
                Messages.welcome();

                WelcomeLoop:
                while (true) {
                    Messages.welcomeOption();
                    Messages.optionInput();
                    Integer welcomeOption = it.integer(false);
            
                    Print.printLine(1);
            
                    try{
                        if(welcomeOption == 1 || welcomeOption == 2){
                            if(welcomeOption == 1){
                                Messages.selectedOption("Login as Administrator...");
                            }
                            else{
                                Messages.selectedOption("Login as Faculty Member...");
                            }
                            Print.printLine(1);
                            
                            Messages.optionsTitle("PLEASE ENTER ACCOUNT DETAILS", true);

                            LoginLoop:
                            while (true) {
                                Messages.takeInput("Username", null);
                                String username = it.string();
                                Print.printLine(1);
                                if(username == null) continue WelcomeLoop;

                                Messages.takeInput("Password", null);
                                String password = it.string();
                                if(password == null) continue WelcomeLoop;
                                Print.printLine(1);

                                if(welcomeOption == 1 && auth.loginAsAdmin(username, password)) continue MainLoop;
                                if(welcomeOption == 2 && auth.login(username, password)) continue MainLoop;

                                Print.printLine(1);
                                continue LoginLoop;
                            }
                        }
                        else if(welcomeOption == 3){
                            Messages.selectedOption("Create a new Account...");
                            Print.printLine(1);

                            Messages.optionsTitle("PLEASE ENTER ACCOUNT DETAILS", true);

                            Messages.takeInput("Username", "String");
                            String username = it.string();
                            Print.printLine(1);
                            if(username == null) continue WelcomeLoop;
                            
                            Messages.takeInput("Password", "String");
                            String password = it.string();
                            Print.printLine(1);
                            if(password == null) continue WelcomeLoop;
                            
                            Messages.takeInput("Contact Number", "String");
                            String number = it.string();
                            Print.printLine(1);
                            if(number == null) continue WelcomeLoop;

                            Messages.takeInput("State", "String");
                            String state = it.string();
                            Print.printLine(1);
                            if(state == null) continue WelcomeLoop;

                            Messages.takeInput("City", "String");
                            String city = it.string();
                            Print.printLine(1);
                            if(city == null) continue WelcomeLoop;

                            Messages.takeInput("Zipcode", "Integer");
                            Integer zipcode = it.integer(true);
                            Print.printLine(1);
                            if(zipcode == null) continue WelcomeLoop;

                            auth.register(username, password, number, state, city, zipcode);
                            Messages.success(" Your account created successfully ");
                            Print.printLine(1);

                            continue WelcomeLoop;
                        }
                    }
                    catch(DatabaseException e){
                        Messages.error(e);
                    }
                }
            }
            else{
                // databases
                Database<Batch> BATCH_DB = new Database<>("batch.ser");
                List<Batch> batchList = BATCH_DB.getAll();
                List<User> facultyList = USER_DB.getAll();

                Messages.welcomeUser(auth.user.USERNAME);

                if(auth.user.ROLE.equals("admin")){
                    Administrator admin = new Administrator(BATCH_DB, USER_DB);

                    AdminLoop:
                    while(true){
                        Messages.adminOption();
                        Messages.optionInput();
    
                        Integer adminOption = it.integer(false);
    
                        Print.printLine(1);
    
                        switch(adminOption){
                            case 1:{
                                Messages.createBatch();
                                Messages.takeInput("Course Name", "String");
                                String courseName = it.string();
                                if(courseName == null) continue AdminLoop;
                                Print.printLine(1);

                                Messages.takeInput("Number of Seats", "Integer");
                                Integer numberOfSeats = it.integer(true);
                                if(numberOfSeats == null) continue AdminLoop;
                                Print.printLine(1);

                                Messages.takeInput("Start Date", "DD/MM/YYYY");
                                LocalDate date = it.date("dd/MM/yyyy");
                                if(date == null) continue AdminLoop;
                                Print.printLine(1);


                                Messages.takeInput("Duration in Days", "Integer");
                                Integer durationInDays = it.integer(true);
                                if(durationInDays == null) continue AdminLoop;
                                Print.printLine(1);

                                String UID = admin.createBatch(courseName, numberOfSeats, date, durationInDays);

                                if(UID == null){
                                    Messages.error("Failed to create new batch.");
                                    continue AdminLoop;
                                }
                                
                                Messages.success(" New batch created successfully (Batch ID) :" + Design.BLANK+ Print.wrapStyle(" " + UID + " ", Design.BLACK_BACKGROUND));
                                Print.printLine(1);
                                batchList = BATCH_DB.getAll();
                                break;
                            }
                            case 2:{
                                Messages.selectedOption("Update existing batch...");
                                Print.printLine(1);
                                if(batchList.size() == 0){
                                    Messages.warning("No batch Found!");
                                    Print.printLine(1);
                                    continue AdminLoop;
                                }
                                Print.printlnStyle("Batch List", Design.RED_BACKGROUND, Design.WHITE);
                                Messages.viewBatch(batchList);
                                Print.printLine(1);

                                Messages.optionInput();

                                Integer selectedBatch = it.integer(true);
                                    
                                if(selectedBatch == null) continue AdminLoop;

                                while(selectedBatch > batchList.size() || selectedBatch <= 0){
                                    Print.printLine(1);
                                    Messages.takeInput("Please enter the number between 1 to " + batchList.size(), "Integer");
                                    
                                    selectedBatch = it.integer(true);

                                    if(selectedBatch == null) continue AdminLoop;
                                }

                                Print.printLine(1);
                                Messages.optionsTitle("PLEASE ENTER UPDATED DETAILS FOR DEFAULT USE 'DF'", true);

                                Messages.takeInput("Course Name", "String");
                                Print.printStyle(" DEFAULT: " + batchList.get(selectedBatch-1).getCourseName() + " " + Design.BLANK, Design.BLACK_BACKGROUND);
                                String courseName = it.string();
                                if(courseName == null) continue AdminLoop;
                                Print.printLine(1);

                                Messages.takeInput("Number of Seats", "Integer");
                                Print.printStyle(" DEFAULT: " + batchList.get(selectedBatch-1).getNumberOfSeats() + " " + Design.BLANK , Design.BLACK_BACKGROUND);
                                Integer numberOfSeats = it.integer(true);
                                if(numberOfSeats == null) continue AdminLoop;
                                Print.printLine(1);

                                Messages.takeInput("Start Date", "DD/MM/YYYY");
                                Print.printStyle(" DEFAULT: " + batchList.get(selectedBatch-1).getStartDate() + " " + Design.BLANK, Design.BLACK_BACKGROUND);
                                LocalDate startDate = it.date("dd/MM/yyyy");
                                if(startDate == null) continue AdminLoop;
                                Print.printLine(1);

                                Messages.takeInput("Duration (Days)", "Integer");
                                Print.printStyle(" DEFAULT: " + batchList.get(selectedBatch-1).getDurationInDays() + " " + Design.BLANK, Design.BLACK_BACKGROUND);
                                Integer duration = it.integer(true);
                                if(duration == null) continue AdminLoop;
                                Print.printLine(1);

                                try{
                                    admin.updateBatch(
                                        batchList.get(selectedBatch-1).getID(),
                                        courseName,
                                        (int)numberOfSeats,
                                        (LocalDate)startDate,
                                        (int)duration
                                    );
                                    Messages.success(" Batch updated successfully (Batch ID) :" + Design.BLANK+ Print.wrapStyle(" " + batchList.get(selectedBatch-1).getID() + " ", Design.BLACK_BACKGROUND));
                                    Print.printLine(1);
                                    batchList = BATCH_DB.getAll();
                                }
                                catch(Exception e){
                                    Messages.error(e);
                                }
                                break;
                            }
                            case 3:{
                                Messages.selectedOption("Assign faculty to batch...");
                                Print.printLine(1);
                                if(facultyList.size() == 0){
                                    Messages.warning("No faculties Found!");
                                    Print.printLine(1);
                                    continue AdminLoop;
                                }
                                if(batchList.size() == 0){
                                    Messages.warning("No batch Found!");
                                    Print.printLine(1);
                                    continue AdminLoop;
                                }
                                Print.printlnStyle("Faculty List", Design.RED_BACKGROUND, Design.WHITE);
                                Messages.viewFaculty(facultyList);
                                Print.printLine(1);

                                Messages.optionInput();

                                Integer facultyId = it.integer(true);

                                if(facultyId == null) continue AdminLoop;

                                while(facultyId > facultyList.size() || facultyId <= 0){
                                    Print.printLine(1);
                                    Messages.takeInput("Please enter the number between 1 to " + facultyList.size(), "Integer");
                                    
                                    facultyId = it.integer(true);

                                    if(facultyId == null) continue AdminLoop;
                                }

                                Print.printLine(1);
                                Print.printStyle("Selected faculty '"+facultyList.get(facultyId-1).USERNAME+"' ("+Print.wrapStyle(facultyId, Design.GREEN)+") :", Design.BLUE, Design.BOLD);
                                Print.printLine(2);
                                
                                Print.printlnStyle("Batch List", Design.RED_BACKGROUND, Design.WHITE);
                                Messages.viewBatch(batchList);
                                Print.printLine(1);

                                Messages.optionsTitle("PLEASE ENTER THE BATCH NUMBER TO BE ASSIGN TO THIS FACULTIY", true);

                                Messages.takeInput("Batch Number", "Space Separated Integers");

                                while(true){
                                    String batchCode = it.string().trim();

                                    if(batchCode == null) continue AdminLoop;

                                    String[] list = batchCode.split(" ");

                                    HashSet<String> batchIdList = new HashSet<>();
                                    
                                    try{
                                        for(String code : list){
                                            int i = Integer.parseInt(code);
                                            if(i <= 0 || i > batchList.size()) throw new Exception();
                                            batchIdList.add(batchList.get(i-1).getID());
                                        }
                                    }
                                    catch(Exception e){
                                        Print.printLine(1);
                                        Messages.takeInput("Please enter valid batch numbers", "Space Separated Integers");
                                        continue;
                                    }

                                    try{
                                        Print.printLine(1);
                                        admin.assignFaculty(facultyList.get(facultyId-1).getID(), batchIdList);
                                        Messages.success(" Faculty successfully assigned to these batches ");
                                        Print.printLine(1);
                                        batchList = BATCH_DB.getAll();
                                    }
                                    catch(Exception e){
                                        Print.printLine(1);
                                        Messages.error(e.getMessage());
                                        Print.printLine(1);
                                    }
                                    break;
                                }
                                break;
                            }
                            case 4:{
                                Messages.selectedOption("View all batches...");
                                Print.printLine(1);
                                if(batchList.size() == 0){
                                    Messages.warning("No batch Found!");
                                    Print.printLine(1);
                                    continue AdminLoop;
                                }
                                Print.printlnStyle("Batch List", Design.RED_BACKGROUND, Design.WHITE);
                                Messages.viewBatch(batchList);
                                Print.printLine(1);
                                break;
                            }
                            case 5:{
                                Messages.selectedOption("View a specific batch...");
                                Print.printLine(1);
                                Messages.takeInput("Batch ID", "String");

                                while(true){
                                    try{
                                        String batchId = it.string();
                                    
                                        if(batchId == null) continue AdminLoop;
    
                                        admin.viewBatch(batchId);
                                        Print.printLine(1);

                                        break;
                                    }
                                    catch(BatchNotFoundException e){
                                        Print.printLine(1);
                                        Messages.takeInput("Please enter a valid Batch ID", "String");
                                    }
                                }
                                break;
                            }
                            case 6:{
                                Messages.selectedOption("View a batch assigned to a faculty...");
                                Print.printLine(1);

                                Print.printlnStyle("Faculty List", Design.RED_BACKGROUND, Design.WHITE);
                                Messages.viewFaculty(facultyList);

                                Print.printLine(1);
                                Messages.optionsTitle("PLEASE ENTER FACULTY NUMBER", true);
                                Messages.takeInput("Faculty ID", "Integer");

                                Integer facultyId = it.integer(true);

                                if(facultyId == null) continue AdminLoop;

                                while(facultyId <= 0 || facultyId > facultyList.size()){
                                    Print.printLine(1);
                                    Messages.takeInput("Please enter a valid Faculty ID", "Integer");
                                    facultyId = it.integer(true);
                                    if(facultyId == null) continue AdminLoop;
                                }

                                Print.printLine(1);
                                
                                List<Batch> assignedBatch = new ArrayList<>();

                                for(Batch batch : batchList){
                                    if(batch.faculties.contains(facultyList.get(facultyId-1).getID())){
                                        assignedBatch.add(batch);
                                    }
                                }

                                if(assignedBatch.size() > 0){
                                    Print.printlnStyle("Batch List", Design.RED_BACKGROUND, Design.WHITE);
                                    Messages.viewBatch(assignedBatch);
                                }
                                else{
                                    Messages.warning("No batches assigned to this faculty");
                                }
                                Print.printLine(1);

                                break;
                            }
                            case 7:{
                                Messages.selectedOption("Delete a batch...");
                                Print.printLine(1);
                                if(batchList.size() == 0){
                                    Messages.warning("No batch Found!");
                                    Print.printLine(1);
                                    continue AdminLoop;
                                }
                                Print.printlnStyle("Batch List", Design.RED_BACKGROUND, Design.WHITE);
                                Messages.viewBatch(batchList);
                                Print.printLine(1);

                                Messages.optionsTitle("PLEASE ENTER BATCH ID YOU WANT TO DELETE", true);
                                
                                Messages.takeInput("Batch ID", "String");

                                String batchId = it.string();
                                    
                                if(batchId == null) continue AdminLoop;

                                while(!BATCH_DB.exist(batchId)){
                                    Print.printLine(1);
                                    Messages.takeInput("Please enter a valid Batch ID", "String");
                                    batchId = it.string();
                                    if(batchId == null) continue AdminLoop;
                                }

                                try{
                                    Print.printLine(1);
                                    admin.deleteBatch(batchId);
                                    batchList = BATCH_DB.getAll();
                                    Messages.success(" Batch deleted successfully (Batch ID) :" + Design.BLANK+ Print.wrapStyle(" " + batchId + " ", Design.BLACK_BACKGROUND));
                                }
                                catch(BatchNotFoundException e){
                                    Messages.error(e);
                                }
                                Print.printLine(1);

                                break;
                            }
                            case 8: {
                                auth.logout();
                                Print.printLine(1);
                                Messages.success(" Logout successffully ");
                                Print.printLine(1);
                                Messages.initial();
                                continue MainLoop;
                            }
                        }
                    }
                }
                else if(auth.user.ROLE.equals("user")){
                    while (true) {
                        Messages.userOption();
                        Messages.optionInput();

                        Integer userOption = it.integer(false);
    
                        Print.printLine(1);

                        switch(userOption){
                            case 1:{
                                Messages.selectedOption("Batch list assigned to me...");
                                Print.printLine(1);

                                List<Batch> assignedBatch = new ArrayList<>();

                                for(Batch batch : batchList){
                                    if(batch.faculties.contains(auth.user.getID())){
                                        assignedBatch.add(batch);
                                    }
                                }

                                if(assignedBatch.size() > 0){
                                    Print.printlnStyle("Batch List", Design.RED_BACKGROUND, Design.WHITE);
                                    Messages.viewBatch(assignedBatch);
                                }
                                else{
                                    Messages.warning("No batches assigned to this faculty");
                                }
                                Print.printLine(1);
                                break;
                            }
                            case 2:{
                                Messages.selectedOption("Delete account...");
                                Print.printLine(1);

                                for(Batch batch : batchList){
                                    if(batch.faculties.contains(auth.user.getID())){
                                        batch.faculties.remove(auth.user.getID());
                                    }
                                }
                                try{
                                    if(USER_DB.delete(auth.user.getID()).save()){
                                        Print.printLine(1);
                                        Messages.success(" Account deleted successffully ");
                                        Print.printLine(1);
                                        auth.logout();
                                        Messages.initial();
                                        continue MainLoop;
                                    }
                                    else throw new Exception();
                                }
                                catch(Exception e){
                                    Messages.error(e);
                                }
                                Print.printLine(1);
                                break;
                            }
                            case 3: {
                                auth.logout();
                                Print.printLine(1);
                                Messages.success(" Logout successffully ");
                                Print.printLine(1);
                                Messages.initial();
                                continue MainLoop;
                            }
                        }
                    }
                }
            }
        }

        
    }
}
