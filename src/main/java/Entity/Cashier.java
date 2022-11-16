package Entity;

public class Cashier {
    public String Cashier_username;
    public String Cashier_password;

    public Cashier(String Cashier_username, String Cashier_password) {
        this.Cashier_password = Cashier_password;
        this.Cashier_username = Cashier_username;
    }

    public String getCashier_username() {
        return this.Cashier_username;
    }

    public String getCashier_password() {
        return this.Cashier_password;
    }
}
