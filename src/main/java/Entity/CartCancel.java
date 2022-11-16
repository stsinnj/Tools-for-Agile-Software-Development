package Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CartCancel {
    public enum Reason {
        TIMEOUT, USER, DISABLE
    }

    private Cart cart;
    private Date time;
    private Reason reason;
    private String user;

    public CartCancel(Cart cart, Reason reason, String user) {
        this.cart = cart;
        this.reason = reason;
        this.user = user;
        time = new Date();
    }

    public String getUser() {
        return user;
    }

    public Cart getCart() {
        return cart;
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    public Reason getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "Reason: " + reason + ", Date: " + getTime() + ", User: " + user + ", Cart: " + cart;
    }
}
