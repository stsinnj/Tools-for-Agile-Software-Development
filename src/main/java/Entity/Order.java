package Entity;

/**
 * order info
 */
public class Order {

    public String username;
    public String orderContent;
    public String amount;
    public String payType;
    public String payTime;

    public Order(String username, String orderContent, String amount, String payType, String payTime) {
        this.username = username;
        this.orderContent = orderContent;
        this.amount = amount;
        this.payType = payType;
        this.payTime = payTime;
    }


    public String format() {
        return username +
                "," + orderContent +
                "," + amount +
                "," + payType +
                "," + payTime;
    }


    @Override
    public String toString() {
        return "Order{" +
                "username='" + username + '\'' +
                ", orderContent='" + orderContent + '\'' +
                ", amount='" + amount + '\'' +
                ", payType='" + payType + '\'' +
                ", payTime='" + payTime + '\'' +
                '}';
    }
}
