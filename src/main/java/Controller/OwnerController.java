package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnerController {

    private MainController mc;

    public OwnerController(MainController controller) {
        this.mc = controller;
    }

    public void execPage() {
        while (true) {
            int selected = mc.fe.displayMenu("\nPlease select your operation", new String[]{
                    "all role user list",
                    "remove user",
                    "add user",
                    "cancel order list",
                    "logout"
            }, "Please enter a selection");

            switch (selected) {
                case 1:
                    printAllUser("resources/User.csv", "Customer");
                    printAllUser("resources/Seller.csv", "Seller");
                    printAllUser("resources/Admin.csv", "Admin");
                    printAllUser("resources/Cashier.csv", "Cashier");
                    printAllUser("resources/Owner.csv", "Owner Users");
                    System.out.println("--------------------------------------------------");
                    break;
                case 3:
                    int selection = mc.fe.displayMenu("\nPlease select your operation", new String[]{
                            "Seller User",
                            "Cashier User",
                            "Owner User",
                            "Cancel"
                    }, "Please enter a selection");
                    if (selection == 1) {
                        addUser("src/main/resources/Seller.csv");
                    } else if (selection == 2) {
                        addUser("src/main/resources/Cashier.csv");
                    } else if (selection == 3) {
                        addUser("src/main/resources/Owner.csv");
                    }
                    break;
                case 2:
                    selection = mc.fe.displayMenu("\nPlease select your operation", new String[]{
                            "Seller User",
                            "Cashier User",
                            "Owner User",
                            "Cancel"
                    }, "Please enter a selection");
                    if (selection == 1) {
                        delUser("src/main/resources/Seller.csv");
                    } else if (selection == 2) {
                        delUser("src/main/resources/Cashier.csv");
                    } else if (selection == 3) {
                        delUser("src/main/resources/Owner.csv");
                    }
                    break;
                case 4:
                    printAllCancelOrders("src/main/resources/cancelOrder.csv");
                    break;
                case 5:
                    return;
            }
        }

    }

    public void printAllCancelOrders(String file) {
        List<String> list = FileUtil.read(file);
        System.out.println("-----------------------------------------");
        for (String s : list) {
            String[] split = s.split(",", 3);
            for (String s1 : split) {
                System.out.print(s1 + "\t");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------");
    }

    private void printAllUser(String file, String role) {
        System.out.println("--------------------------------------------------");
        System.out.println(role + " List: ");
        List<String> customerList = FileUtil.read(file);
        for (int i = 1; i < customerList.size(); i++) {
            System.out.println(i + "." + customerList.get(i).split(",")[0]);
        }
    }

    public Map<String, String> getUserMap(List<String> list) {
        Map<String, String> map = new HashMap<>();

        for (int i = 1; i < list.size(); i++) {
            String s = list.get(i);
            String[] up = s.split(",", 2);
            map.put(up[0].trim(), up[1].trim());
        }
        return map;
    }

    private void writeMapToFile(Map<String, String> map, String header, String file) {
        StringBuilder content = new StringBuilder(header).append("\n");

        map.forEach((k, v) -> {
            content
                    .append(k)
                    .append(",")
                    .append(v)
                    .append("\n");
        });

        FileUtil.write(file, content.toString());
    }

    public void delUser(String file) {
        String username = mc.fe.getString("Please enter a user name: ");
        String password = mc.fe.getString("Please enter a password: ");
        List<String> list = FileUtil.read(file);
        String firstHeader = list.get(0);
        Map<String, String> map = getUserMap(list);

        if (map.get(username) == null)
            System.out.println("The user does not exist.");
        else if (map.get(username).equals(password)) {
            map.remove(username);
            System.out.println("successfully remove");
        } else
            System.out.println("The user name or password is incorrect");


        writeMapToFile(map, firstHeader, file);
    }

    public void addUser(String file) {
        String username = mc.fe.getString("Please enter a user name: ");
        String password = mc.fe.getString("Please enter a password: ");
        List<String> list = FileUtil.read(file);
        String firstHeader = list.get(0);
        Map<String, String> map = getUserMap(list);
        map.put(username, password);
        writeMapToFile(map, firstHeader, file);
        System.out.println("successfully added");
    }

}