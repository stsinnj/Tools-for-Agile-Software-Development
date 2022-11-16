package Entity;

/**
 * class to represent the product
 */
public class Product implements Cloneable {
    public enum Category {
        Drinks, Chocolates, Chips, Candies
    }

    private Category category;
    // eg. coca cola
    private String product;
    private int remain;
    private int price;
    private String id;
    private int totalSold;

    public Product(String id, Category category, String product, int remain, int price) {
        this.category = category;
        this.product = product;
        this.remain = remain;
        this.price = price;
        this.id = id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Product product = new Product(this.id, this.category,
                this.product, this.remain, this.price);
        product.totalSold = this.totalSold;

        return product;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public String getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getProduct() {
        return product;
    }

    public int getRemain() {
        return remain;
    }

    public void addRemain(int num) {
        this.remain +=  num;
    }

    public int getPrice() {
        return price;
    }

    public void addSold(int num) {
        remain -= num;
        totalSold += num;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Product: " + product + " remain: " + remain + " price: " + price;
    }
}
