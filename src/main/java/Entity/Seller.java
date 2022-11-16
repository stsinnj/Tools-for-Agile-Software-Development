package Entity;

public class Seller {
    public String Seller_username;
    public String Seller_password;

    public Seller(String Admin_username, String Admin_password) {
        this.Seller_password = Admin_password;
        this.Seller_username = Admin_username;
    }

    public String getSeller_username() {
        return this.Seller_username;
    }

    public String getSeller_password() {
        return this.Seller_password;
    }

    public boolean setAdmin_username(String s) {
        if (s == null || s.equals("")) {
            System.out.println("\nAdmin username can not be empty or null.");
            return false;
        }
        this.Seller_username = s;
        return true;
    }

    public boolean setAdmin_password(String s) {
        if (s == null || s.equals("")) {
            System.out.println("\nAdmin password can not be empty or null.");
            return false;
        }
        this.Seller_password = s;
        return true;
    }
}