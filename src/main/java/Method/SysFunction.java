package Method;
import Entity.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class SysFunction {
    public EntityContainerImpl entityCon = new EntityContainerImpl();
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Cashier> cashiers = new ArrayList<>();
    public ArrayList<Seller> sellers = new ArrayList<>();

    public HashMap<Double, Double> vending_balance;


    public boolean Start(){
        this.users = entityCon.readUserCSV();
        this.sellers = entityCon.readSellerCSV();
        this.cashiers = entityCon.readCashierCSV();
        this.vending_balance = entityCon.set_balance();
        return true;
    }

    public boolean UserRegister(String UserName, String password){
        if(UserName == null || UserName.isEmpty() || password == null || password.isEmpty())  {
            System.out.println("You have not entered a username or a password.");
            return false;
        }
        for(int i = 0; i < UserName.length(); i++) {
            if(Character.isWhitespace(UserName.charAt(i))) {
                System.out.println("Invalid username" +
                        "\nA valid username is composed of letters, numbers or symbols." +
                        "\nWhitespace is not allowed in username. ");
                return false;
            }
        }
        for(int i = 0; i < password.length(); i++) {
            if(Character.isWhitespace(password.charAt(i))) {
                System.out.println("Invalid password" +
                        "\nWhitespace is not allowed in password.");
                return false;
            }
        }
        //With the above check completed, the following code would check existing usernames
        for(int i = 0; i < this.users.size(); i++) {
            User user = this.users.get(i);
            if(UserName.equals(user.getUser_username())) {
                System.out.println("Username already exists.");
                return false;
            }
        }

        //With all the above checks completed, a new member can be registered and added to the csv file.
        this.addUser(UserName, password);

        try {
            PrintWriter writer = new PrintWriter("src/main/resources/User.csv");
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < this.users.size(); i++) {
                User user = this.users.get(i);
                sb.append(user.getUser_username());
                sb.append(",");
                sb.append(user.getUser_password());
                sb.append("\n");
            }
            writer.write(sb.toString());
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean addUser(String UserName, String password){
        this.users.add(new User(UserName, password));
        return true;
    }


    public boolean UserLogin(String UserName, String password) {
        if (UserName == null || password == null || UserName.equals(" ") || password.equals(" ")){
            System.out.println("You can not enter empty username or password");
        }

        for (User a: this.users){
            if (a.getUser_username().equals(UserName) && a.getUser_password().equals(password)){
                return true;
            }
        }
        return false;
    }

    public User getUserByUserName(String userName){
        User returnUser = null;

        for (User a: this.users){
            if (a.getUser_username().equals(userName)){
                returnUser = a;
            }
        }
        return returnUser;
    }

    public Seller sellerLogin(String UserName, String password) {
        if (UserName == null || password == null || UserName.equals(" ") || password.equals(" ")){
            System.out.println("You can not enter empty username or password");
        }

        for (Seller a: this.sellers){
            if (a.getSeller_username().equals(UserName) && a.getSeller_password().equals(password)){
                return a;
            }
        }
        return null;
    }

    public boolean CashierLogin(String UserName, String password) {
        if (UserName == null || password == null || UserName.equals(" ") || password.equals(" ")){
            System.out.println("You can not enter empty username or password");
        }

        for (Cashier a: this.cashiers){
            if (a.getCashier_username().equals(UserName) && a.getCashier_password().equals(password)){
                return true;
            }
        }
        return false;
    }

    public Cashier getCashierByUserName(String userName) {
        Cashier returnCashier = null;

        for (Cashier a: this.cashiers){
            if (a.getCashier_username().equals(userName)){
                returnCashier = a;
            }
        }
        return returnCashier;
    }

}
