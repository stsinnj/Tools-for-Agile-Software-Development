package Method;
import Entity.*;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

import java.util.*;
import java.io.*;

import java.util.ArrayList;

public class EntityContainerImpl implements EntityContainer{

    public static String sellerFile = "src/main/resources/Seller.csv";
    public ArrayList<User> readUserCSV() {
        ArrayList<User> UserList = new ArrayList<>();
        File f = new File("src/main/resources/User.csv");
        try {
            Scanner scan = new Scanner(f);
            scan.nextLine();
            while(scan.hasNextLine()) {

                String[] st = scan.nextLine().split(",");
                if (st.length != 2){continue;}

                String UserName = st[0];
                String Password = st[1];

                User newUser = new User(UserName,Password);
                UserList.add(newUser);
            }

        } catch( FileNotFoundException e) {
            e.printStackTrace(); //No file here
        }
        return UserList;
    }

    public ArrayList<Seller> readSellerCSV() {
        ArrayList<Seller> sellerList = new ArrayList<>();
        File f = new File(sellerFile);
        try {
            Scanner scan = new Scanner(f);
            scan.nextLine();
            while(scan.hasNextLine()) {

                String[] st = scan.nextLine().split(",");
                if (st.length != 2){continue;}

                String UserName = st[0];
                String Password = st[1];

                Seller newSeller = new Seller(UserName,Password);
                sellerList.add(newSeller);
            }

        } catch( FileNotFoundException e) {
            e.printStackTrace(); //No file here
        }
        return sellerList;
    }

    public ArrayList<Cashier> readCashierCSV() {
        ArrayList<Cashier> CashierList = new ArrayList<>();
        File f = new File("src/main/resources/Cashier.csv");
        try {
            Scanner scan = new Scanner(f);
            scan.nextLine();
            while(scan.hasNextLine()) {

                String[] st = scan.nextLine().split(",");
                if (st.length != 2){continue;}

                String UserName = st[0];
                String Password = st[1];

                Cashier newCashier = new Cashier(UserName, Password);
                CashierList.add(newCashier);
            }

        } catch( FileNotFoundException e) {
            e.printStackTrace(); //No file here
        }
        return CashierList;
    }

    public HashMap<Double, Double> set_balance(){
        HashMap<Double,Double> amountInMachine = new HashMap<Double,Double>();
        amountInMachine.put(20.0,5.0);
        amountInMachine.put(10.0,5.0);
        amountInMachine.put(5.0,5.0);
        amountInMachine.put(2.0,5.0);
        amountInMachine.put(1.0,5.0);
        return amountInMachine;
    }
}
