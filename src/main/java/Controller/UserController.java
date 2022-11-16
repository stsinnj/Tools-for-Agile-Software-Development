package Controller;

import Entity.*;
import Method.FrontEndInteraction;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class UserController {
    public static MainController mc = new MainController();
    FrontEndInteraction fe = new FrontEndInteraction();
    private ProductManager productManager = ProductManager.getInstance();
    private BankCardController bankCardController = new BankCardController();
    private OrderController orderController = new OrderController();
    private CartManager cartManager = CartManager.getInstance();

    public UserController(MainController mc) {
        this.mc = mc;
    }

    public void execPage() {
        while (true) {
            if (mc.loginUser == null) {
                return;
            }

            int selection = mc.fe.displayMenu("\nPlease select your operation",
                    new String[]{"View Goods Category", "Cancel Order", "Add Product to cart", "View Cart Products", "set bank card"
                            , "show bank card", "Checkout", "show all order", "exit"},
                    "Please enter a selection");

            if (selection == 1) {
                this.view_category();
            } else if (selection == 2) {
                this.cancel_order();
            } else if (selection == 3) {
                this.addProductToCart();
            } else if (selection == 4) {
                this.viewsCartProducts();
            } else if (selection == 5) {
                this.setBankCard();
            } else if (selection == 6) {
                this.showBankCard();
            } else if (selection == 7) {
                this.checkout();
            } else if (selection == 8) {
                this.showAllOrder();
            } else if (selection == 9) {
                mc.loginUser = null;
                break;
            }
        }

    }

    public void showAllOrder() {
        String username = mc.loginUser.User_username;
        List<Order> allOrder = orderController.queryOrderByName(username);
        if (allOrder.size() > 0) {
            for (Order o : allOrder) {
                System.out.println(o.toString());
            }
        } else {
            System.out.println("not have order ");
        }
    }

    private void checkout() {
        double sum = 0;
        String username = mc.loginUser.User_username;
        this.viewsCartProducts();
        List<Cart> cartList = cartManager.getCartByUser(username);
        for (Cart cart : cartList) {
            sum += cart.getQuantity() * cart.getProduct().getPrice();
        }
        if (cartList.size() == 0) {
            System.out.println("not have order");
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String payType;
            ThreadTest t = new ThreadTest(120000, new TimerOutException("timeout"));
            t.start();
            while (true) {
                int selection = 0;
                selection = mc.fe.displayMenu("\nPlease select your operation",
                        new String[]{"Pay by Card", "Pay by Cash", "Cancel Order"},
                        "Please enter a selection");
                if (selection == 1) {
                    // Use Card to pay
                    payType = "card";
                    BankCard bankCard = bankCardController.queryBankCardByName(username);
                    if (t.isTimeout) {return;}
                    if (bankCard == null) {
                        System.out.println("not have card");
                    } else {
                        String card = fe.getString("input  card no:");
                        int bLen = card.length();
                        if(bLen<11||bLen>13){
                            System.out.println("The credit card is the wrong length (11-13) ");
                            t.cancel();
                            return;
                        }
                        if (t.isTimeout) {return;}

                        String indate = fe.getString("input  card valid date (yyyy-MM):");
                        int dLen = indate.length();
                        if(dLen!=7){
                            System.out.println("The check code is seven bits ");
                            t.cancel();
                            return;
                        }

                        if (t.isTimeout) {return;}
                        String vCode = fe.getString("input  card CSV:");
                        int vLen = vCode.length();
                        if(vLen!=3){
                            System.out.println("The check code is three bits ");
                            t.cancel();
                            return;
                        }

                        if (t.isTimeout) {return;}

                        boolean isCard = card.equals(bankCard.cardNo) && indate.equals(bankCard.indate) && vCode.equals(
                                bankCard.vCode);
                        if (!isCard) {
                            System.out.println("card info error");
                        } else {
                            String orderContent = "";
                            for (Cart cart : cartList) {
                                orderContent += cart.toString() + "-";
                                //sell
                                productManager.sellProduct(cart);
                            }
                            // remove
                            cartManager.removeCartByUser(username);
                            Order order = new Order(username, orderContent, String.valueOf(sum), payType,
                                    sdf.format(new Date()));
                            orderController.saveCard(order);
                            System.out.println("check success!!!");
                        }
                        t.cancel();
                        System.out.println(" ");
                        break;
                    }
                } else if (selection == 2) {
                    // Use Cash
                    payType = "cash";
                    double cash=0;
                    try {
                        cash  = Double.parseDouble(fe.getString("input cash:"));
                    }catch (NumberFormatException e){
                        System.out.println("Please enter the correct amount");
                        return;
                    }

                    if(cash<0){
                            System.out.println("Please enter an amount greater than 0");
                            t.cancel();
                            return;
                        }

                        if (cash < sum) {
                            System.out.println("The amount is less than to be paid");
                            t.cancel();
                            return;
                        }

                        if (cash % 1 != 0) {
                            System.out.println("input cash error");
                            t.cancel();
                            return;
                        }
                        if (cash > sum * 10) {
                            System.out.println("Excessive amount input");
                            t.cancel();
                            return;
                        }
                        final String[] orderContent = {""};
                        double finalSum1 = sum;
                        String finalPayType1 = payType;
                        try {
                            for (Cart cart : cartList) {
                                orderContent[0] += cart.toString() + "-";
                                //sell
                                productManager.sellProduct(cart);
                            }
                            // remove
                            cartManager.removeCartByUser(username);
                            Order order = new Order(username, orderContent[0], String.valueOf(finalSum1), finalPayType1,
                                    sdf.format(new Date()));
                            orderController.saveCard(order);
                            System.out.println("check success!!!");
                            splitChange(cash - finalSum1);
                            t.cancel();
                            break;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                } else if (selection == 3) {
                    t.cancel();
                    cancel_order();
                    break;
                }
            }
        } catch (IllegalStateException e) {
            return;
        }
    }


    void splitChange(double money) {
        if (money <= 0) {
            System.out.println("money is invalid, money=" + money);
            return;
        }
        double[] prices = {50, 20, 10, 5, 1, 0.5};
        double[] notes = new double[prices.length];
        double change = money;
        int count = 0;
        for (int i = 0; i < prices.length; i++) {
            count = 0;
            while (change - prices[i] >= 0) {
                change = change - prices[i];
                count++;
            }
            notes[i] = count;
        }
        System.out.println("change total =" + money + ", change:");
        for (int num = 0; num < prices.length; num++) {
            if (notes[num] > 0) {
                System.out.print(notes[num] + " n " + prices[num] + "$  \n");
            }
        }
    }


    public void showBankCard() {
        String username = mc.loginUser.User_username;
        BankCard card = bankCardController.queryBankCardByName(username);
        if (card != null) {
            System.out.println(card.toString());
        } else {
            System.out.println("dThe check code is three bits");
        }
    }

    public void setBankCard() {
        String username = mc.loginUser.User_username;

        String bankCard = fe.getString("input  card no:");
        int bLen = bankCard.length();
        if(bLen<11||bLen>13){
            System.out.println("The credit card is the wrong length (11-13) ");
            return;
        }
        String indate = fe.getString("input  card valid date (yyyy-MM):");
        int dLen = indate.length();
        if(dLen!=7){
            System.out.println("The check code is seven bits ");
            return;
        }
        String vCode = fe.getString("input  card CSV:");
        int vLen = vCode.length();
        if(vLen!=3){
            System.out.println("The check code is three bits ");
            return;
        }


        boolean flag = bankCardController.saveCard(new BankCard(username, bankCard, indate, vCode));
        if (flag) {
            System.out.println("Your card is successfully saved");
        } else {
            System.out.println("Your card is not saved ");
        }
    }

    public void addProductToCart() {
        while (true) {
            String productId = fe.getString("Please enter the product id (-1 exit):");
            if ("-1".equals(productId)) {
                break;
            }
            int quantity = fe.getInt("Please enter the product quantity:");
            if (cartManager.addCartItem(mc.loginUser.User_username, productId, quantity)) {
                System.out.println("Add cart success!");
            }
        }

        viewsCartProducts();
    }

    public void viewsCartProducts() {
        System.out.println("carts list: ");
        double sum = 0;
        for (Cart cart : cartManager.getCartByUser(mc.loginUser.User_username)) {
            System.out.println(cart);
            sum += cart.getQuantity() * cart.getProduct().getPrice();
        }
        System.out.println("Amount: " + sum);
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public void cancel_order() {
        int selection = mc.fe.displayMenu("\nPlease select your operation",
                new String[]{"User Cancelled", "Change not available"},
                "Please enter a selection");
        if (selection == 1) {
            List<Cart> cartList = CartManager.getInstance().getCartByUser(mc.loginUser.User_username);
            if (cartList == null || cartList.isEmpty()) {
                System.out.println("There is no product in cart");
            } else {
                CartManager.getInstance().removeCartByUser(mc.loginUser.User_username);
                System.out.println("Order canceled, Reason: User Cancelled, Date: " + getTime());
                OrderCancel.addOrderCancel("User Cancelled", getTime(), mc.loginUser.User_username);
            }
        } else if (selection == 2) {
            CartManager.getInstance().removeCartByUser(mc.loginUser.User_username);
            System.out.println("Order canceled, Reason: Change not available, Date: " + getTime());
            OrderCancel.addOrderCancel("Change not available", getTime(), mc.loginUser.User_username);
        } else {
            System.out.println("Invalid selection");
        }
    }

    public void view_category() {
        productManager.showCategoryAndProducts();
        return;
    }

}
