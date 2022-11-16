package Controller;
import Entity.*;
import Method.*;
import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class MainController {
    public SysFunction sf = new SysFunction();
    public FrontEndInteraction fe = new FrontEndInteraction();
    public EntityContainerImpl entityCon = new EntityContainerImpl();
    public User loginUser = null;
    public Seller loginSeller = null;
    public Cashier loginCashier = null;

    public HashMap<Double, Double> vending_balance = entityCon.set_balance();

    public void start() throws IOException {
        sf.Start();
        while (true){

            int selection = 0;

            selection = fe.displayMenu("Welcome, Please select your operation",
                    new String[]{"UserLogin","UserRegister","Anonymous User", "SellerLogin", "CashierLogin","OwnerLogin","Exit"},
                    "Please enter a selection");


            if (selection == 1){
                String userName = fe.getString("Please enter a valid username:");
                String password;
                final JPasswordField pf = new JPasswordField();

                if (System.console() == null) {
                    password = JOptionPane.showConfirmDialog(null, pf, "Please enter a valid password:",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION
                            ? new String(pf.getPassword()) : "";
                } else {
                    password = new String(System.console().readPassword("Please enter a valid password:"));
                }

                boolean logSuccess =  sf.UserLogin(userName,password);
                if (logSuccess){
                    this.loginUser = sf.getUserByUserName(userName);
                    System.out.println("You have logged in successfully.");

                    UserController us = new UserController(this);
                    us.execPage();
                }else{
                    System.out.println("Failed to login");
                }

            }else if(selection == 2){
                //TO DO
                String[] usernameAndPassword = getUsernameAndPassword(fe,
                        "Please enter a valid username:", "Please enter a valid seller password:");
                String userName = usernameAndPassword[0];
                String password = usernameAndPassword[1];
                boolean registerSuccess = sf.UserRegister(userName,password);
                if(registerSuccess){
                    int sizeofMembers = sf.users.size();

                    this.loginUser = sf.users.get(sizeofMembers - 1);
                    System.out.println("You have successfully registered.");

                    //Go to user controller
                    UserController uc = new UserController(this);
                    uc.execPage();

                }else{
                    System.out.println("You have not registered successfully");

                }

            }else if (selection == 3){
                //Go to user controller
                String UserName = "Anonymous";
                String password = "password";
                boolean logSuccess =  sf.UserLogin(UserName,password);
                if (logSuccess){
                    this.loginUser = sf.getUserByUserName(UserName);
                    UserController uc = new UserController(this);
                    uc.execPage();
                }else{
                    System.out.println("Failed to login as an Anonymous User");
                }

            } else if (selection == 4) {
                sellerLogin(fe, new SellerController(this));
            } else if (selection == 5){
                String userName = fe.getString("Please enter a valid Cashier username:");
                String password;
                if (System.console() == null) {
                    final JPasswordField pf = new JPasswordField();
                    password = JOptionPane.showConfirmDialog(null, pf, "Please enter a valid password:",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION
                            ? new String(pf.getPassword()) : "";
                } else {
                    password = new String(System.console().readPassword("Please enter a valid password:"));
                }

                boolean logSuccess =  sf.CashierLogin(userName,password);
                if (logSuccess){
                    this.loginCashier = sf.getCashierByUserName(userName);

                    System.out.println("You have logged in successfully.");

                    CashierController ca = new CashierController();

                    ca.execPage();
                }else{
                    System.out.println("Failed to login");
                }

            } else if (selection == 6) {
                OwnerController oc = new OwnerController(this);
                Map<String, String> userMap = oc.getUserMap(FileUtil.read("src/main/resources/Owner.csv"));
                String username = fe.getString("Please enter a user name");
                final JPasswordField pf = new JPasswordField();
                String password = JOptionPane.showConfirmDialog(null, pf, "Please enter a valid seller password:",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION
                        ? new String(pf.getPassword()) : "";
//                String password = fe.getString("Please enter a password");
                if (userMap.containsKey(username)) {
                    if (userMap.get(username).equals(password)) {
                        oc.execPage();
                    } else {
                        System.out.println("The user name or password is incorrect. Please log in again");
                    }
                } else {
                    System.out.println("The user name does not exist");
                }

            } else if (selection == 7) {
                System.exit(0);
            }
        }
    }

    public String[] getUsernameAndPassword(FrontEndInteraction fe, String nameMsg, String pwdMsg) {
        String username = fe.getString(nameMsg);
        String password;
        if (System.console() == null) {
            final JPasswordField pf = new JPasswordField();
            password = JOptionPane.showConfirmDialog(null, pf, pwdMsg,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION
                    ? new String(pf.getPassword()) : "";
        } else {
            password = new String(System.console().readPassword(pwdMsg));
        }

        return new String[]{username, password};
    }

    public void sellerLogin(FrontEndInteraction fe, SellerController sc) {
        // seller login
        String[] nameAndPassword = getUsernameAndPassword(fe,
                "Please enter a valid seller username:", "Please enter a valid seller password:");
        String userName = nameAndPassword[0];
        String password = nameAndPassword[1];

        loginSeller = sf.sellerLogin(userName, password);
        if (loginSeller != null) {
            System.out.println("You have logged in successfully.");
            sc.execPage();
        } else {
            System.out.println("Failed to login");
        }
    }
}
