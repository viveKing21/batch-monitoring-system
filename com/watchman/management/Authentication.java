package com.watchman.management;

import java.util.List;

import com.watchman.entities.User;
import com.watchman.entities.UserDetails;
import com.watchman.exceptions.DatabaseException;
import com.watchman.print.Design;
import com.watchman.print.Print;
import com.watchman.services.AdministratorService;
import com.watchman.utils.Messages;
import com.watchman.utils.UidGen;

public class Authentication{
    Database<User> USER_DB;
    Database<User> AUTH_DB;
    public User user;
    private List<User> users;

    public Authentication(Database<User> USER_DB) throws DatabaseException{
        this.USER_DB = USER_DB;
        this.users = USER_DB.getAll();

        if(Database.databaseExist("auth.ser")){
            AUTH_DB = new Database<User>("auth.ser");
            user = (User) AUTH_DB.getAll().get(0);
        }
        else{
            user = null;
            AUTH_DB = null;
        }
    }
    public boolean loginAsAdmin(String username, String password) throws DatabaseException{
        if(user != null){
            Print.printlnStyle("You're already logged in!", Design.RED, Design.YELLOW_BACKGROUND, Design.BOLD);
            return false;
        }

        if(username.equals(AdministratorService.USERNAME) && password.equals(AdministratorService.PASSWORD)) {
            user = new User(AdministratorService.UID, username, password, "admin", null);
            AUTH_DB = new Database<User>("auth.ser");
            AUTH_DB.add(user).save();
            return true;
        }
        else{
            Messages.warning("Invalid credentials!");
            return false;
        }
    }
    public boolean login(String username, String password) throws DatabaseException{
        if(user != null){
            Print.printlnStyle("You're already logged in!", Design.RED, Design.YELLOW_BACKGROUND, Design.BOLD);
            return false;
        }

        if(users == null){
            users = USER_DB.getAll();
        }

        for(User userInfo : users){
            if(username.equals(userInfo.USERNAME) && password.equals(userInfo.PASSWORD)){
                user = userInfo;
                break;
            }
        }

        if(user == null){
            Messages.warning("Invalid credentials!");
            return false;
        }
        AUTH_DB = new Database<User>("auth.ser");
        AUTH_DB.add(user).save();
        
        return true;
    }
    public boolean register(String username, String password, String number, String state, String city, int zipcode){
        boolean result = USER_DB.add(
            new User(
                UidGen.generate(), 
                username, 
                password, 
                "user",
                new UserDetails(number, state, city, zipcode)
            )
        ).save();

        if(result){
            users = USER_DB.getAll();
            return true;
        }
        return false;
    }
    public boolean logout(){
        if(AUTH_DB != null){
            if(AUTH_DB.deleteDatabase()){
                user = null;
                users = null;
                AUTH_DB = null;
                return true;
            }
        }
        return false;
    }
}
