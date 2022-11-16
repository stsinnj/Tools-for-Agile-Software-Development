package Entity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderCancel {
    public static String CANCEL_ORDER_FILE = "src/main/resources/cancelOrder.csv";

    private String reason;
    private String date;
    private String user;

    private static List<OrderCancel> orderCancelList = new ArrayList<OrderCancel>();

    public OrderCancel(String reason, String date, String user) {
        this.reason = reason;
        this.date = date;
        this.user = user;
    }

    @Override
    public String toString() {
        return date + "," + user + "," + reason;
    }

    public static void addOrderCancel(String reason, String date, String user) {
        orderCancelList.add(new OrderCancel(reason, date, user));

        File file = new File(CANCEL_ORDER_FILE);
        boolean writeHead = !file.exists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CANCEL_ORDER_FILE, true))) {
            if (writeHead) {
                bw.write("Date and Time,User Name,Reason\n");
            }
            for (OrderCancel cancel : orderCancelList) {
                bw.write(cancel.toString() + "\n");
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
