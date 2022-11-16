package Controller;

import Method.SysFunction;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class CashierController {
    public static MainController mc = new MainController();
    public static SysFunction sf = new SysFunction();
    public static OrderController od = new OrderController();



    public void execPage() throws IOException {
        sf.Start();
        while (true) {
            int selection = 0;

            selection = mc.fe.displayMenu("\nPlease select your operation",
                    new String[]{"Modify Money", "View Current Available Change", "View Transaction", "exit"},
                    "Please enter a selection");

            if (selection == 1) {
                String MoneyType = mc.fe.getString("Please Enter Notes/Coins which will be changed(Enter cancel to exit): ");
                if (MoneyType.equals("cancel")) {
                    System.out.println("Return to Cashier Menu");
                    continue;
                }
                String MoneyBehaviour = mc.fe.getString("You wanna add or withdraw the Money(Enter '+' or '-')");
                if (!Objects.equals(MoneyBehaviour, "+") && !Objects.equals(MoneyBehaviour, "-")){
                    System.out.println("Only accept '+' or '-' input");
                    System.out.println("Return to Cashier Menu");
                    continue;
                }
                int MoneyAmount = mc.fe.getInt("The Number of Notes/Coins");
                if(MoneyAmount <= 0) {
                    System.out.println("The Number of Notes/Coins should be Natural Number");
                    System.out.println("Return to Cashier Menu");
                    continue;
                }
                if(modify_money(MoneyType,MoneyBehaviour,MoneyAmount)){
                    System.out.println("Modify Successfully");
                } else {
                    System.out.println("Not Found NOTES/COINS.(Only accept:5,10,20,2,1)");
                    System.out.println("Return to Cashier Menu");
                }
            } else if (selection == 2) {
                this.current_change();
            } else if (selection == 3) {
                this.view_transaction();
            } else if (selection == 4) {
                mc.loginCashier = null;
                break;
            }
        }
    }

    public void view_transaction() throws IOException {
        InputStreamReader sr = new InputStreamReader(new FileInputStream(od.filePath));
        char[] chars = new char[10];
        int len;
        StringBuilder sb = new StringBuilder();

        while ((len = sr.read(chars))!=-1){
            sb.append(chars,0,len);
        }
        sr.close();

        System.out.println(sb);
        }



    public void current_change() {
        System.out.println("Notes/Coins Quantity");
        sf.vending_balance.forEach((key, value) -> System.out.println(key + " " + value));
    }

    public boolean modify_money(String MoneyType, String MoneyBehaviour, int MoneyAmount) {
        if (MoneyBehaviour.equals("+")) {
            if (MoneyType.equals("1") || MoneyType.equals("1.0")) {
                double money1 = sf.vending_balance.get(1.0);
                sf.vending_balance.replace(1.0, money1 + MoneyAmount);

            }else if(MoneyType.equals("2") || MoneyType.equals("2.0")){
                    double money2 = sf.vending_balance.get(2.0);
                    sf.vending_balance.replace(2.0, money2 + MoneyAmount);

            } else if(MoneyType.equals("20") || MoneyType.equals("20.0")){
                    double money20 = sf.vending_balance.get(20.0);
                    sf.vending_balance.replace(20.0, money20 + MoneyAmount);
            } else if(MoneyType.equals("10") || MoneyType.equals("10.0")){
                    double money10 = sf.vending_balance.get(10.0);
                    sf.vending_balance.replace(10.0, money10 + MoneyAmount);
            } else if(MoneyType.equals("5") || MoneyType.equals("5.0")){
                    double money5 = sf.vending_balance.get(5.0);
                    sf.vending_balance.replace(5.0, money5 + MoneyAmount);
            } else {
                    return false;
            }
            return true;

        } else if (MoneyBehaviour.equals("-")) {
            if (MoneyType.equals("1") || MoneyType.equals("1.0") ) {
                double money1 = sf.vending_balance.get(1.0);
                double sum = money1 - MoneyAmount;
                if (sum > 0) {
                    sf.vending_balance.replace(1.0, money1 - MoneyAmount);
                } else {
                    return false;
                }
            } else if (MoneyType.equals("2") || MoneyType.equals("2.0")) {
                double money2 = sf.vending_balance.get(2.0);
                double sum = money2 - MoneyAmount;
                if (sum > 0) {
                    sf.vending_balance.replace(2.0, money2 - MoneyAmount);
                } else {
                    return false;
                }
            } else if (MoneyType.equals("20") || MoneyType.equals("20.0")) {
                double money20 = sf.vending_balance.get(20.0);
                double sum = money20 - MoneyAmount;
                if (sum > 0) {
                    sf.vending_balance.replace(20.0, money20 - MoneyAmount);
                } else {
                    return false;
                }
            } else if (MoneyType.equals("10") || MoneyType.equals("10.0") ) {
                double money10 = sf.vending_balance.get(10.0);
                double sum = money10 - MoneyAmount;
                if (sum > 0) {
                    sf.vending_balance.replace(10.0, money10 - MoneyAmount);
                } else {
                    return false;
                }
            } else if (MoneyType.equals("5") || MoneyType.equals("5.0")) {
                double money5 = sf.vending_balance.get(5.0);
                double sum = money5 - MoneyAmount;
                if (sum > 0) {
                    sf.vending_balance.replace(5.0, money5 - MoneyAmount);
                } else {
                    return false;
                }
            } else {
                return false;
            }
            return true;

        }
        return false;
    }



}
