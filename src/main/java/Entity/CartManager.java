package Entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CartManager {
    private Map<String, List<Cart>> cartsMap;
    private final String saveFile = "resources/Cart.csv";
    private static final CartManager CART_MANAGER = new CartManager();

    private CartManager() {
        this.initUserProducts();
    }

    public static CartManager getInstance() {
        return CART_MANAGER;
    }

    private void initUserProducts() {
        cartsMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(saveFile))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] itemsArr = line.split(",");
                String username = itemsArr[0].trim();
                String productId = itemsArr[1].trim();
                int quantity = Integer.parseInt(itemsArr[2]);
                if (!cartsMap.containsKey(username)) {
                    cartsMap.put(username, new ArrayList<>());
                }
                Cart cart = new Cart();
                cart.setQuantity(quantity);
                cart.setProduct(getProductById(productId));
                cartsMap.get(username).add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Product getProductById(String id) {
        Set<Product> products = new HashSet<>();
        ProductManager.getInstance().getProductMap().forEach((k, v) -> {
            products.addAll(v);
        });
        for (Product product : products) {
            if (id.equals(product.getId())) {
                return product;
            }
        }
        return null;
    }

    public List<Cart> getCartByUser(String username) {
        if (!cartsMap.containsKey(username)) {
            cartsMap.put(username, new ArrayList<>());
        }
        List<Cart> cartList = cartsMap.get(username);
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        return cartList;
    }


    public void removeCartByUser(String username) {
        if (cartsMap.containsKey(username)) {
            cartsMap.remove(username);
            saveCsv();
        }
        //flush
        initUserProducts();
    }

    public Cart getCart(String username, String productId) {
        List<Cart> cartByUser = getCartByUser(username);
        // 寻找这个产品是否存在
        for (Cart c : cartByUser) {
            if (c.getProduct().getId().equals(productId)) {
                return c;
            }
        }
        return null;
    }

    // 添加购物车项目
    public boolean addCartItem(String username, String productId, int quantity) {
        Product product = getProductById(productId);
        if (getProductById(productId) != null) {
            if (product.getRemain() < quantity){
                System.out.println("Don't have enough products!");
                return false;
            }else {
                List<Cart> cartByUser = getCartByUser(username);
                Cart cart = getCart(username, productId);
                if (cart == null) {
                    cart = new Cart();
                    cart.setProduct(product);
                    cartByUser.add(cart);
                }
                cart.setQuantity(cart.getQuantity() + quantity);
                saveCsv();
                return true;
            }
        } else {
            System.out.println("Product not exist!");
            return false;
        }
    }

    // 保存信息到文件中
    public void saveCsv() {
        try (FileOutputStream fos = new FileOutputStream(new File(saveFile))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Username,ProductId,Quantity\n");
            for (String username : this.cartsMap.keySet()) {
                for (Cart cart : cartsMap.get(username)) {
                    sb
                            .append(username)
                            .append(",")
                            .append(cart.getProduct().getId())
                            .append(",")
                            .append(cart.getQuantity())
                            .append("\n");
                }
            }
            fos.write(sb.toString().getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}