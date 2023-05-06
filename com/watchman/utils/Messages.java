package com.watchman.utils;

import java.util.List;

import com.watchman.entities.AppInfo;
import com.watchman.entities.Batch;
import com.watchman.entities.User;
import com.watchman.print.Design;
import com.watchman.print.Print;

public class Messages {
    static Design intro = new Design(100);
    static Design wd = new Design(53);
    static Design batch = new Design(150);

    static{
        wd.setBorder(1);
        wd.setBorder(1, 0, 1, 0);
        wd.setBorderStyle("~");
        wd.setBorderColor(Design.BLUE);
        wd.setPadding(0, 1, 0, 1);
        wd.bold(true);
        wd.setTextColor(Design.BLUE);

        intro.setBorder(2);
        intro.setBorderStyle("|");
        intro.setBorderColor(Design.RED);
        intro.setPadding(1);
        intro.italic(true);

        batch.setBorder(1,0,1,0);
        batch.setBorderStyle("-");
        batch.setBorderColor(Design.RED);
        batch.setPadding(0, 1, 0, 1);
    }
    // util methods
    public static void optionsTitle(String title, boolean cancelable){
        if(cancelable){
            Print.printlnStyle(title + " (TYPE '"+Print.wrapStyle('C', Design.GREEN, Design.BOLD, Design.ITALIC, Design.UNDERLINE)+"' FOR CANCELING ANYTIME):-\n", Design.ITALIC, Design.UNDERLINE);
        }
        else{
            Print.printlnStyle(title + " :-\n", Design.ITALIC, Design.UNDERLINE);
        }
    }
    static void option(String title, int index){
        Print.printlnStyle("-" + title +" ("+Print.wrapStyle(index, Design.GREEN)+")", Design.ITALIC, Design.YELLOW);
    }
    static public void optionInput(){
        Print.printStyle("PLEASE SELECT AN OPTION (Enter the code) :" + Design.BLANK, Design.GREEN_BACKGROUND);
    }
    static public void takeInput(String feild, String type){
        if(type != null){
            Print.printStyle(feild + " (" + type +") :" + Design.BLANK, Design.RED_BACKGROUND, Design.WHITE, Design.BOLD);
        }
        else{
            Print.printStyle(feild + " :" + Design.BLANK, Design.RED_BACKGROUND, Design.WHITE, Design.BOLD);
        }
    }
    static public void welcomeUser(String name){
        Print.printDesign("Welcome back '"+Print.wrapStyle(name, Design.GREEN, Design.ITALIC, Design.BOLD)+"' (Happy to see you again :)", wd); 
    }
    static public void selectedOption(String title){
        Print.printlnStyle(title, Design.CYAN, Design.BOLD);
    }

    // welcome messages
    static public void initial(){
        Print.printlnStyle(AppInfo.LOGO, Design.BLUE, Design.BOLD);
        Print.printDesign(AppInfo.DESCRIPTION, intro);
    }
    static public void welcome(){
        Print.printDesign("Welcome to the WatchMan (Batch Monitoring Solution)", wd);
    }
    static public void welcomeOption(){
        optionsTitle("OPTIONS", false);
        option("Login as Administrator", 1);
        option("Login as Faculty Member", 2);
        option("Don't have account? Create new Account", 3);
        Print.printLine(1);
    }

    // admin messages
    static public void adminOption(){
        optionsTitle("HERE ARE A FEW OPTIONS YOU CAN NAVIGATE THROUGH", false);
        option("Create a new batch", 1);
        option("Update existing batch", 2);
        option("Assign faculty to batch", 3);
        option("View all batches", 4);
        option("View a specific batch", 5);
        option("View a batch assinged to a faculty", 6);
        option("Delete a batch", 7);
        option("Logout", 8);
        Print.printLine(1);
    }
    static public void createBatch(){
        selectedOption("Creating a new Batch...");
        Print.printLine(1);
        optionsTitle("PLEASE ENTER BATCH DETAILS", true);
    }
    static public void updateBatch(){
        Print.printLine(1);
        optionsTitle("PLEASE ENTER BATCH DETAILS", true);
    }
    static public void viewBatch(List<Batch> batches){
        
        for(int i = 0; i < batches.size(); i++){

            StringBuilder list = new StringBuilder();
            list.append(Print.wrapStyle("Batch ID: ", Design.BOLD));
            list.append(batches.get(i).getID());
            list.append(Print.wrapStyle(" | ", Design.RED));

            list.append(Print.wrapStyle("Course Name: ", Design.BOLD));
            list.append(batches.get(i).getCourseName());
            list.append(Print.wrapStyle(" | ", Design.RED));

            list.append(Print.wrapStyle("Number of Seats: ", Design.BOLD));
            list.append(batches.get(i).getNumberOfSeats());
            list.append(Print.wrapStyle(" | ", Design.RED));

            list.append(Print.wrapStyle("Start Date: ", Design.BOLD));
            list.append(batches.get(i).getStartDate());
            list.append(Print.wrapStyle(" | ", Design.RED));

            list.append(Print.wrapStyle("Duration (Days): ", Design.BOLD));
            list.append(batches.get(i).getDurationInDays());
            
            list.append(Print.wrapStyle("    ("+Print.wrapStyle(i+1, Design.GREEN)+")", Design.ITALIC));

            System.out.println("\u001B" + Print.wrapDesign(list.toString(), batch).trim());
        }
    }
    static public void viewFaculty(List<User> faculties){
        
        for(int i = 0; i < faculties.size(); i++){

            StringBuilder list = new StringBuilder();
            list.append(Print.wrapStyle("Faculty ID: ", Design.BOLD));
            list.append(faculties.get(i).getID());
            list.append(Print.wrapStyle(" | ", Design.RED)
            );
            list.append(Print.wrapStyle("Username: ", Design.BOLD));
            list.append(faculties.get(i).USERNAME);
            list.append(Print.wrapStyle(" | ", Design.RED));

            list.append(Print.wrapStyle("Phone Number: ", Design.BOLD));
            list.append(faculties.get(i).details.number);
            list.append(Print.wrapStyle(" | ", Design.RED));

            list.append(Print.wrapStyle("State: ", Design.BOLD));
            list.append(faculties.get(i).details.state);
            list.append(Print.wrapStyle(" | ", Design.RED));

            list.append(Print.wrapStyle("ZipCode: ", Design.BOLD));
            list.append(faculties.get(i).details.zipcode);
            
            list.append(Print.wrapStyle("    ("+Print.wrapStyle(i+1, Design.GREEN)+")", Design.ITALIC));

            System.out.println("\u001B" + Print.wrapDesign(list.toString(), batch).trim());;
        }
    }
    
    // faculty/user messages
    static public void userOption(){
        optionsTitle("HERE ARE A FEW OPTIONS YOU CAN NAVIGATE THROUGH", false);
        option("Batch list assigned to me", 1);
        option("Delete account", 2);
        option("Logout", 3);
        Print.printLine(1);
    }

    // errors messages
    static public void error(Exception e){
        Print.printLine(2);
        Print.printlnStyle(" Something Went Wrong :( ", Design.RED_BACKGROUND, Design.WHITE);
        new Exception(Print.wrapStyle(e.getMessage(), Design.RED_BACKGROUND, Design.WHITE));
    }
    static public void inputError(boolean cancelable){
        Print.printLine(1);
        if(cancelable){
            Print.printStyle("PLEASE ENTER A VALID INPUT (PRESS 'C' FOR CANCEL) :"+ Design.BLANK, Design.RED_BACKGROUND, Design.WHITE, Design.BOLD);
        }
        else{
            Print.printStyle("PLEASE ENTER A VALID INPUT :"+ Design.BLANK, Design.RED_BACKGROUND, Design.WHITE, Design.BOLD);
        }
    }
    static public void error(String msg){
        Print.printlnStyle(msg, Design.RED, Design.RED_BACKGROUND, Design.WHITE);
    }
    static public void warning(String msg){
        Print.printlnStyle(msg, Design.RED, Design.YELLOW_BACKGROUND, Design.BOLD);
    }
    // success messages
    static public void success(String msg){
        Print.printlnStyle(msg, Design.BLACK, Design.CYAN_BACKGROUND, Design.BOLD);
    }

}
