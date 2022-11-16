package Entity;

import Entity.Product.Category;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to manage products
 */
public class ProductManager {
    public static final String PRODUCT_FILE = "resources/Product.csv";
    public static final String SALE_FILE = "resources/Sales.csv";
    // singleton instance
    private static final ProductManager INSTANCE = new ProductManager();

    // product map
    private Map<Category, List<Product>> productMap;

    // load product from file
    static {
        try {
            INSTANCE.loadProductFromFile(PRODUCT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ProductManager() {
    }

    public static ProductManager getInstance() {
        return INSTANCE;
    }

    public Map<Category, List<Product>> getProductMap() {
        return productMap;
    }

    /**
     * load products from file
     *
     * @param filename
     * @throws IOException
     */
    public void loadProductFromFile(String filename) throws IOException {
        productMap = new HashMap();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] tmp = line.split(",");
                String id = tmp[0];
                Category category = Category.valueOf(tmp[1]);
                String name = tmp[2];
                int remain = Integer.parseInt(tmp[3]);
                int price = Integer.parseInt(tmp[4]);
                Product product = new Product(id, category, name, remain, price);

                if (!productMap.containsKey(category)) {
                    productMap.put(category, new ArrayList<>());
                }

                productMap.get(category).add(product);
            }
        }
    }

    public boolean isNameOrIdDuplicate(Category category, String name, String id) {
        if (name == null || name.isEmpty()) {
            return true;
        }

        for (Product product : productMap.get(category)) {
            if (id != null && product.getId().equals(id)) {
                System.out.println("Id duplicated");
                return true;
            }
            if (product.getProduct().equals(name)) {
                System.out.println("Name duplicated");
                return true;
            }
        }

        return false;
    }

    /**
     * show products
     */
    public void showCategoryAndProducts() {
        for (Category category : productMap.keySet()) {
            System.out.println("Category: " + category);
            for (Product product : productMap.get(category)) {
                System.out.println("\t" + product);
            }
        }
    }

    public void sellProduct(Cart cart) {
        Product product = cart.getProduct();
        int quantity = cart.getQuantity();
        Category category = product.getCategory();
        List<Product> productList = productMap.get(category);
        for (Product p : productList) {
            if (p.getId().equals(product.getId())) {
                p.addSold(quantity);
            }
        }
        productMap.put(category, productList);
        saveProductToFile();
    }

    public void addProduct(Product product) {
        if (!productMap.containsKey(product.getCategory())) {
            productMap.put(product.getCategory(), new ArrayList<>());
        }
        productMap.get(product.getCategory()).add(product);

        saveProductToFile();
    }

    public void saveProductToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
            bw.write("ID,Category,Product,Num,Price\n");
            for (Category category : productMap.keySet()) {
                for (Product product : productMap.get(category)) {
                    bw.write(product.getId() + "," + category.name() + "," + product.getProduct() + ","
                            + product.getRemain() + "," + product.getPrice() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error while writing to file " + PRODUCT_FILE);
        }
    }

    public void saveSaleToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SALE_FILE))) {
            System.out.print("ID,Category,Product,SALE\n");
            bw.write("ID,Category,Product,SALE\n");
            for (Category category : productMap.keySet()) {
                for (Product product : productMap.get(category)) {
                    String line = product.getId() + "," + category.name() + "," + product.getProduct() + ","
                            + product.getTotalSold() + "\n";
                    bw.write(line);
                    System.out.print(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while writing to file " + SALE_FILE);
        }
    }
}
