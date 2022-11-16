package Entity;

public class User {

    public String User_username;
    public String User_password;

    public User(String User_username, String User_password) {
        this.User_password = User_password;
        this.User_username = User_username;
    }

    public String getUser_username() {
        return this.User_username;
    }

    public String getUser_password() {
        return this.User_password;
    }

    public boolean setUser_username(String s) {
        if (s == null || s.equals("")) {
            System.out.println("\nUser's username can not be empty or null.");
            return false;
        }
        this.User_username = s;
        return true;
    }

    public boolean setUser_password(String s) {
        if (s == null || s.equals("")) {
            System.out.println("\nUser's password can not be empty or null.");
            return false;
        }
        this.User_password = s;
        return true;
    }
}
