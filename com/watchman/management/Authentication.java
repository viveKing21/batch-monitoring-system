package com.watchman.management;

import java.util.List;
import com.watchman.entities.User;
import com.watchman.exceptions.DatabaseException;
import com.watchman.print.Design;
import com.watchman.print.Print;
import com.watchman.services.AdministratorService;

public class Authentication{
    static private User user;
    private List<User> users;

    public Authentication() throws DatabaseException{
        if(Database.databaseExist("auth.ser")){
            user = (User) new Database<User>("auth.ser").getAll().get(0);
        }
        else{
            user = null;
        }
    }
    public boolean loginAsAdmin(String username, String password) throws DatabaseException{
        if(user != null){
            Print.printStyle("You're already logged in!", Design.RED, Design.YELLOW_BACKGROUND, Design.BOLD);
            return false;
        }

        if(username.equals(AdministratorService.USERNAME) && password.equals(AdministratorService.PASSWORD)) {
            user = new User(AdministratorService.UID, username, "admin", password, null);
            new Database<User>("auth.ser").add(user).save();
            return true;
        }
        else{
            Print.printStyle("Invalid credentials!", Design.RED, Design.YELLOW_BACKGROUND, Design.BOLD);
            return false;
        }
    }
    public boolean login(String username, String password) throws DatabaseException{
        if(user != null){
            Print.printStyle("You're already logged in!", Design.RED, Design.YELLOW_BACKGROUND, Design.BOLD);
            return false;
        }

        if(users == null){
            users = new Database<User>("users.ser").getAll();
        };

        for(User userInfo : users){
            if(username.equals(userInfo.USERNAME) && password.equals(userInfo.PASSWORD)){
                user = userInfo;
                break;
            }
        }

        if(user == null){
            Print.printStyle("Invalid credentials!", Design.RED, Design.YELLOW_BACKGROUND, Design.BOLD);
            return false;
        }
        
        new Database<User>("auth.ser").add(user).save();
        
        return true;
    }
}
