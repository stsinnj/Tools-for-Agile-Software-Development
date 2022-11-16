package Method;
import Entity.*;
import java.util.*;

public interface EntityContainer {
    ArrayList<User> readUserCSV();
    ArrayList<Cashier> readCashierCSV();
    ArrayList<Seller> readSellerCSV();
}
