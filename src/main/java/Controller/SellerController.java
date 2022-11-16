package Controller;

import Entity.Product;
import Entity.Product.Category;
import Entity.ProductManager;
import Method.FrontEndInteraction;

import java.util.List;

public class SellerController {
    public FrontEndInteraction fe;
    public MainController mc;
    private ProductManager productManager = ProductManager.getInstance();

    public SellerController(MainController mc) {
        this.mc = mc;
        fe = mc.fe;
    }

    public void execPage() {
        while (true) {
            int selection = mc.fe.displayMenu("\nPlease select your operation",
                    new String[]{"Fill the product", "Modify the product", "Product Detail Report", "Total Sale Report", "exit"},
                    "Please enter a selection");

            if (selection == 1) {
                this.addProduct();
            } else if (selection == 2) {
                this.modifyProduct();
            } else if (selection == 3) {
                this.createProductDetailReport();
            } else if (selection == 4) {
                this.createTotalSaleReport();
            } else if (selection == 5) {
                mc.loginSeller = null;
                break;
            }
        }

    }

    public boolean checkId(String id) {
        try {
            int idNum = Integer.parseInt(id);
            if (idNum <= 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkName(String name) {
        if (name == null) {
            return false;
        }

        name = name.trim();
        if (name.isEmpty()) {
            return false;
        }

        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public void addProduct() {
        int selection = mc.fe.displayMenu("Please select category",
                new String[]{Category.Drinks.name(), Category.Chocolates.name(), Category.Chips.name(), Category.Candies.name(), "exit"},
                "Please enter a selection");
        if (selection > 0 && selection < 5) {
            Category category = Category.values()[selection - 1];
            String id = fe.getString("Please enter id for new product (Must be a number greater than 0): ");
            if (!checkId(id)) {
                System.out.println("Invalid id: " + id);
                return;
            }
            String name = fe.getString("Please enter name for new product (number is invalid): ");
            if (!checkName(name)) {
                System.out.println("Invalid name: " + name);
                return;
            }
            if (!productManager.isNameOrIdDuplicate(category, name, id)) {
                int remain = fe.getInt("Please enter number for new product (max 15): ");
                if (remain <= 0 || remain > 15) {
                    System.out.println("Invalid number for new product");
                    return;
                }
                int price = fe.getInt("Please enter price for new product (must greater than 0): ");
                if (price <= 0) {
                    System.out.println("Invalid price for new product");
                    return;
                }
                Product product = new Product(id, category, name, remain, price);
                productManager.addProduct(product);
                System.out.println("Add product " + product + " successfully");
            }
        }
    }


    public void modifyProduct() {
        int selection = mc.fe.displayMenu("Please select category",
                new String[]{Category.Drinks.name(), Category.Chocolates.name(), Category.Chips.name(), Category.Candies.name(), "exit"},
                "Please enter a selection");
        if (selection > 0 && selection < 5) {
            Category category = Category.values()[selection - 1];
            List<Product> productList = productManager.getProductMap().get(category);
            if (productList == null || productList.isEmpty()) {
                System.out.println("No product found in category " + category);
                return;
            }

            String[] nameArr = new String[productList.size() + 1];
            for (int i = 0; i < nameArr.length - 1; i++) {
                nameArr[i] = productList.get(i).getId() + " " + productList.get(i).getProduct();
            }
            nameArr[nameArr.length - 1] = "exit";

            selection = fe.displayMenu("Please select a product", nameArr, "Please enter a selection");
            if (selection > 0 && selection < nameArr.length) {
                Product copy = null;
                Product product = productList.get(selection - 1);
                try {
                    copy = (Product) (productList.get(selection - 1).clone());
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Current select product: " + copy);
                String name = fe.getString("Please enter name for product, input empty means no change(cannot be number): ", true);
                if (checkName(name)) {
                    if (productManager.isNameOrIdDuplicate(category, name, null)) {
                        return;
                    }
                    copy.setProduct(name);
                } else {
                    System.out.println("Invalid name:" + name);
                    return;
                }

                int remain = fe.getInt("Please enter number for new product, max 15, input empty or invalid means no change: ");
                if (remain > 0 && remain < 15) {
                    copy.setRemain(remain);
                } else {
                    return;
                }

                int price = fe.getInt("Please enter price for new product, input 0 or less means no change:");
                if (price > 0) {
                    copy.setPrice(price);
                } else {
                    return;
                }

                product.setProduct(copy.getProduct());
                product.setRemain(copy.getRemain());
                product.setPrice(copy.getPrice());
                productManager.saveProductToFile();

                System.out.println("Product " + copy + " saved successfully");
            }
        }
    }

    public void createProductDetailReport() {
        productManager.showCategoryAndProducts();
        System.out.println("Please take a look at file " + ProductManager.PRODUCT_FILE);
    }

    public void createTotalSaleReport() {
        productManager.saveSaleToFile();
        System.out.println("Please take a look at file " + ProductManager.SALE_FILE);
    }
}
