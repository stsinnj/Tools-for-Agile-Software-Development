package Controller;


import Entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    List<Order> orderList = new ArrayList<>();
    String filePath = "src/main/resources/order.csv";

    void loadOrder() {
        orderList.clear();
        List<String> list = FileUtil.read(filePath);
        if (list.size() > 0) {
            for (String s : list) {
                String arr[] = s.split(",");
                if (arr.length == 5) {
                    Order order = new Order(arr[0], arr[1], arr[2], arr[3], arr[4]);
                    orderList.add(order);
                }
            }
        }
    }


    /**
     * save a order
     *
     * @param order
     * @return
     */
    public boolean saveCard(Order order) {
        loadOrder();
        orderList.add(order);
        String saveContent = "";
        for (Order c : orderList) {
            saveContent += c.format() + "\n";
        }
        FileUtil.write(filePath, saveContent);
        return true;
    }


    /**
     * get order by username
     *
     * @param username
     * @return
     */
    public List<Order> queryOrderByName(String username) {
        List<Order> orders = new ArrayList<>();
        loadOrder();
        for (Order c : orderList) {
            if (c.username.equals(username)) {
                orders.add(c);
            }
        }
        return orders;
    }
}
